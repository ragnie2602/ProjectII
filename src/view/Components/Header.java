package View.Components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import Model.Account;
import Model.Exam;
import Resources.Callback;
import Resources.Constants;
import Resources.Tools;
import Resources.Constants.ToastType;
import View.Homepage;
import View.Account.CheckPassword;
import View.Account.Information;
import View.Account.Login;
import View.Account.SignUp;
import View.Exam.AddExam;
import View.Exam.EditExam;
import View.Exam.ManageExam;
import View.Exam.ViewExam;
import View.Results.ViewResults;

public class Header extends JPanel {
    private Button home, login, manageExam, signup, viewResults;
    private int isSelected = 0;
    private JLabel account;
    private JPanel leftPanel, rightPanel;
    private JPopupMenu popupMenu;
    private Account user;

    public Header(JFrame parentFrame, GridBagConstraints gbc) {
        super(new BorderLayout());

        JMenuItem information = new JMenuItem("Tài khoản", new ImageIcon(Constants.imagePath + "ic_account.png")),
                signOut = new JMenuItem("Đăng xuất", new ImageIcon(Constants.imagePath + "ic_sign_out.png"));

        Callback.createExamCallback = () -> {
            parentFrame.getContentPane().remove(1);
            parentFrame.getContentPane().add(new AddExam(user), gbc);

            parentFrame.revalidate();
            parentFrame.repaint();
        };

        Callback.editExamCallback = (Exam exam) -> {
            parentFrame.getContentPane().remove(1);
            parentFrame.getContentPane().add(new EditExam(exam, parentFrame), gbc);

            parentFrame.revalidate();
            parentFrame.repaint();
        };

        Callback.homepageCallback = () -> {
            parentFrame.getContentPane().remove(1);
            parentFrame.getContentPane().add(new Homepage(), gbc);
            parentFrame.revalidate();
            parentFrame.repaint();
        };

        Callback.informationCallback = () -> {
            parentFrame.getContentPane().remove(1);
            parentFrame.getContentPane().add(new Information(user), gbc);

            parentFrame.revalidate();
            parentFrame.repaint();
        };

        Callback.manageExamCallback = () -> {
            parentFrame.getContentPane().remove(1);
            parentFrame.getContentPane().add(new ManageExam(user), gbc);

            parentFrame.revalidate();
            parentFrame.repaint();
        };

        Callback.viewExamCallback = (exam) -> {
            if (user == null) {
                Callback.toastCallback.callbackToast("Bạn chưa đăng nhập!", ToastType.WARNING);
                return;
            } else if (user.getRole() == 1) {
                Callback.toastCallback.callbackToast("Bạn không phải là HSSV!", ToastType.WARNING);
                return;
            }

            isSelected = -1;
            changeUI();

            parentFrame.getContentPane().remove(1);
            parentFrame.getContentPane().add(new ViewExam(exam, user.getId(), parentFrame), gbc);

            parentFrame.revalidate();
            parentFrame.repaint();
        };

        Callback.userCallback = (user) -> {
            this.user = user;

            isSelected = 0;
            changeUI();

            account = new JLabel(user.getName(),
                    Tools.resize(user.getImage(), (int) (getHeight() * 0.6), (int) (getHeight() * 0.6)), JLabel.CENTER);
            account.setCursor(new Cursor(Cursor.HAND_CURSOR));
            account.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent me) {
                    popupMenu.show(account, 0, account.getHeight());
                }
            });

            rightPanel.removeAll();

            rightPanel.add(home);
            if (user.getRole() == 1)
                rightPanel.add(manageExam);
            else
                rightPanel.add(viewResults);
            rightPanel.add(account);

            rightPanel.revalidate();
            rightPanel.repaint();

            parentFrame.getContentPane().remove(1);
            parentFrame.getContentPane().add(new Homepage(), gbc);
            parentFrame.revalidate();
            parentFrame.repaint();
        };

        information.addActionListener(e -> {
            new CheckPassword(parentFrame, user.getId());
        });

        home = new Button("Trang chủ");
        home.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                parentFrame.getContentPane().remove(1);
                parentFrame.getContentPane().add(new Homepage(), gbc);

                isSelected = 0;
                changeUI();

                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });
        home.setBackground(Color.WHITE);

        login = new Button("Đăng nhập");
        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                parentFrame.getContentPane().remove(1);
                parentFrame.getContentPane().add(new Login(), gbc);

                isSelected = -1;
                changeUI();

                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });
        login.setMargin(new Insets(12, 24, 12, 24));

        manageExam = new Button("Quản lý đề thi");
        manageExam.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                parentFrame.getContentPane().remove(1);
                parentFrame.getContentPane().add(new ManageExam(user), gbc);

                isSelected = 1;
                changeUI();

                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });
        manageExam.setBackground(Color.WHITE);

        popupMenu = new JPopupMenu();
        popupMenu.add(information);
        popupMenu.add(signOut);

        signOut.addActionListener(e -> {
            rightPanel.remove(account);
            if (user.getRole() == 1) {
                rightPanel.remove(manageExam);
                rightPanel.remove(viewResults);
            }
            user = null;
            rightPanel.add(login);
            rightPanel.add(signup);
            rightPanel.revalidate();
            rightPanel.repaint();

            isSelected = 0;
            changeUI();

            parentFrame.getContentPane().remove(1);
            parentFrame.getContentPane().add(new Homepage(), gbc);
            parentFrame.revalidate();
            parentFrame.repaint();
        });

        signup = new Button("Đăng ký");
        signup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                parentFrame.getContentPane().remove(1);
                parentFrame.getContentPane().add(new SignUp(), gbc);

                isSelected = -1;
                changeUI();

                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });
        signup.setBackground(Constants.blue01);
        signup.setForeground(Color.WHITE);
        signup.setMargin(new Insets(12, 24, 12, 24));

        viewResults = new Button("Xem kết quả");
        viewResults.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                parentFrame.getContentPane().remove(1);
                parentFrame.getContentPane().add(new ViewResults(user.getId()), gbc);

                isSelected = 2;
                changeUI();

                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });
        viewResults.setBackground(Color.WHITE);

        changeUI();

        leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.add(new JLabel(Tools.resize(new ImageIcon(Constants.imagePath + "logo.png"), 80, 288)));

        rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.add(home);
        rightPanel.add(login);
        rightPanel.add(signup);

        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(25, 121, 25, 121));
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
    }

    void changeUI() {
        if (isSelected == 0) {
            home.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Constants.blue01));
            home.setFont(home.getFont().deriveFont(Font.BOLD));

            manageExam.setBorder(null);
            manageExam.setFont(manageExam.getFont().deriveFont(Font.PLAIN));
        } else if (isSelected == 1) {
            home.setBorder(null);
            home.setFont(home.getFont().deriveFont(Font.PLAIN));

            manageExam.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Constants.blue01));
            manageExam.setFont(manageExam.getFont().deriveFont(Font.BOLD));
        } else if (isSelected == 2) {
            home.setBorder(null);
            home.setFont(home.getFont().deriveFont(Font.PLAIN));

            viewResults.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Constants.blue01));
            viewResults.setFont(manageExam.getFont().deriveFont(Font.BOLD));
        } else {
            home.setBorder(null);
        }

        revalidate();
        repaint();
    }
}
