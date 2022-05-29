package org.test;

public class Song {

	int songId;
	String songName;
	String genre;
	String album;
	String songUrl;
	Artist artist;
	public int getSongId() {
		return songId;
	}
	public void setSongId(int songId) {
		this.songId = songId;
	}
	public String getSongName() {
		return songName;
	}
	public void setSongName(String songName) {
		this.songName = songName;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public String getSongUrl() {
		return songUrl;
	}
	public void setSongUrl(String songUrl) {
		this.songUrl = songUrl;
	}
	public Artist getArtist() {
		return artist;
	}
	public void setArtist(Artist artist) {
		this.artist = artist;
	}
	public Song(int songId,String songName, String genre, String album, String songUrl, Artist artist) {
		super();
		this.songId = songId;
		this.songName = songName;
		this.genre = genre;
		this.album = album;
		this.songUrl = songUrl;
		this.artist = artist;
	}
	
	
}
