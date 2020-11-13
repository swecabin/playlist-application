package com.cloud.thealphaproject.thealpha.resource;

import lombok.Data;

/* Handles input from the frontend */
@Data
public class SongResource {
    private int songID;
    private String songName;
    private String singers;
    private int releaseYear;

}

