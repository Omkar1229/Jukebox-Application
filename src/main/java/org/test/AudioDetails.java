package org.test;

public class AudioDetails {

	int id;
	String title;
	String audioUrl;
	
	public AudioDetails(int id, String title, String audioUrl) {
		this.id = id;
		this.title = title;
		this.audioUrl = audioUrl;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAudioUrl() {
		return audioUrl;
	}
	public void setAudioUrl(String audioUrl) {
		this.audioUrl = audioUrl;
	}
	
	
	
}
