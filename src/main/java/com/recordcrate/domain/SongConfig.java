package com.recordcrate.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class SongConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
	@Column(name = "property_name")
	private String propertyName;
	
	@Column(name = "property_value")
	private String propertyValue;
	
}
