package com.recordcrate.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.recordcrate.convertor.SongConvertor;
import com.recordcrate.domain.Song;
import com.recordcrate.domain.SongDto;
import com.recordcrate.repository.SongRepository;
import com.recordcrate.utils.Constants;

@Service
public class SongService {

	@Autowired
	private EntityManager em;
	
	@Autowired
	private SongConvertor songConvertor;

	@Autowired
    private SongRepository songRepository;
	
	public Song getSong(Long id) {
		return songRepository.findOne(id);
	}

	public void deleteSong(Map<?, ?> songDetails) {
		Long id = Long.parseLong((String)songDetails.get(Constants.SONG_FIELDS.SONG_ID));
		songRepository.delete(id);
	}

	public Song storeSong(Map<?, ?> songDetails) {
		Long songId = (StringUtils.isEmpty(songDetails.get(Constants.SONG_FIELDS.SONG_ID))) ? 0 :
			Long.parseLong((String)songDetails.get(Constants.SONG_FIELDS.SONG_ID));
		String artist = (String) songDetails.get(Constants.SONG_FIELDS.ARTIST);
		String title  = (String) songDetails.get(Constants.SONG_FIELDS.TITLE);
		String recordLabel = (String) songDetails.get(Constants.SONG_FIELDS.RECORD_LABEL);
		String year = (String) songDetails.get(Constants.SONG_FIELDS.YEAR);
		String copies = (String) songDetails.get(Constants.SONG_FIELDS.NO_OF_COPIES);
		String duration = (String) songDetails.get(Constants.SONG_FIELDS.DURATION);
		String side = (String) songDetails.get(Constants.SONG_FIELDS.SIDE);
		String songFormat = (String) songDetails.get(Constants.SONG_FIELDS.SONG_FORMAT);
		String genre = (String) songDetails.get(Constants.SONG_FIELDS.GENRE);
		String rpm = (String) songDetails.get(Constants.SONG_FIELDS.SONG_RPM);
		String bpm = (String) songDetails.get(Constants.SONG_FIELDS.SONG_BPM);
		
		Song song = (songId == 0) ? new Song() : getSong(songId); 
		song.setArtist(artist);
		song.setSongTitle(title);
		song.setRecordLabel(recordLabel);;
		song.setYear(year);
		song.setNumberOfCopies(copies);
		song.setDuration(duration);
		song.setSide(side);
		song.setSongFormat(songFormat);
		song.setGenre(genre);
		song.setSongRpm(rpm);
		song.setSongBpm(bpm);
		Song updatedSong = songRepository.save(song);
		return updatedSong;
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getAllSongs(Integer pageNo, Integer noOfSongsToDisplay, Integer sortField, 
			String sortType, String searchFieldNumber, String searchValue, Boolean getAllSongs) {
		List<Song> songs = null;
		if (getAllSongs) {
			songs = songRepository.findAllSongs();
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append(Constants.SQL.QUERY1)
			  .append(Constants.SORT_FIELDS.get(Integer.parseInt(searchFieldNumber)))
			  .append(Constants.SQL.QUERY2);
			Query q = em.createQuery(sb.toString());
			q.setParameter("searchValue", "%" + searchValue + "%");
			songs = q.getResultList();
		}
		return convertSongs(songs, pageNo, noOfSongsToDisplay, sortField, sortType);
	}
	
	public List<HashMap<String, Object>> findSongsByRPM() {
		List<HashMap<String, Object>> songsByMap = new ArrayList<HashMap<String, Object>>();
		List<Object[]> songs = null;
		songs = songRepository.findSongsByRPM();
		songs.forEach(item -> {
			HashMap<String, Object> map = new HashMap<String, Object>();
			StringBuilder sb = new StringBuilder();
			sb.append(item[0]).append(Constants.SPACE).append(Constants.OPEN_BRACKET)
			  .append(item[1]).append(" songs").append(Constants.CLOSE_BRACKET);
			map.put("name", sb.toString());
			map.put("y", item[1]);
			songsByMap.add(map);
		});
		return songsByMap;
	}

	public List<Object> findSongsByFormat() {
		List<Object[]> songs = null;
		List<Object> categories = new ArrayList<Object>();
		List<Object> formatData = new ArrayList<Object>();
		List<Object> songData   = new ArrayList<Object>();
		songs = songRepository.findSongsByFormat();
		songs.forEach(item -> {
			categories.add(item[0]);
			formatData.add(item[1]);
		});
		songData.add(categories);
		songData.add(formatData);
		return songData;
	}
	
	public List<HashMap<String, String>> fetchSongHeaders() {
		List<HashMap<String, String>> songHdrList = new ArrayList<HashMap<String, String>>();
		setHeaderDetails(songHdrList, "15%", "Artist", "artistUp", "Order by ascending artist", 
			"artistDown", "Order by descending artist", "1");
		setHeaderDetails(songHdrList, "15%", "Song Title", "songTitleUp", "Order by ascending song title", 
			"songTitleDown", "Order by descending song title", "2");
		setHeaderDetails(songHdrList, "", "Record Label", "recordLabelUp", "Order by ascending record label",
			"recordLabelDown", "Order by descending record label", "3");
		setHeaderDetails(songHdrList, "5%", "Year", "yearUp", "Order by ascending year",
			"yearDown", "Order by descending year", "4");
		setHeaderDetails(songHdrList, "7%", "Copies", "copiesUp", "Order by ascending number of copies",
			"copiesDown", "Order by descending number of copies", "5");
		setHeaderDetails(songHdrList, "7%", "Duration", "durationUp", "Order by ascending duration",
			"durationDown", "Order by descending duration", "6");
		setHeaderDetails(songHdrList, "8%", "Song Format", "songFormatUp", "Order by ascending song format",
			"songFormatDown", "Order by descending song format", "7");
		setHeaderDetails(songHdrList, "5%", "Side", "sideUp", "Order by ascending side",
				"sideDown", "Order by descending side", "8");
		setHeaderDetails(songHdrList, "", "Genre", "genreUp", "Order by ascending genre",
			"genreDown", "Order by descending genre", "9");
		setHeaderDetails(songHdrList, "7%", "Song BPM", "songBpmUp", "Order by ascending song bpm",
			"songBpmDown", "Order by descending song bpm", "10");
		setHeaderDetails(songHdrList, "7%", "Song RPM", "songRpmUp", "Order by ascending song rpm",
			"songRpmDown", "Order by descending song rpm", "11");
		return songHdrList;
	}
	
	public void setHeaderDetails(List<HashMap<String, String>> hdrMapList, String thWidth, String hdrName, String idUp, 
			String idUpTitle, String idDown, String idDownTitle, String sortOrder) {
		HashMap<String, String> hdrMap = new HashMap<String, String>();
		hdrMap.put("thWidth", thWidth);
		hdrMap.put("hdrName", hdrName);
		hdrMap.put("idUp", idUp);
		hdrMap.put("idUpTitle", idUpTitle);
		hdrMap.put("idDown", idDown);
		hdrMap.put("idDownTitle", idDownTitle);	
		hdrMap.put("sortField", sortOrder);	
		hdrMapList.add(hdrMap);
	}
	
	public SongDto getLastSong() {
		Song song = songRepository.findLastSongEntered();
		return songConvertor.convertSongToSongDto(song);
	}

	private List<Song> sortSongs(List<Song> songs, Integer sortField, String sortType) {
		songs.sort((song1, song2) -> {
        	int compareTo = 0;
        	String field1 = "";
        	String field2 = "";;
        	boolean integerField = false;
        	switch (sortField) {
        	case 1:
        		field1 = song1.getArtist();
        		field2 = song2.getArtist();
        		break;
        	case 2:
        		field1 = song1.getSongTitle();
        		field2 = song2.getSongTitle();
        		break;
        	case 3:
        		field1 = song1.getRecordLabel();
        		field2 = song2.getRecordLabel();
        		break;
        	case 4:
        		field1 = song1.getYear().toString();
        		field2 = song2.getYear().toString();
        		integerField = true;
    			break;
        	case 5:
        		field1 = song1.getNumberOfCopies().toString();
        		field2 = song2.getNumberOfCopies().toString();
        		integerField = true;
        		break;
        	case 6:
        		field1 = song1.getDuration();
        		field2 = song2.getDuration();
        		break;
        	case 7:
        		field1 = song1.getSongFormat();
        		field2 = song2.getSongFormat();
				break;
        	case 8:
        		field1 = song1.getSide();
        		field2 = song2.getSide();
        		break;
        	case 9:
        		field1 = song1.getGenre();
        		field2 = song2.getGenre();
        		break;
        	case 10:
        		field1 = song1.getSongBpm().toString();
        		field2 = song2.getSongBpm().toString();
        		integerField = true;
        		break;
        	case 11:
        		field1 = song1.getSongRpm();
        		field2 = song2.getSongRpm();
        		break;
			default:
				break;
			}
        	if (Constants.SORT.ASC.equals(sortType)) {
    			compareTo = (integerField) ? new Integer(field1).compareTo(new Integer(field2)) : field1.compareTo(field2);
    		} else if (Constants.SORT.DESC.equals(sortType)) {
    			compareTo = (integerField) ? new Integer(field2).compareTo(new Integer(field1)) : field2.compareTo(field1);
    		}
            return compareTo;
        });
		return songs;
	}

	private HashMap<String, Object> convertSongs(List<Song> songs, Integer pageNo, Integer noOfSongsToDisplay, Integer sortField, String sortType) {
		List<SongDto> allSongs = new ArrayList<SongDto>();
		songs = sortSongs(songs, sortField, sortType);
		songs.forEach(song -> {
			allSongs.add(songConvertor.convertSongToSongDto(song));
		});
    	HashMap<String, Object> returnResultList = new HashMap<String, Object>();
    	List<SongDto> pageOfResults = new ArrayList<SongDto>();

		int numberOfPages = allSongs.size() / noOfSongsToDisplay;
		int remainderRecords = allSongs.size() % noOfSongsToDisplay;
	    int totalSongPages = numberOfPages < 1 ? 1 : numberOfPages;
	    if (numberOfPages > 0 && remainderRecords > 0) {
	    	totalSongPages++;
	    }
	    int pageSize = (allSongs.size() < noOfSongsToDisplay) ? 
	    	allSongs.size() : noOfSongsToDisplay;
	    int currentPageStartValue = (pageNo - 1) * pageSize; 
	    int currentPageEndValue = currentPageStartValue + pageSize;
	    currentPageEndValue = (currentPageEndValue > allSongs.size()) ? 
	    	allSongs.size() : currentPageEndValue;
	    if (currentPageStartValue > currentPageEndValue) {
	    	currentPageStartValue = currentPageEndValue - pageSize;
	    }
	    
		pageOfResults = allSongs.subList(currentPageStartValue, currentPageEndValue);
		
		returnResultList.put("totalSongsSelected", allSongs.size());
		returnResultList.put("totalSongPages", totalSongPages);
		returnResultList.put("pageOfResults", pageOfResults);

		return returnResultList;
	}

}