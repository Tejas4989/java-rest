package com.validus.javarest.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Song extends BaseModel {

  @Column
  private int track;

  @Column
  private String name;

  public int getTrack() {
    return track;
  }

  public void setTrack(int track) {
    this.track = track;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
