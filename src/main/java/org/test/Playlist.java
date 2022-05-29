package org.test;

public class Playlist {

	private int playlistId;
	private String playlistName;
	private User user;
	public int getPlaylistId() {
		return playlistId;
	}
	public void setPlaylistId(int playlistId) {
		this.playlistId = playlistId;
	}
	public String getPlaylistName() {
		return playlistName;
	}
	public void setPlaylistName(String playlistName) {
		this.playlistName = playlistName;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Playlist(String playlistName, User user) {
		super();
		this.playlistName = playlistName;
		this.user = user;
	}
	
	
}
