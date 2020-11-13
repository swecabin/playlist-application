package com.cloud.thealphaproject.thealpha.controller;

import com.cloud.thealphaproject.thealpha.assembler.PlaylistResourceAssembler;
import com.cloud.thealphaproject.thealpha.assembler.SongResourceAssembler;
import com.cloud.thealphaproject.thealpha.entity.PingResponse;
import com.cloud.thealphaproject.thealpha.entity.PlaylistEntity;
import com.cloud.thealphaproject.thealpha.entity.SongEntity;
import com.cloud.thealphaproject.thealpha.entity.TestEntity;
import com.cloud.thealphaproject.thealpha.repository.PlaylistRepository;
import com.cloud.thealphaproject.thealpha.repository.PlaylistSongMappingRepository;
import com.cloud.thealphaproject.thealpha.repository.SongRepository;
import com.cloud.thealphaproject.thealpha.repository.TestRepository;
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
    private TestRepository testRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private PlaylistSongMappingRepository mappingRepository;

    @Autowired
    private SongResourceAssembler songResourceAssembler;

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private PlaylistResourceAssembler playlistAssembler;

    @Autowired
    private WebsocketUtil socketUtil;

    @GetMapping(value="ping", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PingResponse> Ping() {
        TestEntity entity = testRepository.findById(1);
        return new ResponseEntity<PingResponse>(PingResponse.builder().message(entity.getName()).build(), HttpStatus.OK);
    }


    //To create playlist
    @PostMapping(value="v1/playlists", produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createPlaylist(@RequestBody
                                                         PlaylistResource resource) {
        PlaylistEntity entity = playlistAssembler.convertToEntity(resource);
        playlistService.createPlaylist(entity);

        socketUtil.broadcastPlaylists();
        return new ResponseEntity(HttpStatus.CREATED);
    }

    //Delete playlist
    @DeleteMapping(value="v1/playlists/{playlistId}")
    public ResponseEntity deletePlaylist(@PathVariable int playlistId) {
        playlistService.deletePlaylist(playlistId);

        socketUtil.broadcastPlaylists();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    //Remove a song from playlist
    @PutMapping(value="v1/playlists/{playlistId}/remove", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity removeSongFromPlaylist(@PathVariable int playlistId, @RequestBody SongResource song) {
        SongEntity songEntity = songResourceAssembler.convertToEntity(song);
        playlistService.removeSongFromPlaylist(playlistId, songEntity);

        socketUtil.broadcastPlaylists();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    //Add song to playlist --> modify playlist mapping, add record to song.
    @GetMapping(value="v1/playlists", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PlaylistResource>> getPlaylists() {
        List<PlaylistEntity> entities = playlistService.getPlaylists();

        List<PlaylistResource> playlists = playlistAssembler.convertToResource(entities);

        socketUtil.broadcastPlaylists();
        return new ResponseEntity<List<PlaylistResource>>(playlists, HttpStatus.OK);
    }

    //Add song to playlist --> modify playlist mapping, add record to song.
    @PostMapping(value="v1/playlists/{playlistId}/add", produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SongResource> addSongToPlaylist(@PathVariable int playlistId,
                                                              @RequestBody SongResource songResource) throws Exception {
        SongEntity songEntity = songResourceAssembler.convertToEntity(songResource);
        playlistService.addSongToPlaylist(playlistId, songEntity);
        //SongResource response = songResourceAssembler.convertToResource(songEntity);

        socketUtil.broadcastPlaylists();
        //return new ResponseEntity(response, HttpStatus.CREATED);
        return new ResponseEntity(HttpStatus.CREATED);
    }




}
