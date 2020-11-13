package com.cloud.thealphaproject.thealpha.repository;

import com.cloud.thealphaproject.thealpha.entity.SongEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends CrudRepository<SongEntity, Integer> {

    //public SongEntity findById(int id);
}
