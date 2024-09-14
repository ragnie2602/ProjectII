package Controller;

import java.util.ArrayList;
import java.util.Random;

import Model.Attempt;
import Model.Choice;
import Model.Exam;
import Model.Question;
import Server.ExamQuery;
import Server.QuizQuery;

public class QuizCtrl {
  public static ArrayList<Question> generateQuiz(Exam exam) {
    ArrayList<Question> response, quiz = new ArrayList<>();
    response = QuizQuery.generateQuiz(exam.getId());

    // Classify
    ArrayList<Question> easies = new ArrayList<>(), mediums = new ArrayList<>(), hards = new ArrayList<>();
    for (Question question : response) {
      switch (question.getLevel()) {
        case 0:
          easies.add(question);
          break;
        case 1:
          mediums.add(question);
          break;
        case 2:
          hards.add(question);
        default:
          break;
      }
    }

    Random random = new Random();
    // Filter
    for (int i = easies.size(); i > exam.getEasies(); i--) {
      easies.remove(random.nextInt(easies.size()));
    }
    for (int i = mediums.size(); i > exam.getMediums(); i--) {
      mediums.remove(random.nextInt(mediums.size()));
    }
    for (int i = hards.size(); i > exam.getHards(); i--) {
      hards.remove(random.nextInt(hards.size()));
    }

    // Composition
    for (Question question : easies) {
      quiz.add(question);
    }
    for (Question question : mediums) {
      quiz.add(question);
    }
    for (Question question : hards) {
      quiz.add(question);
    }

    return quiz;
  }

  public static ArrayList<Attempt> getAllAttempt(int userId) {
    return QuizQuery.getAllAttempt(userId);
  }

  public static ArrayList<Choice> getAllChoices(int questionId) {
    return ExamQuery.getAllChoice(questionId);
  }

  public static boolean submitQuiz(Attempt attempt) {
    return QuizQuery.submitQuiz(attempt);
  }
}
