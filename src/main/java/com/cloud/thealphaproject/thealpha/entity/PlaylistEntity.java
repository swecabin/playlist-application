package com.cloud.thealphaproject.thealpha.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Playlist")
/*
select
    p.PlaylistID, p.PlaylistName, psm.SongID, s.SongName, s.Singers from Playlist p
    join PlaylistSongMapping psm on p.PlaylistID = psm.PlaylistID
    join Songs s where psm.SongID = s.SongID;
*/
public class PlaylistEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)

    private int playlistId;
    private String playlistName;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "PlaylistID")
    private List<PlaylistSongMapping> songMapping;
}
