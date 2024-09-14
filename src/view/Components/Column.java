package View.Components;

import java.awt.*;
import javax.swing.*;

public class Column extends JPanel {
    public Column(int gap, Component... children) {
        super();

        setAlignmentX(JPanel.CENTER_ALIGNMENT);
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        for (int i = 0; i < children.length - 1; i++) {
            add(children[i]);
            add(Box.createVerticalStrut(gap));
        }
        add(children[children.length - 1]);
    }

    public Column(int gap, float crossAlignment, Component... children) {
        super();

        setAlignmentX(crossAlignment);
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        for (int i = 0; i < children.length - 1; i++) {
            add(children[i]);
            add(Box.createVerticalStrut(gap));
        }
        add(children[children.length - 1]);
    }
}