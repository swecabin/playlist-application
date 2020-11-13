
var stompClient = null;

$(document).ready(function(){
	
	if(stompClient!=null)
    		stompClient.disconnect();

    	 var socket = new SockJS('/rt-playlist');
    	 stompClient = Stomp.over(socket);

    	 stompClient.connect({}, function (frame) {
    	        stompClient.subscribe('/topic/playlists', function (message) {
    	        	var playlists = JSON.parse(message.body);
    	        	alert(playlists);
    	        	console.log(playlists);
    	        });
    	    });


});