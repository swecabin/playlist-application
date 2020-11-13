create table Playlist (
PlaylistID INT AUTO_INCREMENT PRIMARY KEY,
PlaylistName varchar(30)
);

create table Songs (
SongID INT AUTO_INCREMENT PRIMARY KEY,
SongName varchar(100),
Singers varchar(200),
ReleaseYear INT
);

create table PlaylistSongMapping (
MappingID INT AUTO_INCREMENT PRIMARY KEY,
PlaylistID INT,
SongID INT,
CONSTRAINT `PSM_Playlist_FK` FOREIGN KEY (PlaylistID) REFERENCES Playlist (PlaylistID) ON DELETE CASCADE,
CONSTRAINT `PSM_Songs_FK` FOREIGN KEY (SongID) REFERENCES Songs (SongID) ON DELETE CASCADE
);


