package Model;

import java.util.Date;

public class Attempt {
  private int id;
  private int examId;
  private int studentId;
  private long duration;
  private float grade;
  private Date timestart;

  public Attempt(int examId, int studentId, long duration, float grade, Date timeStart) {
    this.examId = examId;
    this.studentId = studentId;
    this.duration = duration;
    this.grade = grade;
    this.timestart = timeStart;
  }

  public Attempt(int id, int examId, int studentId, long duration, float grade, Date timeStart) {
    this.id = id;
    this.examId = examId;
    this.studentId = studentId;
    this.duration = duration;
    this.grade = grade;
    this.timestart = timeStart;
  }

  public int getId() {
    return id;
  }

  public int getExamId() {
    return examId;
  }

  public int getStudentId() {
    return studentId;
  }

  public long getDuration() {
    return duration;
  }

  public float getGrade() {
    return grade;
  }

  public Date getTimeStart() {
    return timestart;
  }
}
