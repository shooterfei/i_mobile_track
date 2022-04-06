package com.zeusight.mobile_track.util;

public class Describe {
  private String label;
  private int angle;

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public int getAngle() {
    return angle;
  }

  public void setAngle(int angle) {
    this.angle = angle;
  }

  @Override
  public String toString() {
    return "Describe{" +
      "label='" + label + '\'' +
      ", angle=" + angle +
      '}';
  }
}
