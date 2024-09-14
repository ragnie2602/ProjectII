package View.Exam;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Controller.ExamCtrl;
import Model.Choice;
import Model.Exam;
import Model.Question;
import Resources.Callback;
import Resources.Constants;
import Resources.Constants.FontType;
import Resources.Constants.ToastType;
import View.Components.Button;
import View.Components.Column;
import View.Components.Row;
import View.Components.Spinner;
import View.Components.TextField;

public class EditExam extends JPanel {
    Button cancel, confirm;
    TextField name, description, subject;
    Spinner open, close, easies, easyPts, hards, hardPts, mediums, mediumPts, duration;
    JCheckBox repeat, reviewed;
    JLabel total, totalPts;
    JPanel rightPanel = new JPanel();

    public EditExam(Exam exam, JFrame prevFrame) {
        super();

        setBorder(BorderFactory.createEmptyBorder(0, 40, 20, 40));
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(1, 2, 20, 10));

        Calendar calendar = Calendar.getInstance();

        cancel = new Button("Hủy");
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Callback.manageExamCallback.manageExam();
            }
        });
        cancel.setBackground(Constants.gray02);
        cancel.setForeground(Color.BLACK);
        cancel.setMargin(new Insets(12, 24, 12, 24));

        confirm = new Button("Sửa");
        confirm.setBackground(Constants.blue01);
        confirm.setForeground(Color.WHITE);
        confirm.setMargin(new Insets(12, 24, 12, 24));
        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                edit(exam.getId(), rightPanel.getComponents());
            }
        });

        calendar.add(Calendar.YEAR, 100);
        Date endDate = calendar.getTime();

        close = new Spinner(new SpinnerDateModel(exam.getCloseTime(), exam.getCloseTime(), endDate, Calendar.MINUTE));
        close.setBorderColor(Constants.gray02);
        close.setEditor(new Spinner.DateEditor(close, "dd/MM/yyyy HH:mm"));
        close.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        open = new Spinner(new SpinnerDateModel(exam.getOpenTime(), exam.getOpenTime(), endDate, Calendar.MINUTE));
        open.setBorderColor(Constants.gray02);
        open.setEditor(new Spinner.DateEditor(open, "dd/MM/yyyy HH:mm"));
        open.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        description = new TextField("Mô tả đề thi", 16);
        description.setBorderColor(Constants.gray02);
        description.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        description.setText(exam.getDescription());

        duration = new Spinner(new SpinnerNumberModel(exam.getDuration(), 1, 1440, 5));
        duration.setMinimumSize(new Dimension(200, 40));
        duration.setMaximumSize(new Dimension(200, 40));
        duration.setPreferredSize(new Dimension(200, 40));

        easies = new Spinner(new SpinnerNumberModel(exam.getEasies(), 1, 1440, 1));
        easies.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent ce) {
                changeTotalQuestions();
            }
        });
        easies.setMaximumSize(new Dimension(200, 40));
        easyPts = new Spinner(new SpinnerNumberModel(exam.getEasyPts(), 0.0, 100, 0.05));
        easyPts.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent ce) {
                changeTotalQuestions();
            }
        });
        easyPts.setMaximumSize(new Dimension(200, 40));

        hards = new Spinner(new SpinnerNumberModel(exam.getHards(), 1, 1440, 1));
        hards.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent ce) {
                changeTotalQuestions();
            }
        });
        hards.setMaximumSize(new Dimension(200, 40));
        hardPts = new Spinner(new SpinnerNumberModel(exam.getHardPts(), 0.0, 100, 0.05));
        hardPts.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent ce) {
                changeTotalQuestions();
            }
        });
        hardPts.setMaximumSize(new Dimension(200, 40));

        mediums = new Spinner(new SpinnerNumberModel(exam.getMediums(), 1, 1440, 1));
        mediums.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent ce) {
                changeTotalQuestions();
            }
        });
        mediums.setMaximumSize(new Dimension(200, 40));
        mediumPts = new Spinner(new SpinnerNumberModel(exam.getMediumPts(), 0.0, 100, 0.05));
        mediumPts.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent ce) {
                changeTotalQuestions();
            }
        });
        mediumPts.setMaximumSize(new Dimension(200, 40));

        name = new TextField("Tên đề thi", 16);
        name.setBorderColor(Constants.gray02);
        name.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        name.setText(exam.getName());

        total = new JLabel(
                "Tổng: " + ((int) easies.getValue() + (int) mediums.getValue() + (int) hards.getValue()));
        totalPts = new JLabel(
                "Tổng điểm: " + ((int) easies.getValue() * (double) easyPts.getValue()
                        + (int) mediums.getValue() * (double) mediumPts.getValue()
                        + (int) hards.getValue() * (double) hardPts.getValue()));

        JLabel label1 = new JLabel("Sửa đề thi"), label2 = new JLabel("Thông tin chung"),
                label3 = new JLabel("Kho câu hỏi");
        label1.setFont(Constants.getFont(FontType.QUICKSAND_BOLD).deriveFont(70f));
        label2.setFont(Constants.getFont(FontType.ROBOTO_REGULAR).deriveFont(30f));
        label3.setFont(Constants.getFont(FontType.ROBOTO_REGULAR).deriveFont(30f));

        repeat = new JCheckBox("Cho phép làm lại");
        repeat.setSelected(exam.getCanRepeat());

        reviewed = new JCheckBox("Cho phép xem đáp án");
        reviewed.setSelected(exam.getCanReviewed());

        subject = new TextField("Môn học", 16);
        subject.setBorderColor(Constants.gray02);
        subject.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        subject.setText(exam.getSubject());

        panel.add(new Column(16,
                new Row(16, label2),
                name,
                description,
                subject,
                new Row(16, repeat, reviewed),
                Box.createVerticalStrut(16),
                new Row(16, new JLabel("Thời gian mở   "),
                        open,
                        Box.createHorizontalStrut(16),
                        new JLabel("Thời gian đóng   "),
                        close),
                new Row(0, new JLabel("Thời lượng làm bài   "), duration, new JLabel(" phút"),
                        Box.createHorizontalGlue()),
                Box.createVerticalStrut(16),
                new Row(16,
                        new JLabel("Sô lượng câu hỏi:\t"),
                        new JLabel("Dễ:"), easies,
                        new JLabel("Trung bình:"), mediums,
                        new JLabel("Khó:"), hards),
                new Row(16,
                        new JLabel("Điểm / câu:\t"),
                        new JLabel("Dễ:"), easyPts,
                        new JLabel("Trung bình:"), mediumPts,
                        new JLabel("Khó:"), hardPts),
                new Row(0, total, Box.createHorizontalStrut(12), totalPts),
                new Row(16, cancel, confirm),
                Box.createVerticalGlue()));

        // QUESTION BANK
        Map<Question, ArrayList<Choice>> questionBank = ExamCtrl.getAllQuestion(exam.getId());

        JScrollPane scrollPane = new JScrollPane(rightPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        panel.add(scrollPane);

        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(new Row(0, label3));
        for (Map.Entry<Question, ArrayList<Choice>> entry : questionBank.entrySet()) {
            rightPanel.add(new EditQuestion(prevFrame, rightPanel, entry.getKey(), entry.getValue()));
        }
        rightPanel.add(new AddQuestion(rightPanel));

        add(label1, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
    }

    private void changeTotalQuestions() {
        total.setText(
                "Tổng: " + ((int) easies.getValue() + (int) mediums.getValue() + (int) hards.getValue()));
        totalPts.setText(
                "Tổng điểm: " + ((int) easies.getValue() * (double) easyPts.getValue()
                        + (int) mediums.getValue() * (double) mediumPts.getValue()
                        + (int) hards.getValue() * (double) hardPts.getValue()));
    }

    void edit(int examId, Component[] questions) {
        if (name.getText().isBlank()) {
            Callback.toastCallback.callbackToast("Tên đề thi không được rỗng", ToastType.ERROR);
            return;
        }
        if (description.getText().isBlank()) {
            Callback.toastCallback.callbackToast("Bạn đang để trống mô tả", ToastType.WARNING);
        }
        if (((Date) open.getValue()).after((Date) close.getValue())) {
            Callback.toastCallback.callbackToast("Thời gian đóng đề thi phải sau thời gian mở đề", ToastType.ERROR);
            return;
        }
        if (Integer.parseInt(duration.getValue().toString()) <= 0) {
            Callback.toastCallback.callbackToast("Thời gian làm bài không hợp lệ", ToastType.ERROR);
            return;
        }

        boolean status = ExamCtrl.editExam(new Exam(
                examId,
                name.getText(),
                description.getText(),
                ((Date) open.getValue()),
                ((Date) close.getValue()),
                subject.getText(),
                Integer.parseInt(duration.getValue().toString()),
                repeat.isSelected(),
                reviewed.isSelected(),
                Integer.parseInt(easies.getValue().toString()),
                Float.parseFloat(easyPts.getValue().toString()),
                Integer.parseInt(mediums.getValue().toString()),
                Float.parseFloat(mediumPts.getValue().toString()),
                Integer.parseInt(hards.getValue().toString()),
                Float.parseFloat(hardPts.getValue().toString()),
                0));

        if (status) {
            Callback.toastCallback.callbackToast("Sửa đề thi thành công", ToastType.SUCCESS);

            int sz = rightPanel.getComponentCount() - 1;

            for (int i = 1; i < sz; i++) {
                Component eq = rightPanel.getComponent(i);

                if (eq instanceof AddQuestion)
                    ((AddQuestion) eq).createQuestion(examId);
                else
                    ((EditQuestion) eq).editQuestion();
            }
            Callback.manageExamCallback.manageExam();
        } else {
            Callback.toastCallback.callbackToast("Sửa đề thi thất bại", ToastType.ERROR);
        }
    }
}
