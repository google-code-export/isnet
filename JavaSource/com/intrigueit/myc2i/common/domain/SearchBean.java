package com.intrigueit.myc2i.common.domain;

public class SearchBean {
  private Long recordId;
  private String firstName;
  private String lastName;
  private String city;
  private String state;
  private String email;
  private String chapterName;
  private String chapterLeader;
  public Long getRecordId() {
    return recordId;
  }
  public void setRecordId(Long recordId) {
    this.recordId = recordId;
  }
  public String getFirstName() {
    return firstName;
  }
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  public String getLastName() {
    return lastName;
  }
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  public String getCity() {
    return city;
  }
  public void setCity(String city) {
    this.city = city;
  }
  public String getState() {
    return state;
  }
  public void setState(String state) {
    this.state = state;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getChapterName() {
    return chapterName;
  }
  public void setChapterName(String chapterName) {
    this.chapterName = chapterName;
  }
  public String getChapterLeader() {
    return chapterLeader;
  }
  public void setChapterLeader(String chapterLeader) {
    this.chapterLeader = chapterLeader;
  }
}
