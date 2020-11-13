package com.cloud.thealphaproject.thealpha.service;

import com.cloud.thealphaproject.thealpha.entity.PlaylistEntity;
import com.cloud.thealphaproject.thealpha.entity.PlaylistSongMapping;
import com.cloud.thealphaproject.thealpha.entity.SongEntity;
import com.cloud.thealphaproject.thealpha.repository.PlaylistRepository;
import com.cloud.thealphaproject.thealpha.repository.PlaylistSongMappingRepository;
import com.cloud.thealphaproject.thealpha.repository.SongRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public SongEntity addSongToPlaylist(int playlistId, SongEntity songEntity) throws Exception {

        if (!playlistRepository.existsById(playlistId)) {
            throw new Exception("Invalid playlist");
        }

        songRepository.save(songEntity);

        PlaylistSongMapping psmMapping = PlaylistSongMapping.builder().playlistID(playlistId)
                .songID(songEntity.getSongID()).build();
        mappingRepository.save(psmMapping);

        return songEntity;
    }

    public List<PlaylistEntity> getPlaylists() {

        Iterable<PlaylistEntity> playlistIterable = playlistRepository.findAll();
        List<PlaylistEntity> entities = new ArrayList<PlaylistEntity>();

        if (playlistIterable != null) {
            Iterator<PlaylistEntity> iterator = playlistIterable.iterator();
            while(iterator.hasNext()) {
                PlaylistEntity entity = iterator.next();
                entities.add(entity);
                ObjectMapper mapper = new ObjectMapper();
                try {
                    System.out.println(mapper.writeValueAsString(entity));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();

                }
            }
        }
        return entities;
    }

}
