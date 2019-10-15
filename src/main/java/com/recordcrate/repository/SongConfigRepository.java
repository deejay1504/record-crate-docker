package com.recordcrate.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.recordcrate.domain.SongConfig;

@RepositoryRestResource
public interface SongConfigRepository extends CrudRepository<SongConfig, Long> {
}
