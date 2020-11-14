A basic spring boot application to manage playlist with websockets. The entire backend is built using SpringBoot and Hibernate. 

Functionalities:
1. Create a playlist with random name
2. Add a song with random name to the playlist
3. Remove a song from playlist
4. Delete a playlist
5. All the CUD operations are updated in realtime in the webpage using STOMP websockets. 


Pre-requisites:
MySQL installed
To install MySQL in docker, follow the below steps:
docker run -p 3306:3306 -d --name mysql -e MYSQL_ROOT_PASSWORD=password mysql/mysql-server
docker exec -it mysql bash
mysql -uroot -ppassword

Initial DB migration:
Follow the commands in resources/db-schema/scripts.up.sql

Swagger documentation - http://localhost:8080/swagger-ui.html

Future works:
1. Considering the simplicity of the application, there are no duplicate validations handled for songs or playlist names. 
2. Data in the database can be cached. 
3. Websockets currently push all the playlist data for any kind of CUD operation in the backend. Should be an event based system, where required data is only passed. 
