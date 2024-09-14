package Model;

import javax.swing.ImageIcon;

public class Choice {
  private int id;
  private String text;
  private ImageIcon media;
  private boolean isCorrect;
  private int questionId;

  public Choice() {
  }

  public Choice(String text, ImageIcon media, boolean isCorrect, int questionId) {
    this.text = text;
    this.media = media;
    this.isCorrect = isCorrect;
    this.questionId = questionId;
  }

  public Choice(int id, String text, ImageIcon media, boolean isCorrect, int questionId) {
    this.id = id;
    this.text = text;
    this.media = media;
    this.isCorrect = isCorrect;
    this.questionId = questionId;
  }

  public int getId() {
    return id;
  }

  public String getText() {
    return text;
  }

  public ImageIcon getMedia() {
    return media;
  }

  public boolean getIsCorrect() {
    return isCorrect;
  }

  public int getQuestionId() {
    return questionId;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void setMedia(ImageIcon media) {
    this.media = media;
  }

  public void setIsCorrect(boolean isCorrect) {
    this.isCorrect = isCorrect;
  }
}
