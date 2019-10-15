package com.recordcrate.domain;

import lombok.Data;

@Data
public class SongDto {
	
	private Long id;

	private String artist;
	
	private String songTitle;
	
	private String recordLabel;
	
	private String year;
	
	private String numberOfCopies;
	
	private String durationHours;
	
	private String durationMins;
	
	private String durationSecs;
	
	private String side;
	
	private String songFormat;
	
	private String genre;
	
	private String songRpm;

	private String songBpm;
	
}
