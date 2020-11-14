package com.cloud.thealphaproject.thealpha.service;

import com.cloud.thealphaproject.thealpha.entity.PlaylistEntity;
import com.cloud.thealphaproject.thealpha.entity.PlaylistSongMapping;
import com.cloud.thealphaproject.thealpha.entity.SongEntity;
import com.cloud.thealphaproject.thealpha.repository.PlaylistRepository;
import com.cloud.thealphaproject.thealpha.repository.PlaylistSongMappingRepository;
import com.cloud.thealphaproject.thealpha.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private PlaylistSongMappingRepository mappingRepository;

    public PlaylistEntity createPlaylist(PlaylistEntity entity) {
        return playlistRepository.save(entity);
    }

    public SongEntity addSongToPlaylist(int playlistId, SongEntity songEntity) throws Exception {

        if (!playlistRepository.existsById(playlistId)) {
            throw new Exception("Invalid playlist");
        }
        SongEntity outputEntity = songRepository.save(songEntity);
        PlaylistSongMapping psmMapping = PlaylistSongMapping.builder().playlistID(playlistId)
                .songEntity(outputEntity).build();
        return mappingRepository.save(psmMapping).getSongEntity();
    }

    public List<PlaylistEntity> getPlaylists() {
        Iterable<PlaylistEntity> playlistIterable = playlistRepository.findAll();
        List<PlaylistEntity> entities = new ArrayList<>();
        Iterator<PlaylistEntity> iterator = playlistIterable.iterator();
        while (iterator.hasNext()) {
            entities.add(iterator.next());
        }
        return entities;
    }

    public void deletePlaylist(int playlistId) {
        playlistRepository.deleteById(playlistId);
    }

    public void removeSongFromPlaylist(int playlistId, int songId) {
        mappingRepository.deleteMapping(playlistId, songId);
    }

}
