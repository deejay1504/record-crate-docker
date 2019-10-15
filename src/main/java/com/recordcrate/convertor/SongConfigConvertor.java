package com.recordcrate.convertor;

import com.recordcrate.domain.SongConfig;
import com.recordcrate.domain.SongConfigDto;

public interface SongConfigConvertor {

	public SongConfig convertSongConfigDtoToSongConfig(SongConfigDto songConfigDto);
	
	public SongConfigDto convertSongConfigToSongConfigDto(SongConfig songConfig);
}
