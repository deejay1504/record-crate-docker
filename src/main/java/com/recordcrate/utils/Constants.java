package com.recordcrate.utils;

import java.util.HashMap;

public class Constants {

	public static final String COLON = ":";
	public static final String COMMA = ",";
	public static final String CR    = "\n";
	public static final String SPACE = " ";
	public static final String OPEN_BRACKET  = "(";
	public static final String CLOSE_BRACKET = ")";
	
	public static final class SQL {
		public static final String ALL_SONGS   = "select * from song";
		public static final String BY_FORMAT   = "select song_format, count(*) song_count from song group by song_format order by song_count";
		public static final String BY_RPM      = "select song_rpm, count(*) song_count from song group by song_rpm order by song_count";
		public static final String LAST_SONG   = "select * from song order by id desc limit 1;";
		public static final String QUERY1      = "select s from Song s where s.";
		public static final String QUERY2      = " like :searchValue";
	}
	
	public static final class SONG_FIELDS {
		public static final String SONG_ID      = "songId";
		public static final String ARTIST       = "artist";
		public static final String TITLE        = "songTitle";
		public static final String RECORD_LABEL = "recordLabel";
		public static final String YEAR         = "year";
		public static final String NO_OF_COPIES = "numberOfCopies";
		public static final String DURATION     = "duration";
		public static final String SONG_FORMAT  = "songFormat";
		public static final String SIDE         = "side";
		public static final String GENRE        = "genre";
		public static final String SONG_RPM     = "songRpm";
		public static final String SONG_BPM     = "songBpm";
	}

	public static final class SONG_CONFIG {
		public static final String DIR_NAME    = "exportDir";
		public static final String FILE_NAME   = "exportName";
		public static final String SONG_FORMAT = "currentSongFormat";
	}

	public static final class SORT {
		public static final String ASC  = "asc";
		public static final String DESC = "desc";
	}
	
	public static final HashMap<Integer, String> SORT_FIELDS;
	static {
		SORT_FIELDS = new HashMap<Integer, String>();
		SORT_FIELDS.put(1, SONG_FIELDS.ARTIST);
		SORT_FIELDS.put(2, SONG_FIELDS.TITLE);
		SORT_FIELDS.put(3, SONG_FIELDS.RECORD_LABEL);
		SORT_FIELDS.put(4, SONG_FIELDS.YEAR);
		SORT_FIELDS.put(5, SONG_FIELDS.NO_OF_COPIES);
		SORT_FIELDS.put(6, SONG_FIELDS.DURATION);
		SORT_FIELDS.put(7, SONG_FIELDS.SONG_FORMAT);
		SORT_FIELDS.put(8, SONG_FIELDS.SIDE);
		SORT_FIELDS.put(9, SONG_FIELDS.GENRE);
		SORT_FIELDS.put(10, SONG_FIELDS.SONG_RPM);
		SORT_FIELDS.put(11, SONG_FIELDS.SONG_BPM);
	}
		
}