package com.recordcrate.utils;

public enum SongFormatEnum {
  INCH_7("7 inch"),
  INCH_10("10 inch"),
  INCH_12("12 inch"),
  DIGITAL("Digital"),
  CD("CD"),
  CD_SINGLE("CD_SINGLE"),
  EP("EP"),
  LP("LP");

  private final String format;

  private SongFormatEnum(final String format) {
    this.format = format;
  }

  public String getFormat() {
	return format;
  }
  
}
