package com.recordcrate.utils;

public enum SongSpeedEnum {
  SPEED_33("33 rpm"),
  SPEED_45("45 rpm"),
  SPEED_78("78 rpm");

  private final String format;

  private SongSpeedEnum(final String format) {
    this.format = format;
  }

  public String getFormat() {
	return format;
  }
  
}
