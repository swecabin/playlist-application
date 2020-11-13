package com.cloud.thealphaproject.thealpha.repository;

import com.cloud.thealphaproject.thealpha.entity.PlaylistSongMapping;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistSongMappingRepository extends CrudRepository<PlaylistSongMapping, Integer> {

    //public PlaylistSongMapping findById(int id);
}
