package Server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import Model.Attempt;
import Model.Question;
import Resources.Tools;

public class QuizQuery {
  public static ArrayList<Question> generateQuiz(int examId) {
    ArrayList<Question> questions = new ArrayList<>();
    if (DBConnection.database != null) {
      try {
        PreparedStatement preparedStatement = DBConnection.database
            .prepareStatement("SELECT * FROM QUESTION WHERE EXAM = ?");

        preparedStatement.setInt(1, examId);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
          questions.add(new Question(resultSet.getInt(1),
              resultSet.getString(2),
              resultSet.getBytes(3) == null ? null : Tools.BytesToImage(resultSet.getBytes(3)),
              resultSet.getInt(4),
              examId));
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return questions;
  }

  public static ArrayList<Attempt> getAllAttempt(int userId) {
    ArrayList<Attempt> attempts = new ArrayList<>();

    if (DBConnection.database != null) {
      try {
        PreparedStatement preparedStatement = DBConnection.database
            .prepareStatement("SELECT * FROM ATTEMPT WHERE STUDENT = ?");

        preparedStatement.setInt(1, userId);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
          attempts.add(new Attempt(resultSet.getInt(1),
              resultSet.getInt(2),
              resultSet.getInt(3),
              resultSet.getLong(4),
              resultSet.getFloat(5),
              new Date(resultSet.getTimestamp(6).getTime())));
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return attempts;
  }

  public static boolean submitQuiz(Attempt attempt) {
    if (DBConnection.database != null) {
      try {
        PreparedStatement preparedStatement = DBConnection.database
            .prepareStatement("INSERT INTO ATTEMPT VALUES(?, ?, ?, ?, ?, ?)");

        preparedStatement.setInt(1, attempt.getExamId());
        preparedStatement.setInt(2, attempt.getStudentId());
        preparedStatement.setLong(3, attempt.getDuration());
        preparedStatement.setFloat(4, attempt.getGrade());
        preparedStatement.setTimestamp(5, new Timestamp(attempt.getTimeStart().getTime()));
        preparedStatement.setBoolean(6, true);

        return preparedStatement.executeUpdate() > 0;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return false;
  }
}
