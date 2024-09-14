package View.Components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;

import Resources.Constants;
import Resources.Constants.FontType;

public class PasswordField extends JPasswordField {
    private Color borderColor;
    private ImageIcon icon;
    private String hint;
    private int radius = 16;

    public PasswordField() {
        super();

        setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        setOpaque(false);
    }

    public PasswordField(ImageIcon icon) {
        super();
        this.icon = icon;

        setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        setOpaque(false);
    }

    public PasswordField(String hint) {
        super();
        this.hint = hint;

        setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        setOpaque(false);
    }

    public PasswordField(ImageIcon icon, String hint) {
        super();
        this.hint = hint;
        this.icon = icon;

        setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        setOpaque(false);
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;

        revalidate();
        repaint();
    }

    public void setRadius(int radius) {
        this.radius = radius;

        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        int height = getHeight(), width = getWidth();

        if (!isOpaque()) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setPaint(getBackground());
            g2.fillRoundRect(0, 0, width, height, radius, radius);
            g2.dispose();
        }

        super.paintComponent(g);

        if (borderColor != null) {
            Graphics2D graphics = (Graphics2D) g.create();

            graphics.setColor(Constants.gray02);

            graphics.drawArc(0, 0, radius, radius, 90, 90);
            graphics.drawArc(width - radius - 1, 0, radius, radius, 0, 90);
            graphics.drawArc(width - radius - 1, height - radius, radius, radius, 270, 90);
            graphics.drawArc(0, height - radius, radius, radius, 180, 90);

            graphics.drawLine(radius / 2, 0, width - radius / 2, 0);
            graphics.drawLine(0, radius / 2, 0, height - radius / 2);
            graphics.drawLine(radius / 2, height - 1, width - radius / 2, height - 1);
            graphics.drawLine(width - 1, radius / 2, width - 1, height - radius / 2);

            graphics.dispose();
        }
        if (icon != null) {
            g.drawImage(icon.getImage(), width - icon.getIconWidth() - 10, (height - icon.getIconHeight()) / 2, this);
        }
        if (hint != null) {
            if (this.getPassword().length == 0) {
                Graphics2D g2d = (Graphics2D) g.create();

                g2d.setColor(Color.GRAY);
                g2d.setFont(Constants.getFont(FontType.QUICKSAND_REGULAR).deriveFont(16f));
                g2d.drawString(hint, 10, height / 2 + 7);
                g2d.dispose();
            }
        }
    }
}