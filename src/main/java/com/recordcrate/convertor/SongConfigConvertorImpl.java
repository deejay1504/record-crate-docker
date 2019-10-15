package com.recordcrate.convertor;

import org.springframework.stereotype.Component;

import com.recordcrate.domain.SongConfig;
import com.recordcrate.domain.SongConfigDto;

@Component
public class SongConfigConvertorImpl implements SongConfigConvertor {

	@Override
	public SongConfig convertSongConfigDtoToSongConfig(SongConfigDto songConfigDto) {
		SongConfig songConfig = new SongConfig();
		songConfig.setId(songConfigDto.getId());
		songConfig.setPropertyName(songConfigDto.getPropertyName());
		songConfig.setPropertyValue(songConfigDto.getPropertyValue());
		return songConfig;
	}

	@Override
	public SongConfigDto convertSongConfigToSongConfigDto(SongConfig songConfig) {
		SongConfigDto songConfigDto = new SongConfigDto();
		songConfigDto.setId(songConfig.getId());
		songConfigDto.setPropertyName(songConfig.getPropertyName());
		songConfigDto.setPropertyValue(songConfig.getPropertyValue());
		return songConfigDto;
	}
}