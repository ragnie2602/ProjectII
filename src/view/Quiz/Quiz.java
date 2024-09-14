package View.Quiz;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import Controller.QuizCtrl;
import Model.Attempt;
import Model.Exam;
import Model.Question;
import Resources.Callback;
import Resources.Constants;
import Resources.Tools;
import Resources.Constants.ToastType;
import View.Components.Button;
import View.Components.Column;
import View.Components.Dialog;
import View.Components.RoundedPanel;
import View.Components.Row;

public class Quiz extends JFrame {
  ArrayList<Button> questionButtons = new ArrayList<>();
  ArrayList<Question> questions;
  Button submit;
  Date start;
  int timeRemaining, userId;
  JPanel leftPanel, rightPanel;
  RoundedPanel controlPanel;
  Timer timer;

  public Quiz(Exam exam, int userId, JFrame parentFrame) {
    super();

    start = new Date();
    timeRemaining = exam.getDuration() * 60;
    this.userId = userId;

    GridBagConstraints gbc = new GridBagConstraints();
    JLabel timeLabel = new JLabel("Thời gian còn lại: " + Tools.toTime(timeRemaining));
    leftPanel = new JPanel();
    rightPanel = new JPanel();
    JScrollPane scrollPane = new JScrollPane(rightPanel);

    getContentPane().setBackground(Constants.gray01);
    setAlwaysOnTop(true);
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    getContentPane().setLayout(new GridBagLayout());
    setResizable(false);
    setUndecorated(true);
    setSize(1000, 500);

    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent we) {
        setEnabled(false);

        new Dialog(Quiz.this, "Bạn chưa nộp bài, nộp bài?", x -> {
          Quiz.this.dispose();
          return true;
        });
      }

      public void windowClosed(WindowEvent we) {
        submitQuiz(exam);

        parentFrame.setEnabled(true);
        parentFrame.toFront();

        timer.stop();
      }
    });

    controlPanel = new RoundedPanel(16, Constants.gray02);
    controlPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    controlPanel.setLayout(new GridLayout(0, 5, 10, 10));
    for (int i = 0; i < exam.getQuestionCount(); i++) {
      Button button = new Button("" + (i + 1));
      final int idx = i;

      button.setBorderColor(Constants.gray02);
      button.setRadius(10);
      button.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ae) {
          rightPanel.scrollRectToVisible(rightPanel.getComponent(idx).getBounds());
        }
      });

      controlPanel.add(button);
      questionButtons.add(button);
    }

    submit = new Button("Nộp bài");
    submit.setBackground(Constants.blue01);
    submit.setForeground(Color.WHITE);
    submit.addActionListener(e -> {
      new Dialog(this, "Bạn vẫn muốn nộp bài khi còn thời gian chứ?", x -> {
        parentFrame.setEnabled(true);
        parentFrame.toFront();
        timer.stop();
        this.dispose();

        Callback.homepageCallback.backToHomepage();

        return true;
      });
      timer.stop();
    });

    timer = new Timer(950, e -> {
      timeRemaining--;
      timeLabel.setText("Thời gian còn lại: " + Tools.toTime(timeRemaining));

      if (timeRemaining == 0) {
        parentFrame.setEnabled(true);
        parentFrame.toFront();

        timer.stop();
        this.dispose();

        Callback.homepageCallback.backToHomepage();
        Callback.toastCallback.callbackToast("Bạn đã hết giờ làm bài!", ToastType.WARNING);
      }
    });
    timer.start();
    timeLabel.setBorder(BorderFactory.createLineBorder(Constants.gray01, 1));

    leftPanel.add(new Column(12, new Row(0, Box.createHorizontalGlue(), timeLabel, Box.createHorizontalGlue()),
        controlPanel,
        new Row(0, Box.createHorizontalGlue(), submit, Box.createHorizontalGlue())));

    questions = QuizCtrl.generateQuiz(exam);

    rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
    scrollPane.setBorder(null);
    scrollPane.getVerticalScrollBar().setUnitIncrement(20);
    for (int i = 0; i < questions.size(); i++) {
      rightPanel.add(new QuestionItem(questions.get(i), i, x -> {
        questionButtons.get(x).setBackground(Constants.blue01);
        questionButtons.get(x).setForeground(Color.WHITE);

        return true;
      }));
    }

    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 11;
    gbc.weighty = 1;
    getContentPane().add(leftPanel, gbc);

    gbc.gridx = 1;
    gbc.weightx = 23;
    getContentPane().add(scrollPane, gbc);

    setVisible(true);
  }

  private void submitQuiz(Exam exam) {
    float grade = 0;

    for (Component component : rightPanel.getComponents()) {
      QuestionItem qi = (QuestionItem) component;
      Question question = qi.getQuestion();

      grade += qi.submit() * (question.getLevel() == 0 ? exam.getEasyPts()
          : question.getLevel() == 1 ? exam.getMediumPts() : exam.getHardPts());
    }

    Date end = new Date();

    QuizCtrl.submitQuiz(new Attempt(exam.getId(), userId, end.getTime() - start.getTime(), grade, start));
  }
}
