package Model;

import javax.swing.ImageIcon;

public class Question {
  private int id;
  private String ask;
  private ImageIcon media;
  private int level;
  private int examId;

  public Question() {
  }

  public Question(String ask, ImageIcon media, int level, int examId) {
    this.ask = ask;
    this.media = media;
    this.level = level;
    this.examId = examId;
  }

  public Question(int id, String ask, ImageIcon media, int level, int examId) {
    this.id = id;
    this.ask = ask;
    this.media = media;
    this.level = level;
    this.examId = examId;
  }

  public int getId() {
    return id;
  }

  public String getAsk() {
    return ask;
  }

  public ImageIcon getMedia() {
    return media;
  }

  public int getLevel() {
    return level;
  }

  public int getExamId() {
    return examId;
  }

  public void setAsk(String ask) {
    this.ask = ask;
  }

  public void setMedia(ImageIcon media) {
    this.media = media;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public void setExamId(int examId) {
    this.examId = examId;
  }
}
