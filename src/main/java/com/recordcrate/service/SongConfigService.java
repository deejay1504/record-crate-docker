package com.recordcrate.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recordcrate.convertor.SongConfigConvertor;
import com.recordcrate.domain.Song;
import com.recordcrate.domain.SongConfig;
import com.recordcrate.domain.SongConfigDto;
import com.recordcrate.repository.SongConfigRepository;
import com.recordcrate.repository.SongRepository;
import com.recordcrate.utils.Constants;

@Service
public class SongConfigService {

	@Autowired
	private SongConfigConvertor songConfigConvertor;

	@Autowired
    private SongConfigRepository songConfigRepository;
	
	@Autowired
    private SongRepository songRepository;
	
	public List<SongConfigDto> getSongConfig() {
		List<SongConfigDto> songConfigList = new ArrayList<SongConfigDto>();
		Iterable<SongConfig> allSongConfigs = songConfigRepository.findAll();
		allSongConfigs.forEach(songConfig -> {
			songConfigList.add(songConfigConvertor.convertSongConfigToSongConfigDto(songConfig));
		});
		return songConfigList;
	}

	public Boolean updateSongConfig(Map<?, ?> adminData) {
		String dirName = (String) adminData.get(Constants.SONG_CONFIG.DIR_NAME);
		File fileName = new File(dirName);
		Boolean dirOk = fileName.isDirectory();
		if (dirOk) {
			Iterable<SongConfig> allSongConfigs = songConfigRepository.findAll();
			allSongConfigs.forEach(songConfig -> {
				String configValue = (String) adminData.get(songConfig.getPropertyName());
				songConfig.setPropertyValue(configValue);
				songConfigRepository.save(songConfig);
			});
		}
		return dirOk;
	}

	public Boolean export() {
		SongConfig songConfig = songConfigRepository.findOne(1L);
		String exportDir = songConfig.getPropertyValue();
		songConfig = songConfigRepository.findOne(2L);
		String exportName = songConfig.getPropertyValue();
		StringBuilder fileName = new StringBuilder();
		fileName.append(exportDir);
		if (!exportDir.endsWith(File.separator)) {
			fileName.append(File.separator);
		}
		fileName.append(exportName);
		
		boolean exportOk;
		StringBuilder sb = new StringBuilder();
		List<Song> allSongs = songRepository.findAllSongs();
		allSongs.forEach(song -> {
			sb.append(song.getId()).append(Constants.COMMA)
			  .append(song.getArtist()).append(Constants.COMMA)
			  .append(song.getSongTitle()).append(Constants.COMMA)
			  .append(song.getRecordLabel()).append(Constants.COMMA)
			  .append(song.getYear()).append(Constants.COMMA)
			  .append(song.getNumberOfCopies()).append(Constants.COMMA)
			  .append(song.getDuration()).append(Constants.COMMA)
			  .append(song.getSide()).append(Constants.COMMA)
			  .append(song.getSongFormat()).append(Constants.COMMA)
			  .append(song.getSongRpm()).append(Constants.COMMA)
			  .append(song.getGenre()).append(Constants.COMMA)
			  .append(song.getSongBpm()).append(Constants.CR);
		});
		
		try {
			Files.write(Paths.get(fileName.toString()), sb.toString().getBytes());
			exportOk = true;
		} catch (IOException e) {
			exportOk = false;
		}
		return exportOk;
	}
}