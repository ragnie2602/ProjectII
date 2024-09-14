package View.Components;

import javax.swing.border.Border;
import java.awt.*;

public class GradientBorder implements Border {
    private final int thickness;
    private final Color startColor;
    private final Color endColor;

    public GradientBorder(int thickness, Color startColor, Color endColor) {
        this.thickness = thickness;
        this.startColor = startColor;
        this.endColor = endColor;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Tạo GradientPaint
        GradientPaint gp = new GradientPaint(
                x, y, startColor,
                x, y + height, endColor);

        // Áp dụng GradientPaint
        g2d.setPaint(gp);
        g2d.fillRect(x, y, width, thickness); // Top border
        g2d.fillRect(x, y + height - thickness, width, thickness); // Bottom border
        g2d.fillRect(x, y, thickness, height); // Left border
        g2d.fillRect(x + width - thickness, y, thickness, height); // Right border

        g2d.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(thickness, thickness, thickness, thickness);
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }
}
