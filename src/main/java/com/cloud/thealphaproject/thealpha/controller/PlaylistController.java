package com.cloud.thealphaproject.thealpha.controller;

import com.cloud.thealphaproject.thealpha.assembler.PlaylistResourceAssembler;
import com.cloud.thealphaproject.thealpha.assembler.SongResourceAssembler;
import com.cloud.thealphaproject.thealpha.entity.PlaylistEntity;
import com.cloud.thealphaproject.thealpha.entity.SongEntity;
import com.cloud.thealphaproject.thealpha.resource.PlaylistExtendedResource;
import com.cloud.thealphaproject.thealpha.resource.PlaylistResource;
import com.cloud.thealphaproject.thealpha.resource.SongResource;
import com.cloud.thealphaproject.thealpha.service.PlaylistService;
import com.cloud.thealphaproject.thealpha.websocket.WebsocketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class PlaylistController {

    @Autowired
    private SongResourceAssembler songResourceAssembler;

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private PlaylistResourceAssembler playlistAssembler;

    @Autowired
    private WebsocketUtil socketUtil;

    @PostMapping(value="v1/playlists", produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlaylistResource> createPlaylist(@RequestBody PlaylistResource resource) {
        PlaylistEntity entity = playlistAssembler.convertToEntity(resource);
        PlaylistEntity responseEntity = playlistService.createPlaylist(entity);
        PlaylistResource outputResource = playlistAssembler.convertToResource(responseEntity);
        socketUtil.broadcastPlaylists();
        return new ResponseEntity<>(outputResource, HttpStatus.CREATED);
    }

    @PostMapping(value="v1/playlists/{playlistId}/add", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SongResource> addSongToPlaylist(@PathVariable int playlistId,
                                                          @RequestBody SongResource songResource) throws Exception {
        SongEntity songEntity = songResourceAssembler.convertToEntity(songResource);
        SongEntity responseSongEntity = playlistService.addSongToPlaylist(playlistId, songEntity);
        SongResource response = songResourceAssembler.convertToResource(responseSongEntity);
        socketUtil.broadcastPlaylists();
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @GetMapping(value="v1/playlists", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PlaylistExtendedResource>> getPlaylists() {
        List<PlaylistEntity> entities = playlistService.getPlaylists();
        List<PlaylistExtendedResource> playlists = playlistAssembler.convertToExtendedResource(entities);
        socketUtil.broadcastPlaylists();
        return new ResponseEntity(playlists, HttpStatus.OK);
    }

    @DeleteMapping(value="v1/playlists/{playlistId}/songs/{songId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity removeSongFromPlaylist(@PathVariable int playlistId, @PathVariable int songId) {
        playlistService.removeSongFromPlaylist(playlistId, songId);
        socketUtil.broadcastPlaylists();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value="v1/playlists/{playlistId}")
    public ResponseEntity deletePlaylist(@PathVariable int playlistId) {
        playlistService.deletePlaylist(playlistId);
        socketUtil.broadcastPlaylists();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
