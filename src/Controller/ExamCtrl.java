package Controller;

import java.util.ArrayList;
import java.util.Map;

import Model.Choice;
import Model.Exam;
import Model.Question;

import Server.ExamQuery;

public class ExamCtrl {
  public static boolean createChoices(ArrayList<Choice> choices) {
    return ExamQuery.createChoices(choices);
  }

  public static int createExam(Exam exam) {
    return ExamQuery.createExam(exam);
  }

  public static int createQuestion(Question question) {
    return ExamQuery.createQuestion(question);
  }

  public static boolean deleteChoice(int choiceId) {
    return ExamQuery.deleteChoice(choiceId);
  }

  public static boolean deleteExam(int examId) {
    return ExamQuery.deleteExam(examId);
  }

  public static boolean deleteQuestion(int questionId) {
    return ExamQuery.deleteQuestion(questionId);
  }

  public static boolean editChoices(ArrayList<Choice> choices) {
    ArrayList<Choice> newChoices = new ArrayList<>(), oldChoices = new ArrayList<>();

    for (Choice choice : choices) {
      if (choice.getId() == 0) {
        newChoices.add(choice);
      } else {
        oldChoices.add(choice);
      }
    }

    return createChoices(newChoices) && ExamQuery.editChoice(oldChoices);
  }

  public static boolean editExam(Exam exam) {
    return ExamQuery.editExam(exam);
  }

  public static boolean editQuestion(Question question) {
    return ExamQuery.editQuestion(question);
  }

  public static ArrayList<Exam> getAllExam(int userId) {
    return ExamQuery.getAllExam(userId);
  }

  public static Map<Question, ArrayList<Choice>> getAllQuestion(int examId) {
    return ExamQuery.getAllQuestion(examId);
  }

  public static Exam getExam(int examId) {
    return ExamQuery.getExam(examId);
  }

  public static ArrayList<Exam> searchExam(String keyword) {
    return ExamQuery.searchExam(keyword);
  }
}
