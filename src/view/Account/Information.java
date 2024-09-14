package View.Account;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;

import java.util.Calendar;
import java.util.Date;

import javax.swing.*;

import Controller.AuthCtrl;
import Model.Account;
import Resources.Callback;
import Resources.Constants;
import Resources.Tools;
import Resources.Constants.FontType;
import Resources.Constants.ToastType;
import View.Components.*;

public class Information extends JPanel {
  private Button changeInfoButton;
  private JLabel avatar, notifyLabel;
  private JScrollPane scrollPane;
  private Spinner dob;
  private PasswordField password, password2;
  private RoundedPanel signUpPanel;
  private TextField _class, email, name, phoneNumber, school;

  private Account user;

  public Information(Account user) {
    super(new BorderLayout());
    this.user = user;

    JLabel label1 = new JLabel("Thông tin cá nhân"),
        label2 = new JLabel("Nếu không muốn thay đổi mật khẩu, hãy để trống");
    label1.setFont(Constants.getFont(FontType.QUICKSAND_BOLD).deriveFont(37f));
    label2.setFont(Constants.getFont(FontType.QUICKSAND_REGULAR).deriveFont(28f));

    avatar = new JLabel(
        user.getImage() == null ? Tools.resize(new ImageIcon(Constants.imagePath + "blank_avatar.png"), 100, 100)
            : user.getImage());
    avatar.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent me) {
        ImageIcon img = Tools.pickImage(100, 100);

        if (img != null) {
          avatar.setIcon(img);
        }
      }
    });

    _class = new TextField("Lớp", 16);
    _class.setBorderColor(Constants.gray02);
    _class.setMaximumSize(new Dimension(62, 60));
    _class.setMinimumSize(new Dimension(62, 60));
    _class.setPreferredSize(new Dimension(62, 60));
    _class.setRadius(8);
    _class.setText(user.getclass());

    Calendar calendar = Calendar.getInstance();

    calendar.add(Calendar.YEAR, -100);
    Date startDate = calendar.getTime();
    calendar.add(Calendar.YEAR, 94);
    Date endDate = calendar.getTime();

    dob = new Spinner(new SpinnerDateModel(user.getDob(), startDate, endDate, Calendar.DATE));
    dob.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
    dob.setEditor(new JSpinner.DateEditor(dob, "dd/MM/yyyy"));
    dob.setMaximumSize(new Dimension(295, 60));

    email = new TextField("Email (không bắt buộc)", 16);
    email.setBorderColor(Constants.gray02);
    email.setMaximumSize(new Dimension(378, 60));
    email.setMinimumSize(new Dimension(378, 60));
    email.setPreferredSize(new Dimension(378, 60));
    email.setRadius(8);
    email.setText(user.getEmail());

    name = new TextField("Họ và tên", 16);
    name.setBorderColor(Constants.gray02);
    name.setMaximumSize(new Dimension(262, 60));
    name.setMinimumSize(new Dimension(262, 60));
    name.setPreferredSize(new Dimension(262, 60));
    name.setRadius(8);
    name.setText(user.getName());

    notifyLabel = new JLabel();
    notifyLabel.setForeground(Color.RED);

    password = new PasswordField("Mật khẩu mới");
    password.setBorderColor(Constants.gray02);
    password.setMaximumSize(new Dimension(378, 60));
    password.setMinimumSize(new Dimension(378, 60));
    password.setPreferredSize(new Dimension(378, 60));
    password.setRadius(8);

    password2 = new PasswordField("Xác nhận mật khẩu mới");
    password2.setBorderColor(Constants.gray02);
    password2.setMaximumSize(new Dimension(378, 60));
    password2.setMinimumSize(new Dimension(378, 60));
    password2.setPreferredSize(new Dimension(378, 60));
    password2.setRadius(8);

    phoneNumber = new TextField("Số điện thoại (không bắt buộc)", 16);
    phoneNumber.setBorderColor(Constants.gray02);
    phoneNumber.setMaximumSize(new Dimension(378, 60));
    phoneNumber.setMinimumSize(new Dimension(378, 60));
    phoneNumber.setPreferredSize(new Dimension(378, 60));
    phoneNumber.setRadius(8);
    phoneNumber.setText(user.getPhoneNumber());

    school = new TextField("Trường", 16);
    school.setBorderColor(Constants.gray02);
    school.setMaximumSize(new Dimension(300, 60));
    school.setMinimumSize(new Dimension(300, 60));
    school.setPreferredSize(new Dimension(300, 60));
    school.setRadius(8);
    school.setText(user.getSchool());

    changeInfoButton = new Button("Xác nhận");
    changeInfoButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        signUp();
      }
    });
    changeInfoButton.setBackground(Constants.blue01);
    changeInfoButton.setFont(Constants.getFont(FontType.QUICKSAND_BOLD));
    changeInfoButton.setForeground(Color.WHITE);
    changeInfoButton.setMaximumSize(new Dimension(378, 60));
    changeInfoButton.setMinimumSize(new Dimension(378, 60));
    changeInfoButton.setPreferredSize(new Dimension(378, 60));

    signUpPanel = new RoundedPanel(18, Constants.gray02);
    signUpPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    signUpPanel.setLayout(new BoxLayout(signUpPanel, BoxLayout.Y_AXIS));
    signUpPanel.setMaximumSize(new Dimension(578, 0));
    signUpPanel.add(new Column(16,
        new Center(label1, BoxLayout.X_AXIS),
        new Center(label2, BoxLayout.X_AXIS)));

    signUpPanel.add(Box.createVerticalStrut(24));
    signUpPanel.add(new Column(16,
        password,
        password2,
        new JSeparator(JSeparator.HORIZONTAL),
        new Row(16, avatar, name),
        new Row(16, new JLabel("Ngày sinh"), dob),
        email,
        phoneNumber,
        new Row(16, school, _class),
        new Center(notifyLabel, BoxLayout.X_AXIS),
        new Center(changeInfoButton, BoxLayout.X_AXIS)));
    signUpPanel.add(Box.createVerticalGlue());

    scrollPane = new JScrollPane(signUpPanel);
    scrollPane.getVerticalScrollBar().setBackground(Color.WHITE);
    scrollPane.getVerticalScrollBar().setUnitIncrement(20);
    scrollPane.setBackground(Color.WHITE);
    scrollPane.setBorder(null);

    setBackground(Constants.neutral01);

    add(Box.createHorizontalStrut(467), BorderLayout.WEST);
    add(Box.createHorizontalStrut(467), BorderLayout.EAST);
    add(Box.createVerticalStrut(80), BorderLayout.NORTH);
    add(Box.createVerticalStrut(80), BorderLayout.SOUTH);
    add(scrollPane, BorderLayout.CENTER);
  }

  public void signUp() {
    if (!(new String(password.getPassword())).equals(new String(password2.getPassword()))) {
      notifyLabel.setText("Xác nhận mật khẩu không trùng khớp");
    } else if (name.getText().isEmpty()) {
      notifyLabel.setText("Họ tên không được để trống");
    } else if (school.getText().isEmpty()) {
      notifyLabel.setText("Không được để trống tên trường");
    } else if (!Tools.checkPhone(phoneNumber.getText())) {
      notifyLabel.setText("Số định dạng điện thoại không hợp lệ");
    } else if (!Tools.checkEmail(email.getText())) {
      notifyLabel.setText("Email không hợp lệ");
    } else {
      notifyLabel.setText("");

      String _password = new String(password.getPassword());

      if (!_password.isBlank()) {
        if (AuthCtrl.changePassword(user.getId(), _password)) {
          Callback.toastCallback.callbackToast("Đổi mật khẩu thành công!", ToastType.SUCCESS);
        } else {
          Callback.toastCallback.callbackToast("Đổi mật khẩu thất bại!", ToastType.SUCCESS);
        }
      }

      String _phoneNumber = phoneNumber.getText().isBlank() ? null : phoneNumber.getText();
      String _email = email.getText().isBlank() ? null : email.getText();
      String __class = _class.getText().isBlank() ? null : _class.getText();

      user.setName(name.getText());
      user.setDob(Tools.toSqlDate(dob.getValue().toString()));
      user.setPhoneNumber(_phoneNumber);
      user.setEmail(_email);
      user.setSchool(school.getText());
      user.setClass(__class);
      user.setImage((ImageIcon) avatar.getIcon());

      boolean status = AuthCtrl.changeInfomation(user);

      if (status) {
        Callback.toastCallback.callbackToast("Thay đổi thông tin thành công!", ToastType.SUCCESS);
        Callback.userCallback.callbackUser(user);
      } else {
        Callback.toastCallback.callbackToast("Có lỗi xảy ra", ToastType.ERROR);
      }
    }
  }
}
