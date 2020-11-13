package com.cloud.thealphaproject.thealpha.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Songs")
public class SongEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int songID;

    private String songName;
    private String singers;
    private int releaseYear;

}
