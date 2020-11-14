package com.cloud.thealphaproject.thealpha.assembler;

import com.cloud.thealphaproject.thealpha.entity.PlaylistEntity;
import com.cloud.thealphaproject.thealpha.entity.PlaylistSongMapping;
import com.cloud.thealphaproject.thealpha.resource.PlaylistExtendedResource;
import com.cloud.thealphaproject.thealpha.resource.PlaylistResource;
import com.cloud.thealphaproject.thealpha.resource.SongResource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaylistResourceAssembler {

    public List<PlaylistExtendedResource> convertToExtendedResource(List<PlaylistEntity> playlists) {
        List<PlaylistExtendedResource> resourceList = new ArrayList<>();

        for (PlaylistEntity playlistEntity : playlists) {
            PlaylistExtendedResource playlist = new PlaylistExtendedResource();
            playlist.setPlaylistId(playlistEntity.getPlaylistId());
            playlist.setPlaylistName(playlistEntity.getPlaylistName());

            List<SongResource> songsList = new ArrayList<>();
            if (playlistEntity.getSongMapping() != null) {
                for (PlaylistSongMapping psMapping : playlistEntity.getSongMapping()) {
                    SongResource songResource = new SongResource();
                    BeanUtils.copyProperties(psMapping.getSongEntity(), songResource);
                    songsList.add(songResource);
                }
            }
            playlist.setSongs(songsList);
            resourceList.add(playlist);
        }

        return resourceList;
    }

    public PlaylistEntity convertToEntity(PlaylistResource resource) {
        PlaylistEntity entity = new PlaylistEntity();
        BeanUtils.copyProperties(resource, entity);
        return entity;
    }


    public PlaylistResource convertToResource(PlaylistEntity entity) {
        PlaylistResource resource = new PlaylistResource();
        BeanUtils.copyProperties(entity, resource);
        return resource;
    }

}
