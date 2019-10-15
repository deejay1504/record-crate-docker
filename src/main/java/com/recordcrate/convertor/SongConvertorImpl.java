package com.recordcrate.convertor;

import org.springframework.stereotype.Component;

import com.recordcrate.domain.Song;
import com.recordcrate.domain.SongDto;
import com.recordcrate.utils.Constants;

@Component
public class SongConvertorImpl implements SongConvertor {

	@Override
	public Song convertSongDtoToSong(SongDto songDto) {
		Song song = new Song();
		song.setId(songDto.getId());
		song.setArtist(songDto.getArtist());
		song.setSongBpm(songDto.getSongBpm());
		song.setSongRpm(songDto.getSongRpm());
		song.setGenre(song.getGenre());
		song.setNumberOfCopies(song.getNumberOfCopies());
		song.setRecordLabel(song.getRecordLabel());
		song.setSide(song.getSide());
		song.setSongFormat(song.getSongFormat());
		song.setSongTitle(song.getSongTitle());
		song.setYear(song.getYear());
		StringBuilder sb = new StringBuilder();
		sb.append(songDto.getDurationHours()).append(Constants.COLON)
		  .append(songDto.getDurationMins()).append(Constants.COLON)
		  .append(songDto.getDurationSecs());
		song.setDuration(sb.toString());
		return song;
	}

	@Override
	public SongDto convertSongToSongDto(Song song) {
		SongDto songDto = new SongDto();
		songDto.setId(song.getId());
		songDto.setArtist(song.getArtist());
		songDto.setSongBpm(song.getSongBpm());
		songDto.setSongRpm(song.getSongRpm());
		songDto.setGenre(song.getGenre());
		songDto.setNumberOfCopies(song.getNumberOfCopies());
		songDto.setRecordLabel(song.getRecordLabel());
		songDto.setSide(song.getSide());
		songDto.setSongFormat(song.getSongFormat());
		songDto.setSongTitle(song.getSongTitle());
		songDto.setYear(song.getYear());
		String[] duration = song.getDuration().split(Constants.COLON);
		songDto.setDurationHours(duration[0]);
		songDto.setDurationMins(duration[1]);
		songDto.setDurationSecs(duration[2]);
		return songDto;
	}

}