package Model;

import java.util.Date;

public class Exam {
  private int id;
  private String name;
  private String description;
  private Date openTime;
  private Date closeTime;
  private String subject;
  private int duration;
  private boolean canRepeat;
  private boolean canReviewed;
  private int easies;
  private float easyPts;
  private int mediums;
  private float mediumPts;
  private int hards;
  private float hardPts;
  private int teacherId;

  public Exam() {
  }

  public Exam(
      String name,
      String description,
      Date openTime,
      Date closeTime,
      String subject,
      int duration,
      boolean canRepeat,
      boolean canReviewed,
      int easies, float easyPts,
      int mediums, float mediumPts,
      int hards, float hardPts,
      int teacherId) {
    this.name = name;
    this.description = description;
    this.openTime = openTime;
    this.closeTime = closeTime;
    this.subject = subject;
    this.duration = duration;
    this.canRepeat = canRepeat;
    this.canReviewed = canReviewed;
    this.easies = easies;
    this.easyPts = easyPts;
    this.mediums = mediums;
    this.mediumPts = mediumPts;
    this.hards = hards;
    this.hardPts = hardPts;
    this.teacherId = teacherId;
  }

  public Exam(
      int id,
      String name,
      String description,
      Date openTime,
      Date closeTime,
      String subject,
      int duration,
      boolean canRepeat,
      boolean canReviewed,
      int easies, float easyPts,
      int mediums, float mediumPts,
      int hards, float hardPts,
      int teacherId) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.openTime = openTime;
    this.closeTime = closeTime;
    this.subject = subject;
    this.duration = duration;
    this.canRepeat = canRepeat;
    this.canReviewed = canReviewed;
    this.easies = easies;
    this.easyPts = easyPts;
    this.mediums = mediums;
    this.mediumPts = mediumPts;
    this.hards = hards;
    this.hardPts = hardPts;
    this.teacherId = teacherId;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Date getOpenTime() {
    return openTime;
  }

  public Date getCloseTime() {
    return closeTime;
  }

  public String getSubject() {
    return subject;
  }

  public int getDuration() {
    return duration;
  }

  public boolean getCanRepeat() {
    return canRepeat;
  }

  public boolean getCanReviewed() {
    return canReviewed;
  }

  public int getEasies() {
    return easies;
  }

  public float getMediumPts() {
    return mediumPts;
  }

  public int getMediums() {
    return mediums;
  }

  public float getHardPts() {
    return hardPts;
  }

  public int getHards() {
    return hards;
  }

  public float getEasyPts() {
    return easyPts;
  }

  public int getTeacherId() {
    return teacherId;
  }

  public int getQuestionCount() {
    return easies + mediums + hards;
  }

  public float getTotal() {
    return easies * easyPts + mediums * mediumPts + hards * hardPts;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setOpenTime(Date openTime) {
    this.openTime = openTime;
  }

  public void setCloseTime(Date closeTime) {
    this.closeTime = closeTime;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public void setCanRepeat(boolean canRepeat) {
    this.canRepeat = canRepeat;
  }

  public void setCanReviewed(boolean canReviewed) {
    this.canReviewed = canReviewed;
  }

  public void setEasies(int easies) {
    this.easies = easies;
  }

  public void setMediums(int mediums) {
    this.mediums = mediums;
  }

  public void setHards(int hards) {
    this.hards = hards;
  }
}
