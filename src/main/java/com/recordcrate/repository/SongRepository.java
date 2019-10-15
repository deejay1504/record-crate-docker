package com.recordcrate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.recordcrate.domain.Song;
import com.recordcrate.utils.Constants;

@RepositoryRestResource(collectionResourceRel = "song", path = "song")
public interface SongRepository extends CrudRepository<Song, Long> {

	@Query(nativeQuery = true, value = Constants.SQL.ALL_SONGS)
	List<Song> findAllSongs();

	@Query(nativeQuery = true, value = Constants.SQL.BY_FORMAT)
	List<Object[]> findSongsByFormat();

	@Query(nativeQuery = true, value = Constants.SQL.BY_RPM)
    List<Object[]> findSongsByRPM();

    @Query(nativeQuery = true, value = Constants.SQL.LAST_SONG)
    Song findLastSongEntered();

}
