package View.Exam;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.*;

import Controller.ExamCtrl;
import Model.Account;
import Model.Exam;
import Resources.Callback;
import Resources.Constants;
import Resources.Tools;
import Resources.Constants.FontType;
import Resources.Constants.ToastType;
import View.Components.Button;
import View.Components.Column;
import View.Components.RoundedPanel;
import View.Components.Row;

public class ManageExam extends JPanel {
    private Button addExam;

    public ManageExam(Account user) {
        super();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));
        setLayout(new BorderLayout());

        JLabel label1 = new JLabel("Quản lý đề thi");
        label1.setFont(Constants.getFont(FontType.QUICKSAND_BOLD).deriveFont(70f));
        add(label1, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(0, 2, 16, 8));

        addExam = new Button("Tạo đề thi", Tools.resize(new ImageIcon(Constants.imagePath + "ic_add.png"), 40, 40));
        addExam.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Callback.createExamCallback.createExam();
            }
        });
        addExam.setPreferredSize(new Dimension(0, 60));
        panel.add(addExam);

        for (Exam exam : ExamCtrl.getAllExam(user.getId())) {
            RoundedPanel tmpPanel = new RoundedPanel(16), tmp2Panel = new RoundedPanel(0);
            tmpPanel.setBorderColor(Constants.gray02);
            tmpPanel.setLayout(new GridLayout(1, 1));

            JLabel name = new JLabel(exam.getName());
            name.setFont(name.getFont().deriveFont(Font.BOLD).deriveFont(20.0f));

            Button edit = new Button("Sửa"), delete = new Button("Xóa");
            delete.setBackground(Constants.red01);
            delete.setForeground(Color.WHITE);
            delete.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    if (ExamCtrl.deleteExam(exam.getId())) {
                        Callback.toastCallback.callbackToast("Xóa đề thi thành công", ToastType.SUCCESS);
                        Callback.manageExamCallback.manageExam();
                    } else {
                        Callback.toastCallback.callbackToast("Xóa đề thi thất bại", ToastType.ERROR);
                    }
                }
            });
            edit.setBackground(Constants.blue01);
            edit.setForeground(Color.WHITE);
            edit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    Callback.editExamCallback.editExam(exam);
                }
            });

            tmp2Panel.setLayout(new GridLayout(1, 1, 12, 12));
            tmp2Panel.add(new JLabel("<html><nobr><i>Số câu</i><br><b>"
                    + (exam.getEasies() + exam.getMediums() + exam.getHards()) + "</b></nobr></html>"));

            tmpPanel.add(new Row(16,
                    Box.createHorizontalStrut(12),
                    new Column(12,
                            Box.createVerticalStrut(12),
                            new Row(0, name, Box.createHorizontalGlue()),
                            new Row(0, new JLabel(exam.getDescription()), Box.createHorizontalGlue()),
                            new JSeparator(JSeparator.HORIZONTAL),
                            new Row(12,
                                    new JLabel("<html>Thời gian mở:   <b><i>"
                                            + simpleDateFormat.format(exam.getOpenTime()) + "</i></b></html>"),
                                    new JLabel("<html>-<b><i>" + simpleDateFormat.format(exam.getCloseTime())
                                            + "</i></b></html>")),
                            Box.createVerticalGlue(),
                            new Row(16, Box.createHorizontalGlue(), edit, delete),
                            Box.createVerticalStrut(12)),
                    new Column(0, Box.createVerticalGlue(), tmp2Panel, Box.createVerticalGlue()),
                    Box.createHorizontalStrut(12)));

            panel.add(tmpPanel);
        }

        if (panel.getComponentCount() < 10) {
            int tmp = panel.getComponentCount();
            for (int i = 0; i < 10 - tmp; i++) {
                JLabel label = new JLabel();

                panel.add(label);
            }
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }
}
