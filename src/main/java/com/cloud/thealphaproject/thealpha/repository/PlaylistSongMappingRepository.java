package com.cloud.thealphaproject.thealpha.repository;

import com.cloud.thealphaproject.thealpha.entity.PlaylistSongMapping;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface PlaylistSongMappingRepository extends CrudRepository<PlaylistSongMapping, Integer> {
    
    @Transactional
    @Modifying
    @Query(value = "delete from PlaylistSongMapping where PlaylistID = ?1 and SongID = ?2")
    void deleteMapping(int playlistId, int songId);

}
