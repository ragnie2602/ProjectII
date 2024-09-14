package View.Account;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Controller.AuthCtrl;
import Resources.Callback;
import Resources.Constants;
import Resources.Constants.ToastType;
import View.Components.Button;
import View.Components.Column;
import View.Components.PasswordField;
import View.Components.Row;

public class CheckPassword extends JDialog {
  private Button check;
  private PasswordField password;

  public CheckPassword(JFrame parentFrame, int userId) {
    super(parentFrame, "Xác thực", true);

    JPanel panel = new JPanel(new GridLayout(1, 1));

    setBackground(Color.WHITE);
    setLayout(new GridLayout(1, 1));
    setBounds(parentFrame.getX() + parentFrame.getWidth() / 2 - 225,
        parentFrame.getY() + parentFrame.getHeight() / 2 - 80, 450, 160);

    add(panel);

    check = new Button("Xác nhận");
    check.setBackground(Constants.blue01);
    check.setForeground(Color.WHITE);
    check.addActionListener(e -> {
      if (!AuthCtrl.checkPassword(userId, new String(password.getPassword()))) {
        setVisible(false);
        Callback.toastCallback.callbackToast("Sai mật khẩu", ToastType.ERROR);
      } else {
        Callback.informationCallback.showInformation();
        setVisible(false);
      }
    });

    password = new PasswordField("Mật khẩu");
    password.setBorderColor(Constants.gray02);

    panel.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));
    panel.add(new Column(14, password,
        new Row(0, Box.createHorizontalGlue(), check, Box.createHorizontalGlue())));

    setVisible(true);
  }
}