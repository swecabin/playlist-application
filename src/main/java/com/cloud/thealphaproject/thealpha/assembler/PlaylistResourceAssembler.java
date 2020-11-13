package com.cloud.thealphaproject.thealpha.assembler;

import com.cloud.thealphaproject.thealpha.entity.PlaylistEntity;
import com.cloud.thealphaproject.thealpha.entity.PlaylistSongMapping;
import com.cloud.thealphaproject.thealpha.resource.PlaylistResource;
import com.cloud.thealphaproject.thealpha.resource.SongResource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaylistResourceAssembler {


    //Get all playlists
    public List<PlaylistResource> convertToResource(List<PlaylistEntity> playlists) {


        List<PlaylistResource> resourceList = new ArrayList<PlaylistResource>();
        for ( int i = 0 ; i < playlists.size(); i++) {
           PlaylistEntity playlistEntity = playlists.get(i);

            PlaylistResource playlist = new PlaylistResource();
            playlist.setPlaylistId(playlistEntity.getPlaylistId());
            playlist.setPlaylistName(playlistEntity.getPlaylistName());

            List<SongResource> songsList = new ArrayList<SongResource>();
            for (int j = 0 ; j < playlistEntity.getSongMapping().size(); j++) {
                PlaylistSongMapping psMapping = playlistEntity.getSongMapping().get(j);
                SongResource songResource = new SongResource();
                songResource.setSongID(psMapping.getSongEntity().getSongID());
                songResource.setSongName(psMapping.getSongEntity().getSongName());
                songResource.setSingers(psMapping.getSongEntity().getSingers());
                songsList.add(songResource);
            }
            playlist.setSongs(songsList);
            resourceList.add(playlist);
        }

        return resourceList;
    }

    public PlaylistEntity convertToEntity(PlaylistResource resource) {
        return PlaylistEntity.builder().playlistName(resource.getPlaylistName()).build();
    }

    //Get playlist by ID
//    public PlaylistResource conver

}
