package com.cloud.thealphaproject.thealpha.repository;

import com.cloud.thealphaproject.thealpha.entity.PlaylistEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository  extends CrudRepository<PlaylistEntity, Integer> {

//    public PlaylistEntity findById(int id);
}
