package com.cloud.thealphaproject.thealpha.websocket;

import com.cloud.thealphaproject.thealpha.assembler.PlaylistResourceAssembler;
import com.cloud.thealphaproject.thealpha.entity.PlaylistEntity;
import com.cloud.thealphaproject.thealpha.resource.PlaylistExtendedResource;
import com.cloud.thealphaproject.thealpha.service.PlaylistService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebsocketUtil {

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private PlaylistResourceAssembler playlistResourceAssembler;

    @Autowired
    private SimpMessagingTemplate webSocket;

    @Autowired
    private ObjectMapper mapper;

    @Async
    public void broadcastPlaylists() {
        List<PlaylistEntity> entities = playlistService.getPlaylists();
        List<PlaylistExtendedResource> playlists = playlistResourceAssembler.convertToExtendedResource(entities);
        try {
            String message = mapper.writeValueAsString(playlists);
            webSocket.convertAndSend("/topic/playlists", new OutputMessage(message));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
