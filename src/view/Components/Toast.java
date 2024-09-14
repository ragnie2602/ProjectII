package View.Components;

import javax.swing.*;

import Resources.Constants.ToastType;

import java.awt.*;

public class Toast extends JWindow {
    public Toast(JFrame parent, String message, ToastType type) {
        super(parent);

        Color backgroundColor = Color.WHITE, foregroundColor = Color.WHITE;

        switch (type) {
            case SUCCESS:
                backgroundColor = new Color(0x79D70F);
                foregroundColor = Color.WHITE;
                break;
            case WARNING:
                backgroundColor = new Color(0xF5A31A);
                foregroundColor = Color.BLACK;
                break;
            case ERROR:
                backgroundColor = new Color(0xD32626);
                foregroundColor = Color.WHITE;
                break;
            default:
                break;
        }

        JLabel label = new JLabel(message);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(backgroundColor);
        label.setForeground(foregroundColor);

        RoundedPanel panel = new RoundedPanel(16);
        panel.add(label);
        panel.setBackground(backgroundColor);

        getContentPane().add(panel, BorderLayout.CENTER);

        setSize(300, 50);
        setLocationRelativeTo(parent);

        setLocation(parent.getX() + parent.getWidth() / 2 - getWidth() / 2, parent.getY() + getHeight() / 2);

        setVisible(true);

        Timer timer = new Timer(3000, e -> dispose());
        timer.setRepeats(false);
        timer.start();
    }
}
