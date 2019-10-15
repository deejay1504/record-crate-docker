/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.recordcrate.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.recordcrate.domain.Song;
import com.recordcrate.domain.SongDto;
import com.recordcrate.service.SongService;

import lombok.Data;

@RestController
@Data
@RequestMapping("/api")
public class SongController {

    @Autowired
    private SongService songService;
    
    @RequestMapping("/")
	public ModelAndView firstPage() {
		return new ModelAndView("welcome");
	}
    
    @RequestMapping(
    		method = RequestMethod.GET,
    		value = "/fetch/song",
    		produces = "application/json")
    public Song fetchSong(
    		@RequestParam(value = "id", required = false) Long id) {
    	return songService.getSong(id);
    }

    @RequestMapping(
    		method = RequestMethod.GET,
    		value = "/fetch/allsongs",
    		produces = "application/json")
    public ResponseEntity<HashMap<String, Object>> fetchAllSongs(
    		@RequestParam(value = "pageNo", required = false) Integer pageNo,
    		@RequestParam(value = "noOfSongsToDisplay", required = false) Integer noOfSongsToDisplay,
    		@RequestParam(value = "sortField", required = false) Integer sortFieldNumber,
    		@RequestParam(value = "sortType", required = false) String sortType,
    		@RequestParam(value = "searchFieldNumber", required = false) String searchFieldNumber,
    		@RequestParam(value = "searchValue", required = false) String searchValue,
    		@RequestParam(value = "getAllSongs", required = true) Boolean getAllSongs) {
    	HashMap<String, Object> pageOfResults = songService.getAllSongs(pageNo, 
    		noOfSongsToDisplay, sortFieldNumber, sortType, searchFieldNumber, searchValue, getAllSongs);
    	return new ResponseEntity<HashMap<String, Object>>(pageOfResults, HttpStatus.OK);
    }

    @RequestMapping(
    		method = RequestMethod.GET,
    		value = "/fetch/format",
    		produces = "application/json")
    public ResponseEntity<List<Object>> findSongsByFormat() {
    	List<Object> songFormat = songService.findSongsByFormat();
    	return new ResponseEntity<List<Object>>(songFormat, HttpStatus.OK);
    }

    @RequestMapping(
    		method = RequestMethod.GET,
    		value = "/fetch/rpm",
    		produces = "application/json")
    public ResponseEntity<List<HashMap<String, Object>>> findSongsByRPM() {
    	List<HashMap<String, Object>> songsByGenre = songService.findSongsByRPM();
    	return new ResponseEntity<List<HashMap<String, Object>>>(songsByGenre, HttpStatus.OK);
    }

    @RequestMapping(
    	method = RequestMethod.GET,
    	value = "/fetch/tableheaders",
    	produces = "application/json")
    public ResponseEntity<List<HashMap<String, String>>> getTableHeaders() {
    	List<HashMap<String, String>> songHeaders = songService.fetchSongHeaders();
    	return new ResponseEntity<List<HashMap<String, String>>>(songHeaders, HttpStatus.OK);
    }
    
    @RequestMapping(
    		method = RequestMethod.POST,
    		value = "/save/song",
    		produces = "application/json",
    		consumes = "application/json")
    public ResponseEntity<Boolean> storeSong(@RequestBody Map<?, ?> songDetails) {
    	Song updatedSong = songService.storeSong(songDetails);
    	Boolean updateOk = (updatedSong == null) ? false : true;
    	return new ResponseEntity<Boolean>(updateOk, HttpStatus.OK);
    }

    @RequestMapping(
    	method = RequestMethod.POST,
        value = "/delete/song",
        produces = "application/json")
    public ResponseEntity<Boolean> deleteSong(@RequestBody Map<?, ?> songDetails) {
        songService.deleteSong(songDetails);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
    
    @RequestMapping(
    		method = RequestMethod.GET,
    		value = "/fetch/last/song",
    		produces = "application/json")
    public ResponseEntity<SongDto> fetchLastSong() {
    	SongDto songDto = songService.getLastSong();
    	return new ResponseEntity<SongDto>(songDto, HttpStatus.OK);
    }

}