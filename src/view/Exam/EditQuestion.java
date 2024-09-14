package View.Exam;

import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;

import javax.swing.*;

import Controller.ExamCtrl;
import Model.Choice;
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
import View.Components.TextArea;
import View.Components.TextField;

public class EditQuestion extends RoundedPanel {
    private Button addChoice, delete;
    private TextArea ask;
    private JLabel media;
    private JRadioButton easy, hard, medium;
    public Question question;

    public EditQuestion(JFrame parentFrame, JPanel prevPanel, Question question, ArrayList<Choice> choices) {
        super(16);

        this.question = question;

        ButtonGroup bg = new ButtonGroup();

        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        addChoice = new Button("Thêm phương án",
                Tools.resize(new ImageIcon(Constants.imagePath + "ic_add.png"), 40, 40));
        addChoice.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addChoice.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                add(new ChoiceSetting(null, EditQuestion.this, null), getComponentCount() - 2);

                revalidate();
                repaint();
            }
        });

        ask = new TextArea("Nội dung câu hỏi", 16);
        ask.setBorderColor(Constants.gray02);
        ask.setText(question.getAsk());

        delete = new Button("Xóa");
        delete.setBackground(Constants.red01);
        delete.setForeground(Color.WHITE);
        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new Dialog(parentFrame, "Bạn có chắc muốn xóa vĩnh viễn câu hỏi này không?",
                        x -> {
                            if (ExamCtrl.deleteQuestion(question.getId())) {
                                prevPanel.remove(EditQuestion.this);

                                prevPanel.revalidate();
                                prevPanel.repaint();

                                return true;
                            } else {
                                return false;
                            }
                        });
            }
        });

        easy = new JRadioButton("Dễ");
        easy.setSelected(question.getLevel() == 0);
        hard = new JRadioButton("Khó");
        hard.setSelected(question.getLevel() == 2);
        medium = new JRadioButton("Trung bình");
        medium.setSelected(question.getLevel() == 1);

        bg.add(easy);
        bg.add(medium);
        bg.add(hard);

        media = new JLabel("Thêm hình ảnh");
        media.setBackground(Constants.gray02);
        media.setCursor(new Cursor(Cursor.HAND_CURSOR));
        media.setForeground(Color.BLUE);
        if (question.getMedia() != null) {
            media.setText("");
            media.setIcon(question.getMedia());
        }
        media.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                ImageIcon ii = Tools.pickImage();

                if (ii != null) {
                    media.setText(null);
                    media.setIcon(ii);
                }
            }
        });

        add(new Column(8,
                ask,
                media,
                new Row(16, easy, medium, hard)));
        add(new Row(0, addChoice));
        add(Box.createVerticalStrut(16));
        for (Choice choice : choices) {
            add(new ChoiceSetting(parentFrame, this, choice));
        }
        add(Box.createVerticalStrut(16));
        add(new Row(0, delete));
    }

    public void createQuestion(int examId) {
        int questionId = ExamCtrl.createQuestion(new Question(
                ask.getText(),
                (ImageIcon) media.getIcon(),
                easy.isSelected() ? 0 : medium.isSelected() ? 1 : 2,
                examId));

        if (questionId != -1) {
            ArrayList<Choice> choices = new ArrayList<>();

            int sz = getComponentCount() - 2;

            for (int i = 3; i < sz; i++) {
                ChoiceSetting cs = (ChoiceSetting) getComponent(i);
                choices.add(cs.getChoice(questionId));
            }

            ExamCtrl.createChoices(choices);
        } else {
            Callback.toastCallback.callbackToast("Có lỗi khi tạo câu hỏi " + ask.getText(), ToastType.ERROR);
        }
    }

    public void editQuestion() {
        boolean status = ExamCtrl.editQuestion(new Question(
                question.getId(),
                ask.getText(),
                (ImageIcon) media.getIcon(),
                easy.isSelected() ? 0 : medium.isSelected() ? 1 : 2,
                question.getExamId()));

        if (status) {
            ArrayList<Choice> choices = new ArrayList<>();

            int sz = getComponentCount() - 2;

            for (int i = 3; i < sz; i++) {
                ChoiceSetting cs = (ChoiceSetting) getComponent(i);
                choices.add(cs.getChoice(question.getId()));
            }

            ExamCtrl.editChoices(choices);
        } else {
            Callback.toastCallback.callbackToast("Có lỗi khi tạo câu hỏi " + ask.getText(), ToastType.ERROR);
        }
    }

    class ChoiceSetting extends RoundedPanel {
        private Button delete;
        private JCheckBox isCorrect;
        private JLabel media;
        private TextField text;

        private Choice choice;

        ChoiceSetting(JFrame parentFrame, RoundedPanel parent, Choice choice) {
            super(16);

            this.choice = choice;

            setBackground(Constants.gray01);
            setLayout(new GridLayout(1, 1));

            delete = new Button("", Tools.resize(new ImageIcon(Constants.imagePath + "ic_delete.png"), 28, 28));
            delete.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    if (choice != null && !ExamCtrl.deleteChoice(choice.getId())) {
                        Callback.toastCallback.callbackToast("Có lỗi khi xóa câu hỏi", ToastType.ERROR);
                    } else {
                        parent.remove(ChoiceSetting.this);

                        parent.revalidate();
                        parent.repaint();
                    }
                }
            });

            isCorrect = new JCheckBox();
            isCorrect.setBackground(Color.WHITE);
            isCorrect.setSelected(choice == null ? false : choice.getIsCorrect());

            media = new JLabel("Thêm hình ảnh");
            media.setBackground(Constants.gray02);
            media.setCursor(new Cursor(Cursor.HAND_CURSOR));
            media.setForeground(Color.BLUE);
            if (choice != null && choice.getMedia() != null) {
                media.setText("");
                media.setIcon(choice.getMedia());
            }
            media.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent me) {
                    ImageIcon ii = Tools.pickImage(getHeight() / 2, getWidth() / 2);

                    if (ii != null) {
                        media.setText(null);
                        media.setIcon(ii);
                    }
                }
            });

            text = new TextField("Đáp án", 14);
            text.setBorderColor(Constants.gray02);
            text.setText(choice == null ? "" : choice.getText());

            add(new Row(10,
                    isCorrect,
                    new Column(16, text, media),
                    delete));
        }

        public Choice getChoice(int questionId) {
            return new Choice(choice == null ? 0 : choice.getId(), text.getText(), (ImageIcon) media.getIcon(),
                    isCorrect.isSelected(),
                    questionId);
        }
    }
}
