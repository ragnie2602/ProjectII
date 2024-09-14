package View.Components;

import java.awt.*;
import javax.swing.*;

public class Center extends JPanel {
    public Center(Component component, int axis) {
        super();

        setBackground(Color.WHITE);
        setLayout(new BoxLayout(this, axis));
        add(axis == BoxLayout.X_AXIS ? Box.createHorizontalGlue() : Box.createVerticalGlue());
        add(component);
        add(axis == BoxLayout.X_AXIS ? Box.createHorizontalGlue() : Box.createVerticalGlue());
    }
}