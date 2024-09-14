package View.Components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.plaf.basic.*;

import Resources.Constants;

public class Spinner extends JSpinner {
    private Color borderColor = Constants.gray02;
    private int radius = 16;

    public Spinner(SpinnerModel model) {
        super(model);

        setBackground(Color.WHITE);
        setUI(new SpinnerUI());
        setPreferredSize(new Dimension(0,
                ((SpinnerUI) getUI()).getNextButtonHeight() + ((SpinnerUI) getUI()).getPreviousButtonHeight() + 20));
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        int height = getHeight(), width = getWidth();

        super.paintComponent(g);

        Graphics2D graphics = (Graphics2D) g.create();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (borderColor != null) {
            graphics.setColor(borderColor);

            graphics.drawArc(0, 0, radius, radius, 90, 90);
            graphics.drawArc(width - radius - 1, 0, radius, radius, 0, 90);
            graphics.drawArc(width - radius - 1, height - radius, radius, radius, 270, 90);
            graphics.drawArc(0, height - radius, radius, radius, 180, 90);

            graphics.drawLine(radius / 2, 0, width - radius / 2, 0);
            graphics.drawLine(0, radius / 2, 0, height - radius / 2);
            graphics.drawLine(radius / 2, height - 1, width - radius / 2, height - 1);
            graphics.drawLine(width - 1, radius / 2, width - 1, height - radius / 2);
        }
    }

    private class SpinnerUI extends BasicSpinnerUI {
        protected int getNextButtonHeight() {
            return ((int) createNextButton().getPreferredSize().getHeight());
        }

        protected int getPreviousButtonHeight() {
            return ((int) createPreviousButton().getPreferredSize().getHeight());
        }

        @Override
        protected Component createNextButton() {
            Button nextButton = new Button("", new ImageIcon(Constants.imagePath + "ic_up.png"));
            nextButton.setRadius(4);
            nextButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    SpinnerModel model = Spinner.this.getModel();
                    Object currentValue = model.getNextValue();

                    if (currentValue != null) {
                        model.setValue(currentValue);
                    }
                }
            });

            return nextButton;
        }

        @Override
        protected Component createPreviousButton() {
            Button previousButton = new Button("", new ImageIcon(Constants.imagePath + "ic_down.png"));
            previousButton.setRadius(4);
            previousButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    SpinnerModel model = Spinner.this.getModel();
                    Object currentValue = model.getPreviousValue();

                    if (currentValue != null) {
                        model.setValue(currentValue);
                    }
                }
            });

            return previousButton;
        }
    }

    // private class RoundedSpinnerUI extends BasicSpinnerUI {
    // @Override
    // protected Component createNextButton() {
    // Button nextButton = new Button("▲");
    // nextButton.addActionListener(new ActionListener() {
    // @Override
    // public void actionPerformed(ActionEvent e) {
    // // Handle increment logic here
    // // For example:
    // SpinnerModel model = Spinner.this.getModel();
    // Object currentValue = model.getNextValue();
    // if (currentValue != null) {
    // model.setValue(currentValue);
    // }
    // }
    // });
    // return nextButton;
    // }

    // @Override
    // protected Component createPreviousButton() {
    // Button prevButton = new Button("▼");
    // prevButton.addActionListener(new ActionListener() {
    // @Override
    // public void actionPerformed(ActionEvent e) {
    // // Handle decrement logic here
    // // For example:
    // SpinnerModel model = RoundedSpinner.this.getModel();
    // Object currentValue = model.getPreviousValue();
    // if (currentValue != null) {
    // model.setValue(currentValue);
    // }
    // }
    // });
    // return prevButton;
    // }
    // }

}
