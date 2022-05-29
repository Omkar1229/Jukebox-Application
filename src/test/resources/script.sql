create table user(User_Id int primary key auto_increment,name char(20) not null)auto_increment=1;
insert into user(name) values('user1'),('user2');

create table artist(Artist_Id int primary key auto_increment,name char(20) not null)auto_increment=1001;
insert into artist(name) values('artist1'),('artist2'),('artist3');

create table podcast(podcast_id int primary key auto_increment,name char(40) not null,artist_id int,constraint fk_p foreign key (artist_id) references artist(artist_id)on update cascade)auto_increment=2001;
insert into podcast(name,artist_Id) values('podcastA',1001),('podcastB',1002),('podcastC',1001);

create table podcastdetails(id int primary key auto_increment,episodeName char(30),Releasedate date,podcastUrl char(200) not null,podcast_Id int,constraint fk_pd foreign key (podcast_id) references podcast(podcast_id)on update cascade)auto_increment=2101;
insert into podcastdetails(episodeName,releasedate,podcasturl,podcast_Id) values('ep3','2021-06-01','urladdr(ep3)',2001),('ep1','2021-06-01','urladdr(ep1)',2001),('ep2','2021-08-04','urladdr(ep1)',2001),('ep1','2021-12-01','urladdr(ep1)',2002),('ep2','2021-01-04','urladdr(ep1)',2002);

create table song(song_id int primary key auto_increment,songname char(40),genre char(30),album char(40),songUrl char(200) not null,songDuration time,artist_id int,constraint fk_s foreign key (artist_id) references artist(artist_id)on update cascade)auto_increment=4001;
insert into song(songname,genre,album,songurl,songduration,artist_id) values('song1','remix','album1','C:\\Users\\sayal\\Downloads\\Roobaroo-_SongsMp3.wav','00:03:25',1001);
insert into song(songname,genre,album,songurl,songduration,artist_id) values('song2','remix','album1','C:\\Users\\sayal\\Downloads\\Roobaroo-_SongsMp3.wav','00:03:25',1001);
insert into song(songname,genre,album,songurl,songduration,artist_id) values('song3','rock','album2','C:\\Users\\sayal\\Downloads\\Roobaroo-_SongsMp3.wav','00:03:25',1003);
insert into song(songname,genre,album,songurl,songduration,artist_id) values('song4','rock','album2','C:\\Users\\sayal\\Downloads\\Roobaroo-_SongsMp3.wav','00:03:25',1003);


create table playlist(playlistId int primary key auto_increment,name char(30),user_id int,constraint fk_pl foreign key (user_id) references user(user_id)on update cascade)auto_increment=8001;
insert into playlist(name,user_id) values('travel',1),('adventure',1),('party',2),('motivational',2);

create table playlistMapping(id int primary key auto_increment,songId int,constraint fk_si foreign key (songId) references song(song_id)on update cascade,podcastid int,constraint fk_pi foreign key (PodcastId) references podcastdetails(id)on update cascade ,position int not null,mediaType char(40),playlistId int,constraint fk_pm foreign key (playlistid) references playlist(playlistid)on update cascade)auto_increment=10001;
insert into playlistMapping(songid,podcastid,position,mediatype,playlistId) values(4002,null,1,'song',8002),(null,2103,2,'podcast',8004);
