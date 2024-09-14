package View;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Resources.Constants.DialogType;
import View.Components.Center;

public class Dialog extends JFrame {
    private JButton confirmButton;

    public Dialog(String message, DialogType type, JFrame parentFrame) {
        super();

        if (parentFrame != null) {
            parentFrame.setEnabled(false);
        }

        getContentPane().setBackground(Color.WHITE);
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setSize(800, 200);

        confirmButton = new JButton("OK");
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (parentFrame != null) {
                    parentFrame.setEnabled(true);
                    parentFrame.toFront();
                }
                setVisible(false);
            }
        });

        add(new Center(new JLabel(message, JLabel.CENTER), BoxLayout.X_AXIS));
        add(Box.createVerticalStrut(16));
        add(new Center(confirmButton, BoxLayout.X_AXIS));

        setVisible(true);
    }
}
