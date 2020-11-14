package com.cloud.thealphaproject.thealpha.resource;

import lombok.Data;

import java.util.List;

@Data
public class PlaylistExtendedResource extends PlaylistResource {


    private List<SongResource> songs;
}

/*
{
    "playlist_id": 1,
    "playlist_name": "abc",
    "songs":  [
            {
                "song_id": 1,
                "song_name": "abc"
            },
            {
                "song_id": 2,
                "song_name": "abc"
            }
            ]
}
 */
