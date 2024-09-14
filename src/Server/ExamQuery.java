package Server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Model.Choice;
import Model.Exam;
import Model.Question;
import Resources.Tools;

public class ExamQuery {
  public static boolean createChoices(ArrayList<Choice> choices) {
    if (DBConnection.database != null) {
      try {
        PreparedStatement preparedStatement = DBConnection.database
            .prepareStatement("INSERT INTO CHOICE VALUES (?, ?, ?, ?)");

        for (Choice choice : choices) {
          preparedStatement.setString(1, choice.getText());
          preparedStatement.setBytes(2, choice.getMedia() == null ? null : Tools.imageToBytes(choice.getMedia()));
          preparedStatement.setBoolean(3, choice.getIsCorrect());
          preparedStatement.setInt(4, choice.getQuestionId());

          preparedStatement.addBatch();
        }

        preparedStatement.executeBatch();

        return true;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return false;
  }

  public static int createExam(Exam exam) {
    if (DBConnection.database != null) {
      try {
        PreparedStatement preparedStatement = DBConnection.database
            .prepareStatement("INSERT INTO EXAM VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        preparedStatement.setString(1, exam.getName());
        preparedStatement.setString(2, exam.getDescription());
        preparedStatement.setTimestamp(3, new Timestamp(exam.getOpenTime().getTime()));
        preparedStatement.setTimestamp(4, new Timestamp(exam.getCloseTime().getTime()));
        preparedStatement.setString(5, exam.getSubject());
        preparedStatement.setInt(6, exam.getDuration());
        preparedStatement.setBoolean(7, exam.getCanRepeat());
        preparedStatement.setBoolean(8, exam.getCanReviewed());
        preparedStatement.setInt(9, exam.getEasies());
        preparedStatement.setFloat(10, exam.getEasyPts());
        preparedStatement.setInt(11, exam.getMediums());
        preparedStatement.setFloat(12, exam.getMediumPts());
        preparedStatement.setInt(13, exam.getHards());
        preparedStatement.setFloat(14, exam.getHardPts());
        preparedStatement.setInt(15, exam.getTeacherId());

        preparedStatement.executeUpdate();

        preparedStatement = DBConnection.database.prepareStatement("SELECT @@IDENTITY");
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
          return resultSet.getInt(1);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return -1;
  }

  public static int createQuestion(Question question) {
    if (DBConnection.database != null) {
      try {
        PreparedStatement preparedStatement = DBConnection.database
            .prepareStatement("INSERT INTO QUESTION VALUES(?, ?, ?, ?)");

        preparedStatement.setString(1, question.getAsk());
        preparedStatement.setBytes(2, question.getMedia() == null ? null : Tools.imageToBytes(question.getMedia()));
        preparedStatement.setInt(3, question.getLevel());
        preparedStatement.setInt(4, question.getExamId());

        preparedStatement.executeUpdate();

        preparedStatement = DBConnection.database.prepareStatement("SELECT @@IDENTITY");
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
          return resultSet.getInt(1);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return -1;
  }

  public static boolean deleteChoice(int choiceId) {
    if (DBConnection.database != null) {
      try {
        PreparedStatement preparedStatement = DBConnection.database.prepareStatement("DELETE FROM CHOICE WHERE ID = ?");

        preparedStatement.setInt(1, choiceId);

        preparedStatement.executeUpdate();

        return true;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return false;
  }

  public static boolean deleteChoiceOfQuestion(int questionId) {
    if (DBConnection.database != null) {
      try {
        PreparedStatement preparedStatement = DBConnection.database
            .prepareStatement("DELETE FROM CHOICE WHERE QUESTION = ?");

        preparedStatement.setInt(1, questionId);

        preparedStatement.executeUpdate();

        return true;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return false;
  }

  public static boolean deleteExam(int examId) {
    if (DBConnection.database != null) {
      try {
        if (!deleteQuestionOfExam(examId)) {
          return false;
        }

        PreparedStatement preparedStatement = DBConnection.database.prepareStatement("DELETE FROM EXAM WHERE ID = ?");

        preparedStatement.setInt(1, examId);

        preparedStatement.executeUpdate();

        return true;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return false;
  }

  public static boolean deleteQuestion(int questionId) {
    if (DBConnection.database != null) {
      try {
        if (!deleteChoiceOfQuestion(questionId)) {
          return false;
        }

        PreparedStatement preparedStatement = DBConnection.database
            .prepareStatement("DELETE FROM QUESTION WHERE ID = ?");

        preparedStatement.setInt(1, questionId);

        preparedStatement.executeUpdate();

        return true;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return false;
  }

  public static boolean deleteQuestionOfExam(int examId) {
    if (DBConnection.database != null) {
      try {
        PreparedStatement preparedStatement = DBConnection.database
            .prepareStatement("SELECT ID FROM QUESTION WHERE EXAM = ?");

        preparedStatement.setInt(1, examId);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
          if (!deleteChoiceOfQuestion(resultSet.getInt(1)))
            return false;
        }

        preparedStatement = DBConnection.database.prepareStatement("DELETE FROM QUESTION WHERE EXAM = ?");

        preparedStatement.setInt(1, examId);

        preparedStatement.executeUpdate();

        return true;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return false;
  }

  public static boolean editChoice(ArrayList<Choice> choices) {
    if (DBConnection.database != null) {

      try {
        PreparedStatement preparedStatement = DBConnection.database
            .prepareStatement("UPDATE CHOICE SET TEXT = ?, MEDIA = ?, ISCORRECT = ? WHERE ID = ?");

        for (Choice choice : choices) {
          preparedStatement.setString(1, choice.getText());
          preparedStatement.setBytes(2, choice.getMedia() == null ? null : Tools.imageToBytes(choice.getMedia()));
          preparedStatement.setBoolean(3, choice.getIsCorrect());
          preparedStatement.setInt(4, choice.getId());

          preparedStatement.addBatch();
        }

        preparedStatement.executeBatch();

        return true;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return false;
  }

  public static boolean editExam(Exam exam) {
    if (DBConnection.database != null) {
      try {
        PreparedStatement preparedStatement = DBConnection.database.prepareStatement(
            "UPDATE EXAM SET [NAME] = ?, [DESCRIPTION] = ?, OPENTIME = ?, CLOSETIME = ?, SUBJECT = ?, DURATION = ?, CANREPEAT = ?, CANREVIEWED = ?, EASIES = ?, EASYPOINT = ?, MEDIUMS = ?, MEDIUMPOINT = ?, HARDS = ?, HARDPOINT = ?  WHERE ID = ?");

        preparedStatement.setString(1, exam.getName());
        preparedStatement.setString(2, exam.getDescription());
        preparedStatement.setTimestamp(3, new Timestamp(exam.getOpenTime().getTime()));
        preparedStatement.setTimestamp(4, new Timestamp(exam.getCloseTime().getTime()));
        preparedStatement.setString(5, exam.getSubject());
        preparedStatement.setInt(6, exam.getDuration());
        preparedStatement.setBoolean(7, exam.getCanRepeat());
        preparedStatement.setBoolean(8, exam.getCanReviewed());
        preparedStatement.setInt(9, exam.getEasies());
        preparedStatement.setFloat(10, exam.getEasyPts());
        preparedStatement.setInt(11, exam.getMediums());
        preparedStatement.setFloat(12, exam.getMediumPts());
        preparedStatement.setInt(13, exam.getHards());
        preparedStatement.setFloat(14, exam.getHardPts());
        preparedStatement.setInt(15, exam.getId());

        preparedStatement.executeUpdate();

        return true;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return false;
  }

  public static boolean editQuestion(Question question) {
    if (DBConnection.database != null) {
      try {
        PreparedStatement preparedStatement = DBConnection.database
            .prepareStatement("UPDATE QUESTION SET ASK = ?, MEDIA = ?, [LEVEL] = ? WHERE ID = ?");

        preparedStatement.setString(1, question.getAsk());
        preparedStatement.setBytes(2, question.getMedia() == null ? null : Tools.imageToBytes(question.getMedia()));
        preparedStatement.setInt(3, question.getLevel());
        preparedStatement.setInt(4, question.getId());

        preparedStatement.executeUpdate();

        return true;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return false;
  }

  public static ArrayList<Choice> getAllChoice(int questionId) {
    ArrayList<Choice> choices = new ArrayList<>();

    if (DBConnection.database != null) {
      try {
        PreparedStatement preparedStatement = DBConnection.database
            .prepareStatement("SELECT * FROM CHOICE WHERE QUESTION = ?");

        preparedStatement.setInt(1, questionId);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {

          choices.add(new Choice(resultSet.getInt(1),
              resultSet.getString(2),
              resultSet.getBytes(3) == null ? null : Tools.BytesToImage(resultSet.getBytes(3)),
              resultSet.getBoolean(4),
              resultSet.getInt(5)));
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return choices;
  }

  public static ArrayList<Exam> getAllExam(int userId) {
    ArrayList<Exam> exams = new ArrayList<>();

    if (DBConnection.database != null) {
      try {
        PreparedStatement preparedStatement = DBConnection.database
            .prepareStatement("SELECT * FROM EXAM WHERE TEACHER = ?");

        preparedStatement.setInt(1, userId);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
          exams.add(new Exam(resultSet.getInt(1),
              resultSet.getString(2),
              resultSet.getString(3),
              resultSet.getTimestamp(4),
              resultSet.getTimestamp(5),
              resultSet.getString(6),
              resultSet.getInt(7),
              resultSet.getBoolean(8),
              resultSet.getBoolean(9),
              resultSet.getInt(10),
              resultSet.getFloat(11),
              resultSet.getInt(12),
              resultSet.getFloat(13),
              resultSet.getInt(14),
              resultSet.getFloat(15),
              resultSet.getInt(16)));
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return exams;
  }

  public static Map<Question, ArrayList<Choice>> getAllQuestion(int examId) {
    Map<Question, ArrayList<Choice>> questions = new HashMap<>();

    if (DBConnection.database != null) {
      try {
        PreparedStatement preparedStatement = DBConnection.database
            .prepareStatement("SELECT * FROM QUESTION WHERE EXAM = ?");

        preparedStatement.setInt(1, examId);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
          questions.put(new Question(resultSet.getInt(1),
              resultSet.getString(2),
              resultSet.getBytes(3) == null ? null : Tools.BytesToImage(resultSet.getBytes(3)),
              resultSet.getInt(4),
              resultSet.getInt(5)), getAllChoice(resultSet.getInt(1)));
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return questions;
  }

  public static Exam getExam(int examId) {
    if (DBConnection.database != null) {
      try {
        PreparedStatement preparedStatement = DBConnection.database.prepareStatement("SELECT * FROM EXAM WHERE ID = ?");

        preparedStatement.setInt(1, examId);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
          return new Exam(resultSet.getInt(1),
              resultSet.getString(2),
              resultSet.getString(3),
              new Date(),
              new Date(),
              resultSet.getString(6),
              resultSet.getInt(7),
              false,
              false,
              resultSet.getInt(10),
              resultSet.getFloat(11),
              resultSet.getInt(12),
              resultSet.getFloat(13),
              resultSet.getInt(14),
              resultSet.getInt(15),
              resultSet.getInt(16));
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return null;
  }

  public static ArrayList<Exam> searchExam(String keyword) {
    ArrayList<Exam> exams = new ArrayList<>();
    if (DBConnection.database != null) {
      try {
        PreparedStatement preparedStatement = DBConnection.database
            .prepareStatement(
                "SELECT * FROM EXAM WHERE [NAME] LIKE ? OR [DESCRIPTION] LIKE ? OR [SUBJECT] LIKE ?");
        preparedStatement.setString(1, "%" + keyword + "%");
        preparedStatement.setString(2, "%" + keyword + "%");
        preparedStatement.setString(3, "%" + keyword + "%");

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
          exams.add(new Exam(resultSet.getInt(1),
              resultSet.getString(2),
              resultSet.getString(3),
              resultSet.getTimestamp(4),
              resultSet.getTimestamp(5),
              resultSet.getString(6),
              resultSet.getInt(7),
              resultSet.getBoolean(8),
              resultSet.getBoolean(9),
              resultSet.getInt(10),
              resultSet.getFloat(11),
              resultSet.getInt(12),
              resultSet.getFloat(13),
              resultSet.getInt(14),
              resultSet.getFloat(15),
              resultSet.getInt(16)));
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return exams;
  }
}
