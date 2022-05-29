package org.test;

public class Artist {

	int artistId;
	String artistName;
	public int getArtistId() {
		return artistId;
	}
	public void setArtistId(int artistId) {
		this.artistId = artistId;
	}
	public String getArtistName() {
		return artistName;
	}
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	public Artist(int artistId,String artistName) {
		this.artistId=artistId;
		this.artistName = artistName;
	}
	
}
