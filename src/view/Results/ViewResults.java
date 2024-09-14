package View.Results;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Controller.QuizCtrl;
import Model.Attempt;

public class ViewResults extends JPanel {
  public ViewResults(int userId) {
    super();

    JPanel wrapper = new JPanel();
    JScrollPane scrollPane = new JScrollPane(wrapper);

    scrollPane.getVerticalScrollBar().setUnitIncrement(16);
    scrollPane.setBorder(null);

    wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));

    for (Attempt attempt : QuizCtrl.getAllAttempt(userId)) {
      wrapper.add(new ResultItem(attempt));
      wrapper.add(Box.createVerticalStrut(20));
    }

    add(scrollPane);
  }
}
