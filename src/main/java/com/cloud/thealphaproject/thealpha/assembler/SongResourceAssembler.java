package com.cloud.thealphaproject.thealpha.assembler;

import com.cloud.thealphaproject.thealpha.entity.SongEntity;
import com.cloud.thealphaproject.thealpha.resource.SongResource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class SongResourceAssembler {

    public SongEntity convertToEntity(SongResource resource) {
        SongEntity entity = new SongEntity();
        BeanUtils.copyProperties(resource, entity);
        return entity;
    }

    public SongResource convertToResource(SongEntity entity) {
        SongResource resource = new SongResource();
        BeanUtils.copyProperties(entity, resource);
        return resource;
    }
}
