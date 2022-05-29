


create table user(User_Id int primary key auto_increment,name char(20) not null)auto_increment=1;

create table playlist(playlistId int primary key auto_increment,name char(30),user_id int,constraint fk_pl foreign key (user_id) references user(user_id)on update cascade)auto_increment=8001;

create table playlistMapping(id int primary key auto_increment,songId int,constraint fk_si foreign key (songId) references song(song_id)on update cascade,podcastid int,constraint fk_pi foreign key (PodcastId) references podcastdetails(id)on update cascade ,position int not null,mediaType char(40),playlistId int,constraint fk_pm foreign key (playlistid) references playlist(playlistid)on update cascade)auto_increment=10001;

create table artist(Artist_Id int primary key auto_increment,name char(20) not null)auto_increment=1001;

create table podcast(podcast_id int primary key auto_increment,name char(40) not null,artist_id int,constraint fk_p foreign key (artist_id) references artist(artist_id)on update cascade)auto_increment=2001;

create table podcastdetails(id int primary key auto_increment,episodeName char(30),Releasedate date,podcastUrl char(200) not null,podcast_Id int,constraint fk_pd foreign key (podcast_id) references podcast(podcast_id)on update cascade)auto_increment=2101;

create table song(song_id int primary key auto_increment,songname char(40),genre char(30),album char(40),songUrl char(200) not null,songDuration time,artist_id int,constraint fk_s foreign key (artist_id) references artist(artist_id)on update cascade)auto_increment=4001;

insert into song(songname,genre, album,songUrl,artist_id) values('Aanewala pal janewala hai','Vintage','Golmaal','C:\\NIIT Course\\Eclipse\\database\\Week7 Jukebox_new\\resources\\Aanewala Pal Janewala Hai.wav',1006),
('Believer','Rock','Evolve','C:\\NIIT Course\\Eclipse\\database\\Week7 Jukebox_new\\resources\\Believer.wav',1007),
('Badtameez Dil','Party','Yeh jawaani hai deewani','C:\\NIIT Course\\Eclipse\\database\\Week7 Jukebox_new\\resources\\Badtameez Dil.wav',1008),
('Pal pal dil ke paas','Vintage','Blackmail','C:\\NIIT Course\\Eclipse\\database\\Week7 Jukebox_new\\resources\\Pal Pal Dil Ke Paas Tum Rehti Ho.wav',1006),
('Galway girl','Romantic','Divide','C:\\NIIT Course\\Eclipse\\database\\Week7 Jukebox_new\\resources\\Galway Girl.wav',1009),
('Shape of you','Romantic','Divide','C:\\NIIT Course\\Eclipse\\database\\Week7 Jukebox_new\\resources\\Shape Of You.wav',1009),
('Maay bhawaani','Devotional','Tanhaji-The unsung warrior','C:\\NIIT Course\\Eclipse\\database\\Week7 Jukebox_new\\resources\\Maay Bhavani.wav',1010),
('Kala chashma','Party','Baar baar dekho','C:\\NIIT Course\\Eclipse\\database\\Week7 Jukebox_new\\resources\\Kala Chashma.wav',1011),
('Khwaja mere khwaja','Sufi','Jodha Akbar','C:\\NIIT Course\\Eclipse\\database\\Week7 Jukebox_new\\resources\\Khwaja Mere Khwaja.wav',1012),
('Kun faya kun','Sufi','Rockstar','C:\\NIIT Course\\Eclipse\\database\\Week7 Jukebox_new\\resources\\Kun Faya Kun.wav',1012),
('Naam gum jayega','Vintage','Kinara','C:\\NIIT Course\\Eclipse\\database\\Week7 Jukebox_new\\resources\\Naam Gum Jayega.wav',1004),
('Namo namo shankara','Devotional','Kedarnath','C:\\NIIT Course\\Eclipse\\database\\Week7 Jukebox_new\\resources\\Namo Namo.wav',1013),
('Perfect','Romantic','Divide','C:\\NIIT Course\\Eclipse\\database\\Week7 Jukebox_new\\resources\\Perfect.wav',1009),
('Rock on','Rock','Rock on','C:\\NIIT Course\\Eclipse\\database\\Week7 Jukebox_new\\resources\\Rock On.wav',1014),
('Roobaroo','Pop','Rang de basanti','C:\\NIIT Course\\Eclipse\\database\\Week7 Jukebox_new\\resources\\Roobaroo.wav',1012),
('Zinda hai toh','Rock','Bhag Milkha Bhag','C:\\NIIT Course\\Eclipse\\database\\Week7 Jukebox_new\\resources\\Zinda.wav',1016),
('Dil dhadakne do','Party','Zindagi na milegi dobaara','C:\\NIIT Course\\Eclipse\\database\\Week7 Jukebox_new\\resources\\Dil Dhadakne Do.wav',1017),
('Senorita','Party','Zindagi na milegi dobaara','C:\\NIIT Course\\Eclipse\\database\\Week7 Jukebox_new\\resources\\Senorita.wav',1014)


select * from podcast;
insert into podcast(name,artist_id) values('The cricket podcast',1015),('Sandeep Maheshwaris Podcast',1005);


select * from podcastdetails;
insert into podcastdetails(episodeName,Releasedate,podcastUrl,podcast_Id) values('IPL Mega Auction Preview','2022-02-10','C:\\NIIT Course\\Eclipse\\database\\Week7 Jukebox_new\\resources\\IPL Mega Auction Preview .wav',2006),
('Cricket News! U19 Cup Final','2022-02-11','C:\\NIIT Course\\Eclipse\\database\\Week7 Jukebox_new\\resources\\Cricket News! U19 World Cup Final.wav',2006),
('IPL Mega Auction Review','2022-02-14','C:\\NIIT Course\\Eclipse\\database\\Week7 Jukebox_new\\resources\\IPL Mega-Auction Reaction.wav',2006),
('Psychology of Self Motivation','2021-11-02','C:\\NIIT Course\\Eclipse\\database\\Week7 Jukebox_new\\resources\\The Psychology of Self Motivation.wav',2007),
('How To Start Your Own Business','2021-12-15','C:\\NIIT Course\\Eclipse\\database\\Week7 Jukebox_new\\resources\\How To Start Your Own Business.wav',2007),
('PHILOSOPHY OF LIFE','2022-02-10','C:\\NIIT Course\\Eclipse\\database\\Week7 Jukebox_new\\resources\\PHILOSOPHY OF LIFE.wav',2007);




select * from artist
insert into artist(name) values('Lata Mangeshkar'),('Sandeep Maheshwari'),('Kishore Kumar'),('Imagine Dragons'),('Benny Dayal'),('Ed Sheeran'),
('Sukhwinder Singh'),('Amar Arshi'),('A.R.Rahman'),('Amit Trivedi'),('Farhan Akhtar'),('Jack Hope'),('Siddharth Mahadevan'),('Shankar Mahadevan')


select * from song where genre='remix';
select * from song s join artist a on s.artist_id=a.artist_id where name like 'a%';
select * from song where album like 'a%';
select * from podcast p join podcastdetails pd on p.podcast_id=pd.podcast_id where releasedate='2021-06-01';
select * from playlist p join user u on p.user_id=u.user_id where u.name like 'user1';
select * from podcastDetails pd join podcast p on p.podcast_id=pd.podcast_id where p.name='podcastA';
insert into playlistMapping(songid,podcastid,position,mediatype,playlistId) values(4001,null,3,'song',8003);

