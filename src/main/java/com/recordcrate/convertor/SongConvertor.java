package com.recordcrate.convertor;

import com.recordcrate.domain.Song;
import com.recordcrate.domain.SongDto;

public interface SongConvertor {

	public Song convertSongDtoToSong(SongDto songDto);
	
	public SongDto convertSongToSongDto(Song song);
}
