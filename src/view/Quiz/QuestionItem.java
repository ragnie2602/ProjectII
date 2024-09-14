package View.Quiz;

import java.awt.event.ItemEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import Controller.QuizCtrl;
import Model.Choice;
import Model.Question;

public class QuestionItem extends JPanel {
  ArrayList<Choice> choices;
  int correct = 0;
  Question question;

  public QuestionItem(Question question, int n, Function<Integer, Boolean> callback) {
    super();

    this.question = question;

    ButtonGroup bg = new ButtonGroup();

    setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    add(new JLabel("Câu hỏi " + (n + 1) + ": " + question.getAsk()));
    if (question.getMedia() != null) {
      add(new JLabel(question.getMedia()));
    }

    choices = QuizCtrl.getAllChoices(question.getId());
    Collections.shuffle(choices);

    if (isMultiChoice(choices) > 1) {
      for (Choice choice : choices) {
        JCheckBox checkBox = new JCheckBox(choice.getText());
        checkBox.addItemListener(e -> {
          callback.apply(n);

          if (e.getStateChange() == ItemEvent.SELECTED) {
            correct += choice.getIsCorrect() ? 1 : 0;
          } else {
            correct -= choice.getIsCorrect() ? 1 : 0;
          }
        });

        add(checkBox);
        if (choice.getMedia() != null) {
          add(new JLabel(choice.getMedia()));
        }
      }
    } else {
      for (Choice choice : choices) {
        JRadioButton radioButton = new JRadioButton(choice.getText());
        radioButton.addItemListener(e -> {
          callback.apply(n);

          if (e.getStateChange() == ItemEvent.SELECTED) {
            correct = choice.getIsCorrect() ? 1 : 0;
          }
        });

        add(radioButton);
        bg.add(radioButton);
        if (choice.getMedia() != null) {
          add(new JLabel(choice.getMedia()));
        }
      }
    }
  }

  private int isMultiChoice(ArrayList<Choice> choices) {
    int i = 0;

    for (Choice choice : choices) {
      i += choice.getIsCorrect() ? 1 : 0;
    }

    return i;
  }

  public float submit() {
    return (correct + 0.0f) / isMultiChoice(choices);
  }

  public Question getQuestion() {
    return question;
  }
}
