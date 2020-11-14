var stompClient = null;

$(document).ready(function(){
	
	if(stompClient!=null)
    	stompClient.disconnect();

    	 var socket = new SockJS('/rt-playlist');
    	 stompClient = Stomp.over(socket);

    	 stompClient.connect({}, function (frame) {
    	        stompClient.subscribe('/topic/playlists', function (message) {
    	        	generateTable(JSON.parse(message.body).text);
    	        	console.log(message);
    	        });
    	    });

        var playlists =  getPlaylists().then(function(response) {
                     console.log("Get playlists returned:", response);
                     generateTable(response);
                     return response;
            }, function(error) {
                       console.error("Failed!", error);
                           return "failure";
        });

});

function getPlaylists() {
  return new Promise(function(resolve, reject) {
    var req = new XMLHttpRequest();
    req.open('GET', 'http://localhost:8080/v1/playlists');

    req.onload = function() {
      if (req.status == 200) {
        resolve(req.response);
      }
      else {
        reject(Error(req.statusText));
      }
    };

    req.onerror = function() {
      reject(Error("Network Error"));
    };

    req.send();
  });
}

function generateTable(playlists) {
    console.log("Generate table", playlists);

        var jsonPlaylist = JSON.parse(playlists);

        var table = document.createElement("TABLE");
        table.border = "1";

        var columnCount = 3;

        var headers = new Array();
        headers.push(["Name", "Songs", ""]);

        var row = table.insertRow(-1);
        for (var i = 0; i < columnCount; i++) {
            var headerCell = document.createElement("TH");
            headerCell.innerHTML = headers[0][i];
            row.appendChild(headerCell);
        }

        for (var i = 0; i < jsonPlaylist.length; i++) {
            row = table.insertRow(-1);

             var c1 = row.insertCell(-1);
             c1.innerHTML = jsonPlaylist[i].playlistName;


             var songTable = document.createElement("TABLE");
             songTable.border = "1";

             for (var j = 0 ; j < jsonPlaylist[i].songs.length; j++) {
                  var songRow = songTable.insertRow(-1);
                  var songName = songRow.insertCell(-1);
                  songName.innerHTML = jsonPlaylist[i].songs[j].songName;
                  songRow.appendChild(songName);

                  var singers = songRow.insertCell(-1);
                  singers.innerHTML = jsonPlaylist[i].songs[j].singers;
                  songRow.appendChild(singers);

                  //Remove song from playlist
                  var deleteButton = document.createElement("BUTTON");
                  deleteButton.setAttribute('id', jsonPlaylist[i].songs[j].songID);
                  deleteButton.setAttribute('playlistId', jsonPlaylist[i].playlistId);
                  deleteButton.textContent = 'Remove song';
                  deleteButton.addEventListener('click', function() {
                    removeSongFromPlaylist(deleteButton.getAttribute('playlistId'), deleteButton.getAttribute('id'));
                  });

                  songRow.appendChild(deleteButton);

                  songTable.appendChild(songRow);
             }

                row.appendChild(songTable);

                var c3 = row.insertCell(-1);

                //Add song button in C3
                var addSongButton = document.createElement("BUTTON");
                addSongButton.setAttribute('id', jsonPlaylist[i].playlistId);
                addSongButton.textContent = 'Add song';
                addSongButton.addEventListener('click', function() {
                  addSongToPlaylist(addSongButton.getAttribute('id'));
                });
                c3.appendChild(addSongButton);


                //Delete playlist button in C3
                var deletePlaylistButton = document.createElement("BUTTON");
                deletePlaylistButton.setAttribute('id', jsonPlaylist[i].playlistId);
                deletePlaylistButton.textContent = 'Delete playlist';
                deletePlaylistButton.addEventListener('click', function() {
                  deletePlaylist(deletePlaylistButton.getAttribute('id'));
                });
                c3.appendChild(deletePlaylistButton);


                table.appendChild(row);
        }

        table.appendChild(row.insertCell(-1));

        //Add new playlist button in last row
        var createPlaylistButton = document.createElement("BUTTON");
        createPlaylistButton.setAttribute('id', 'add-playlist');
        createPlaylistButton.textContent = 'Add new playlist';
        createPlaylistButton.addEventListener('click', function() {
            createPlaylist();
        });
        table.appendChild(createPlaylistButton);


        var dvTable = document.getElementById("playlist-table");
        dvTable.innerHTML = "";
        dvTable.appendChild(table);

}

function deletePlaylist(playlistID) {

  return new Promise(function(resolve, reject) {

    var req = new XMLHttpRequest();
    req.open('DELETE', 'http://localhost:8080/v1/playlists/'+playlistID);

    req.onload = function() {
      if (req.status == 204) {
        resolve(req.response);
      }
      else {
        reject(Error(req.statusText));
      }
    };

    req.onerror = function() {
      reject(Error("Network Error"));
    };

    req.send();
  });
}

function removeSongFromPlaylist(playlistID, songID) {
    return new Promise(function(resolve, reject) {

    var req = new XMLHttpRequest();
    req.open('DELETE', 'http://localhost:8080/v1/playlists/'+playlistID+'/songs/'+songID);

    req.onload = function() {
      if (req.status == 204) {
        resolve(req.response);
      }
      else {
        reject(Error(req.statusText));
      }
    };

    req.onerror = function() {
      reject(Error("Network Error"));
    };

    req.send();
  });
}

function addSongToPlaylist(playlistID) {
return new Promise(function(resolve, reject) {

    var req = new XMLHttpRequest();

    // create a JSON object
    var payload = {
        "songName": Math.random().toString(36).substr(2, 5),
        "singers": Math.random().toString(36).substr(2, 10),
        "releaseYear": 2019
    };
    req.open('POST', 'http://localhost:8080/v1/playlists/'+playlistID+'/add');
    req.setRequestHeader('Content-Type', 'application/json');

    req.onload = function() {
      if (req.status == 201) {
        resolve(req.response);
      }
      else {
        reject(Error(req.statusText));
      }
    };

    req.onerror = function() {
      reject(Error("Network Error"));
    };

    req.send(JSON.stringify(payload));
  });
}


function createPlaylist() {
return new Promise(function(resolve, reject) {

    var req = new XMLHttpRequest();

    // create a JSON object
    var payload = {
        "playlistName": Math.random().toString(36).substr(2, 5)
    };
    req.open('POST', 'http://localhost:8080/v1/playlists');
    req.setRequestHeader('Content-Type', 'application/json');

    req.onload = function() {
      if (req.status == 201) {
        resolve(req.response);
      }
      else {
        reject(Error(req.statusText));
      }
    };

    req.onerror = function() {
      reject(Error("Network Error"));
    };

    req.send(JSON.stringify(payload));
  });
}