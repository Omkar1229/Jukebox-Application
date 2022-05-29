package org.test;

public class Podcast {

	int podcastId;
	String podcastName;
	Artist artist;
	public int getPodcastId() {
		return podcastId;
	}
	public void setPodcastId(int podcastId) {
		this.podcastId = podcastId;
	}
	public String getPodcastName() {
		return podcastName;
	}
	public void setPodcastName(String podcastName) {
		this.podcastName = podcastName;
	}
	public Artist getArtist() {
		return artist;
	}
	public void setArtist(Artist artist) {
		this.artist = artist;
	}
	public Podcast(int podcastId,String podcastName, Artist artist) {
		super();
		this.podcastId=podcastId;
		this.podcastName = podcastName;
		this.artist = artist;
	}
	
	
}
