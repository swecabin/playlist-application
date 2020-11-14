var stompClient = null;

$(document).ready(function(){
	
	if(stompClient!=null)
    	stompClient.disconnect();

    	 var socket = new SockJS('/rt-playlist');
    	 stompClient = Stomp.over(socket);

    	 stompClient.connect({}, function (frame) {
    	        stompClient.subscribe('/topic/playlists', function (message) {
    	        	generateTable(message);
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
  // Return a new promise.
  return new Promise(function(resolve, reject) {
    // Do the usual XHR stuff
    var req = new XMLHttpRequest();
    req.open('GET', 'http://localhost:8080/v1/playlists');

    req.onload = function() {
      // This is called even on 404 etc
      // so check the status
      if (req.status == 200) {
        // Resolve the promise with the response text
        resolve(req.response);
      }
      else {
        // Otherwise reject with the status text
        // which will hopefully be a meaningful error
        reject(Error(req.statusText));
      }
    };

    // Handle network errors
    req.onerror = function() {
      reject(Error("Network Error"));
    };

    // Make the request
    req.send();
  });
}

function generateTable(playlists) {
    console.log("Generate table", playlists);

        var jsonPlaylist = JSON.parse(playlists);

        //Create a HTML Table element.
        var table = document.createElement("TABLE");
        table.border = "1";

        //Get the count of columns.
        var columnCount = 3;

        var headers = new Array();
        headers.push(["Name", "Songs", ""]);

        //Add the header row.
        var row = table.insertRow(-1);
        for (var i = 0; i < columnCount; i++) {
            var headerCell = document.createElement("TH");
            headerCell.innerHTML = headers[0][i];
            row.appendChild(headerCell);
        }

        //Add the data rows.
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

                  var deleteButton = document.createElement("BUTTON");
                  deleteButton.setAttribute('id', jsonPlaylist[i].songs[j].songID);
                  deleteButton.textContent = 'Remove song';
                  songRow.appendChild(deleteButton);

                  songTable.appendChild(songRow);
             }

                row.appendChild(songTable);

                var c3 = row.insertCell(-1);

                //Add song button in C3
                var addSongButton = document.createElement("BUTTON");
                addSongButton.setAttribute('id', jsonPlaylist[i].playlistId);
                addSongButton.textContent = 'Add song';
                c3.appendChild(addSongButton);


                //Delete playlist button in C3
                var deletePlaylistButton = document.createElement("BUTTON");
                deletePlaylistButton.setAttribute('id', jsonPlaylist[i].playlistId);
                deletePlaylistButton.textContent = 'Delete playlist';
                deletePlaylistButton.onclick = deletePlaylist(jsonPlaylist[i].playlistId);
                c3.appendChild(deletePlaylistButton);


                table.appendChild(row);
        }

        table.appendChild(row.insertCell(-1));
        //Add new playlist button in last row
        var addNewPlaylistButton = document.createElement("BUTTON");
        addNewPlaylistButton.setAttribute('id', 'add-playlist');
        addNewPlaylistButton.textContent = 'Add new playlist';
        table.appendChild(addNewPlaylistButton);


        var dvTable = document.getElementById("playlist-table");
        dvTable.innerHTML = "";
        dvTable.appendChild(table);

}

function deletePlaylist(playlistID) {

  // Return a new promise.
  return new Promise(function(resolve, reject) {

    //playlistID = document.getElementById()
    // Do the usual XHR stuff
    var req = new XMLHttpRequest();
    req.open('DELETE', 'http://localhost:8080/v1/playlists/'+playlistID);

    req.onload = function() {
      // This is called even on 404 etc
      // so check the status
      if (req.status == 204) {
        // Resolve the promise with the response text
        resolve(req.response);
      }
      else {
        // Otherwise reject with the status text
        // which will hopefully be a meaningful error
        reject(Error(req.statusText));
      }
    };

    // Handle network errors
    req.onerror = function() {
      reject(Error("Network Error"));
    };

    // Make the request
    req.send();
  });
}
