package View.Results;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JSeparator;

import Controller.ExamCtrl;
import Model.Attempt;
import Model.Exam;
import Resources.Constants;
import View.Components.Column;
import View.Components.RoundedPanel;
import View.Components.Row;

public class ResultItem extends RoundedPanel {
        public ResultItem(Attempt attempt) {
                super(16, Constants.gray02);

                Exam exam = ExamCtrl.getExam(attempt.getExamId());

                JLabel total = new JLabel("<html>Điểm: <b>" + attempt.getGrade() + "</b></html>",
                                new ImageIcon(Constants.imagePath + "ic_medal.png"), JLabel.LEFT);

                total.setForeground(Constants.red01);

                add(new Column(0,
                                new Column(10,
                                                new Row(0, new JLabel("<html><b>" + exam.getName() + "</b></html>"),
                                                                Box.createHorizontalGlue()),
                                                new Row(12,
                                                                new JLabel("Tổng điểm: " + exam.getTotal(),
                                                                                new ImageIcon(Constants.imagePath
                                                                                                + "ic_medal.png"),
                                                                                JLabel.LEFT),
                                                                new JLabel("Thời lượng: " + (exam.getDuration())
                                                                                + " phút",
                                                                                new ImageIcon(Constants.imagePath
                                                                                                + "ic_time.png"),
                                                                                JLabel.LEFT),
                                                                Box.createHorizontalGlue())),
                                new JSeparator(),
                                Box.createVerticalStrut(5),
                                new Column(10,
                                                new Row(0, new JLabel("Thời gian bắt đầu: " + attempt.getTimeStart(),
                                                                new ImageIcon(Constants.imagePath + "ic_time.png"),
                                                                JLabel.LEFT),
                                                                Box.createHorizontalGlue()),
                                                new Row(0, new JLabel("Thời gian làm bài: " + attempt.getDuration(),
                                                                new ImageIcon(Constants.imagePath + "ic_time.png"),
                                                                JLabel.LEFT),
                                                                Box.createHorizontalGlue()),
                                                new Row(0, total, Box.createHorizontalGlue()))));
        }
}
