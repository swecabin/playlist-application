package com.cloud.thealphaproject.thealpha.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="PlaylistSongMapping")
public class PlaylistSongMapping {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int mappingID;

    private int playlistID;
    //private int songID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SongID")
    private SongEntity songEntity;

}
