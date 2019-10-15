package com.recordcrate.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
	private String artist;
	
	@Column(name = "song_title")
	private String songTitle;
	
	@Column(name = "record_label")
	private String recordLabel;
	
	private String year;
	
	@Column(name = "number_of_copies")
	private String numberOfCopies;
	
	private String duration;
	
	private String side;

	@Column(name = "song_format")
	private String songFormat;
	
	private String genre;
	
	@Column(name = "song_rpm")
	private String songRpm;
	
	@Column(name = "song_bpm")
	private String songBpm;
}
