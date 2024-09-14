package View.Components;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Function;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Resources.Constants;

public class Dialog extends JDialog {
  public Dialog(JFrame prevFrame, String title, Function<Integer, Boolean> onSubmit) {
    super(prevFrame, "Cảnh báo", true);

    Button cancel = new Button("Hủy"), submit = new Button("OK");

    ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));
    setLayout(new GridLayout(1, 1));
    setLocation(prevFrame.getX() + prevFrame.getWidth() / 2 - 375, prevFrame.getY() + prevFrame.getHeight() / 2 - 100);
    setSize(750, 200);

    cancel.setBackground(Constants.gray02);
    cancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        setVisible(false);
      }
    });

    submit.setBackground(Constants.blue01);
    submit.setForeground(Color.WHITE);
    submit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        onSubmit.apply(0);

        setVisible(false);
      }
    });

    getContentPane().add(new Column(16,
        new Column(0, new Row(0, Box.createHorizontalGlue(), new JLabel(title), Box.createHorizontalGlue())),
        new Row(14, Box.createHorizontalGlue(), cancel, submit, Box.createHorizontalGlue())));

    setVisible(true);
  }
}
