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

public class SignUp extends JPanel {
    private Button signUpButton;
    private JLabel avatar, notifyLabel;
    private JRadioButton student, teacher;
    private JScrollPane scrollPane;
    private Spinner dob;
    private PasswordField password;
    private RoundedPanel signUpPanel;
    private TextField _class, email, name, phoneNumber, school, username;

    public SignUp() {
        super(new BorderLayout());

        ButtonGroup roleGroup;

        JLabel label1 = new JLabel("Đăng ký"), label2 = new JLabel("Hãy gia nhập với chúng tôi");
        label1.setFont(Constants.getFont(FontType.QUICKSAND_BOLD).deriveFont(37f));
        label2.setFont(Constants.getFont(FontType.QUICKSAND_REGULAR).deriveFont(28f));

        avatar = new JLabel(Tools.resize(new ImageIcon(Constants.imagePath + "blank_avatar.png"), 100, 100));
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

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.YEAR, -100);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.YEAR, 94);
        Date initialDate = calendar.getTime();
        Date endDate = calendar.getTime();

        dob = new Spinner(new SpinnerDateModel(initialDate, startDate, endDate, Calendar.DATE));
        dob.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        dob.setEditor(new JSpinner.DateEditor(dob, "dd/MM/yyyy"));
        dob.setMaximumSize(new Dimension(295, 60));

        email = new TextField("Email (không bắt buộc)", 16);
        email.setBorderColor(Constants.gray02);
        email.setMaximumSize(new Dimension(378, 60));
        email.setMinimumSize(new Dimension(378, 60));
        email.setPreferredSize(new Dimension(378, 60));
        email.setRadius(8);

        name = new TextField("Họ và tên", 16);
        name.setBorderColor(Constants.gray02);
        name.setMaximumSize(new Dimension(262, 60));
        name.setMinimumSize(new Dimension(262, 60));
        name.setPreferredSize(new Dimension(262, 60));
        name.setRadius(8);

        notifyLabel = new JLabel();
        notifyLabel.setForeground(Color.RED);

        password = new PasswordField("Mật khẩu");
        password.setBorderColor(Constants.gray02);
        password.setMaximumSize(new Dimension(378, 60));
        password.setMinimumSize(new Dimension(378, 60));
        password.setPreferredSize(new Dimension(378, 60));
        password.setRadius(8);

        phoneNumber = new TextField("Số điện thoại (không bắt buộc)", 16);
        phoneNumber.setBorderColor(Constants.gray02);
        phoneNumber.setMaximumSize(new Dimension(378, 60));
        phoneNumber.setMinimumSize(new Dimension(378, 60));
        phoneNumber.setPreferredSize(new Dimension(378, 60));
        phoneNumber.setRadius(8);

        school = new TextField("Trường", 16);
        school.setBorderColor(Constants.gray02);
        school.setMaximumSize(new Dimension(300, 60));
        school.setMinimumSize(new Dimension(300, 60));
        school.setPreferredSize(new Dimension(300, 60));
        school.setRadius(8);

        signUpButton = new Button("Đăng ký");
        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                signUp();
            }
        });
        signUpButton.setBackground(Constants.blue01);
        signUpButton.setFont(Constants.getFont(FontType.QUICKSAND_BOLD));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setMaximumSize(new Dimension(378, 60));
        signUpButton.setMinimumSize(new Dimension(378, 60));
        signUpButton.setPreferredSize(new Dimension(378, 60));

        student = new JRadioButton("Học sinh / Sinh viên");
        student.setSelected(true);
        teacher = new JRadioButton("Giáo viên / Giảng viên");
        roleGroup = new ButtonGroup();
        roleGroup.add(student);
        roleGroup.add(teacher);

        username = new TextField("Tên đăng nhập", 16);
        username.setBorderColor(Constants.gray02);
        username.setMaximumSize(new Dimension(378, 60));
        username.setMinimumSize(new Dimension(378, 60));
        username.setPreferredSize(new Dimension(378, 60));
        username.setRadius(8);

        signUpPanel = new RoundedPanel(18, Constants.gray02);
        signUpPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        signUpPanel.setLayout(new BoxLayout(signUpPanel, BoxLayout.Y_AXIS));
        signUpPanel.setMaximumSize(new Dimension(578, 0));
        signUpPanel.add(new Column(16,
                new Center(label1, BoxLayout.X_AXIS),
                new Center(label2, BoxLayout.X_AXIS)));

        signUpPanel.add(Box.createVerticalStrut(24));
        signUpPanel.add(new Column(16,
                username,
                password,
                new JSeparator(JSeparator.HORIZONTAL),
                new Row(16, avatar, name),
                new Row(16, new JLabel("Ngày sinh"), dob),
                email,
                phoneNumber,
                new Row(16, school, _class),
                new Row(16, new JLabel("Bạn là: "), student, teacher),
                new Center(notifyLabel, BoxLayout.X_AXIS),
                new Center(signUpButton, BoxLayout.X_AXIS)));
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
        if (username.getText().isEmpty()) {
            notifyLabel.setText("Tên đăng nhập không được để trống");
        } else if (password.getPassword().length == 0) {
            notifyLabel.setText("Mật khẩu không được để trống");
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

            String _phoneNumber = phoneNumber.getText().isBlank() ? null : phoneNumber.getText();
            String _email = email.getText().isBlank() ? null : email.getText();
            String __class = _class.getText().isBlank() ? null : _class.getText();

            int status = AuthCtrl.signUp(new Account(
                    student.isSelected() ? 0 : 1, name.getText(),
                    Tools.toSqlDate(dob.getValue().toString()),
                    _phoneNumber,
                    _email,
                    school.getText(),
                    __class,
                    (ImageIcon) avatar.getIcon()),
                    username.getText(),
                    new String(password.getPassword()));

            if (status == 0) {
                Callback.toastCallback.callbackToast("Đăng ký thành công, hãy đăng nhập để tiếp tục",
                        ToastType.SUCCESS);
            } else if (status == 2627) {
                Callback.toastCallback.callbackToast("Tên đăng nhập đã tồn tại", ToastType.ERROR);
            } else {
                Callback.toastCallback.callbackToast("Đăng ký thất bại (Mã lỗi: " + status + ")", ToastType.ERROR);
            }
        }
    }
}
