package org.test;

public class PlaylistMapping {

	int id;
	int songId;
	int podcastId;
	int position;
	String mediaType;
	Playlist playlist;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSongId() {
		return songId;
	}
	public void setSongId(int songId) {
		this.songId = songId;
	}
	public int getPodcastId() {
		return podcastId;
	}
	public void setPodcastId(int podcastId) {
		this.podcastId = podcastId;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getMediaType() {
		return mediaType;
	}
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	public Playlist getPlaylist() {
		return playlist;
	}
	public void setPlaylist(Playlist playlist) {
		this.playlist = playlist;
	}
	public PlaylistMapping(int songId, int podcastId, int position, String mediaType, Playlist playlist) {
		super();
		this.songId = songId;
		this.podcastId = podcastId;
		this.position = position;
		this.mediaType = mediaType;
		this.playlist = playlist;
	}
	
	
}
