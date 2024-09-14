package View.Account;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controller.AuthCtrl;
import Model.Account;
import Resources.Callback;
import Resources.Constants;
import Resources.Constants.FontType;
import View.Components.Button;
import View.Components.Center;
import View.Components.PasswordField;
import View.Components.RoundedPanel;
import View.Components.TextField;

public class Login extends JPanel {
    private Button loginButton;
    private JLabel notifyLabel;
    private RoundedPanel loginPanel;
    private PasswordField password;
    private TextField username;

    public Login() {
        super(new BorderLayout());

        JLabel label1 = new JLabel("Đăng nhập"), label2 = new JLabel("Chào mừng bạn quay trở lại");
        label1.setFont(Constants.getFont(FontType.QUICKSAND_BOLD).deriveFont(37f));

        label2.setFont(Constants.getFont(FontType.QUICKSAND_REGULAR).deriveFont(28f));

        notifyLabel = new JLabel();
        notifyLabel.setForeground(Color.RED);
        notifyLabel.setMaximumSize(new Dimension(378, 20));
        notifyLabel.setMinimumSize(new Dimension(378, 20));
        notifyLabel.setPreferredSize(new Dimension(378, 20));

        password = new PasswordField("Mật khẩu");
        password.setBorderColor(Constants.gray02);
        password.setMaximumSize(new Dimension(378, 60));
        password.setMinimumSize(new Dimension(378, 60));
        password.setPreferredSize(new Dimension(378, 60));
        password.setRadius(8);

        loginButton = new Button("Đăng nhập");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                login();
            }
        });
        loginButton.setBackground(Constants.blue01);
        loginButton.setFont(Constants.getFont(FontType.QUICKSAND_BOLD));
        loginButton.setForeground(Color.WHITE);
        loginButton.setMaximumSize(new Dimension(378, 60));
        loginButton.setMinimumSize(new Dimension(378, 60));
        loginButton.setPreferredSize(new Dimension(378, 60));

        username = new TextField("Tên đăng nhập", 16);
        username.setBorderColor(Constants.gray02);
        username.setMaximumSize(new Dimension(378, 60));
        username.setMinimumSize(new Dimension(378, 60));
        username.setPreferredSize(new Dimension(378, 60));
        username.setRadius(8);

        loginPanel = new RoundedPanel(18, Constants.gray02);
        loginPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setMaximumSize(new Dimension(578, 667));
        loginPanel.add(new Center(label1, BoxLayout.X_AXIS));
        loginPanel.add(Box.createVerticalStrut(16));
        loginPanel.add(new Center(label2, BoxLayout.X_AXIS));
        loginPanel.add(Box.createVerticalGlue());
        loginPanel.add(username);
        loginPanel.add(Box.createVerticalStrut(16));
        loginPanel.add(password);
        loginPanel.add(Box.createVerticalStrut(16));
        loginPanel.add(new Center(notifyLabel, BoxLayout.X_AXIS));
        loginPanel.add(Box.createVerticalStrut(16));
        loginPanel.add(new Center(loginButton, BoxLayout.X_AXIS));
        loginPanel.add(Box.createVerticalGlue());

        setBackground(Constants.neutral01);

        add(Box.createHorizontalStrut(467), BorderLayout.WEST);
        add(Box.createHorizontalStrut(467), BorderLayout.EAST);
        add(Box.createVerticalStrut(153), BorderLayout.NORTH);
        add(Box.createVerticalStrut(153), BorderLayout.SOUTH);
        add(loginPanel, BorderLayout.CENTER);
    }

    public void login() {
        if (password.getPassword().toString().isEmpty() || username.getText().isEmpty()) {
            notifyLabel.setText("Tài khoản và mật khẩu không được để trống");
            return;
        }

        Account user = AuthCtrl.login(username.getText(), new String(password.getPassword()));

        if (user != null) {
            Callback.userCallback.callbackUser(user);
        } else {
            notifyLabel.setText("Sai tài khoản hoặc mật khẩu");
        }
    }

}