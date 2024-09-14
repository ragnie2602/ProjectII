package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;

import Controller.AuthCtrl;
import Controller.ExamCtrl;
import Model.Account;
import Model.Exam;
import Resources.Callback;
import Resources.Constants;
import Resources.Constants.FontType;
import View.Components.Button;
import View.Components.Column;
import View.Components.RoundedPanel;
import View.Components.Row;
import View.Components.TextField;

public class Homepage extends JPanel {
    JPanel leftPanel = new JPanel();
    TextField searchBox = new TextField(new ImageIcon(Constants.imagePath + "search.png"),
            "Tìm kiếm đề thi", 14);

    public Homepage() {
        super();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 0));
        setLayout(new GridLayout(1, 2, 0, 10));

        JLabel label1 = new JLabel(
                "<html><div>Cùng ôn luyện các bài</div><div>thi trắc nghiệm với IntelliQuiz</div></html>");
        label1.setFont(Constants.getFont(FontType.QUICKSAND_BOLD).deriveFont(70f));
        label1.setHorizontalAlignment(JLabel.CENTER);

        searchBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {
                for (int i = 0; i < leftPanel.getComponentCount(); i++) {
                    if (leftPanel.getComponent(i) instanceof RoundedPanel) {
                        leftPanel.remove(i);
                    }
                }

                if (!searchBox.getText().isEmpty()) {
                    if (leftPanel.getComponent(0) instanceof Box.Filler) {
                        leftPanel.remove(0);
                    }

                    ArrayList<Exam> exams = ExamCtrl.searchExam(searchBox.getText());
                    for (Exam exam : exams) {
                        RoundedPanel tmpPanel = new RoundedPanel(16), tmp2Panel = new RoundedPanel(0);
                        tmpPanel.setBackground(Constants.neutral02);
                        tmpPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                        tmpPanel.setLayout(new GridLayout(1, 1));

                        Account account = AuthCtrl.getUser(exam.getTeacherId());
                        Button view = new Button("Xem");
                        JLabel name = new JLabel(exam.getName());

                        name.setFont(name.getFont().deriveFont(Font.BOLD).deriveFont(20.0f));

                        view.setBackground(Constants.blue01);
                        view.setForeground(Color.WHITE);
                        view.addActionListener(e -> {
                            Callback.viewExamCallback.viewExam(exam);
                        });

                        tmp2Panel.setOpaque(false);
                        tmp2Panel.setLayout(new GridLayout(1, 1, 12, 12));
                        tmp2Panel.add(new JLabel("<html><nobr><i>Số câu</i><br><b>"
                                + (exam.getEasies() + exam.getMediums() + exam.getHards()) +
                                "</b></nobr></html>"));

                        tmpPanel.add(new Row(16,
                                new Column(12,
                                        new Row(0, name, Box.createHorizontalGlue()),
                                        new Row(0, new JLabel(exam.getDescription()), Box.createHorizontalGlue()),
                                        new JSeparator(JSeparator.HORIZONTAL),
                                        new Row(12,
                                                new JLabel("<html>Thời gian mở: <b><i>"
                                                        + simpleDateFormat.format(exam.getOpenTime())
                                                        + "</i></b></html>"),
                                                new JLabel(
                                                        "<html>-<b><i>" + simpleDateFormat.format(exam.getCloseTime())
                                                                + "</i></b></html>")),
                                        Box.createVerticalGlue(),
                                        new Row(16,
                                                new JLabel("<html>GV: <b>" + account.getName() + "</b> - "
                                                        + account.getSchool() + "</html>"),
                                                Box.createHorizontalGlue(),
                                                view)),
                                new Column(0, Box.createVerticalGlue(), tmp2Panel,
                                        Box.createVerticalGlue())));

                        leftPanel.add(tmpPanel, leftPanel.getComponentCount() - 1);
                    }
                } else {
                    if (!(leftPanel.getComponent(0) instanceof Box.Filler)) {
                        leftPanel.add(Box.createVerticalGlue(), 0);
                    }
                }

                leftPanel.revalidate();
                leftPanel.repaint();
            }
        });
        searchBox.setBackground(Constants.gray01);
        searchBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        searchBox.setPreferredSize(new Dimension(600, 60));
        searchBox.setRadius(5);

        leftPanel.setBackground(Color.WHITE);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(new Row(0, label1, Box.createHorizontalGlue()));
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(searchBox);
        leftPanel.add(Box.createVerticalGlue());

        JScrollPane leftScrollPane = new JScrollPane(leftPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        leftScrollPane.setBorder(null);

        add(leftScrollPane);
        add(new JLabel(new ImageIcon(Constants.imagePath + "homePage.png")));
    }
}
