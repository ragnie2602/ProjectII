package View;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import Resources.Callback;
import Resources.Constants;
import View.Components.Header;
import View.Components.Toast;

public class Home {
    private GridBagLayout gb = new GridBagLayout();
    private GridBagConstraints gbc = new GridBagConstraints();
    private Header header;
    private Homepage homepage;
    private JFrame homeFrame;

    public Home() {
        homeFrame = new JFrame("Phần mềm thi trắc nghiệm");
        homeFrame.getContentPane().setBackground(Color.WHITE);
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        homeFrame.setIconImage(new ImageIcon(Constants.imagePath + "IntelliQuiz.ico").getImage());
        homeFrame.setLayout(gb);
        homeFrame.setSize(1000, 500);

        homepage = new Homepage();

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        header = new Header(homeFrame, gbc);
        homeFrame.add(header, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 962;
        homeFrame.add(homepage, gbc);

        homeFrame.setVisible(true);

        Callback.toastCallback = (message, type) -> {
            new Toast(homeFrame, message, type);
        };
    }
}
