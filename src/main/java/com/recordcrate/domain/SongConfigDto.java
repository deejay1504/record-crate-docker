package com.recordcrate.domain;

import lombok.Data;

@Data
public class SongConfigDto {

    private Long id;
    
	private String propertyName;
	
	private String propertyValue;
	
}
