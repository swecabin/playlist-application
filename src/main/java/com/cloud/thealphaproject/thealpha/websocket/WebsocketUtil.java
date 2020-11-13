package com.cloud.thealphaproject.thealpha.websocket;

import com.cloud.thealphaproject.thealpha.assembler.PlaylistResourceAssembler;
import com.cloud.thealphaproject.thealpha.entity.PlaylistEntity;
import com.cloud.thealphaproject.thealpha.resource.PlaylistResource;
import com.cloud.thealphaproject.thealpha.service.PlaylistService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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

    public void broadcastPlaylists() {

        System.out.println("Sending message");

        List<PlaylistEntity> entities = playlistService.getPlaylists();

        List<PlaylistResource> playlists = playlistResourceAssembler.convertToResource(entities);

        ObjectMapper mapper = new ObjectMapper();
        String message = null;
        try {
            message = mapper.writeValueAsString(playlists);
            System.out.println("Sent message:"+ message);
            webSocket.convertAndSend("/topic/playlists", new OutputMessage(message));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
