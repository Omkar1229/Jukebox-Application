package org.test;
import java.sql.Date;

public class PodcastDetails {

	int pdId;
	String episodeName;
	Date releaseDate;
	String podcastUrl;
	Podcast podcast;
	public int getPdId() {
		return pdId;
	}
	public void setPdId(int pdId) {
		this.pdId = pdId;
	}
	public String getEpisodeName() {
		return episodeName;
	}
	public void setEpisodeName(String episodeName) {
		this.episodeName = episodeName;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getPodcastUrl() {
		return podcastUrl;
	}
	public void setPodcastUrl(String podcastUrl) {
		this.podcastUrl = podcastUrl;
	}
	public Podcast getPodcast() {
		return podcast;
	}
	public void setPodcast(Podcast podcast) {
		this.podcast = podcast;
	}
	public PodcastDetails(int pdId,String episodeName, Date releaseDate, String podcastUrl, Podcast podcast) {
		this.pdId=pdId;
		this.episodeName = episodeName;
		this.releaseDate = releaseDate;
		this.podcastUrl = podcastUrl;
		this.podcast = podcast;
	}
	
	
}
