package org.test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

public class SearchTest {
	
	@Test
	public void testSearchByGenre() throws SQLException {
		SongDAO songDAO = new SongDAO(DbConfigTest.getTestConnection());
		List<Song> remixSongs = songDAO.getSongsBasedOnGenre("remix");
		Assert.assertEquals(2, remixSongs.size());
		List<String> expectedSongNames = Arrays.asList("song1","song2");
		List<String> actualSongNames = remixSongs.stream().map(song->song.getSongName()).collect(Collectors.toList());
		Assert.assertEquals(expectedSongNames, actualSongNames);
		
	}

	@Test
	public void testSearchByNonPresentGenre() throws SQLException {
		SongDAO songDAO = new SongDAO(DbConfigTest.getTestConnection());
		List<Song> remixSongs = songDAO.getSongsBasedOnGenre("missing_genre");
		Assert.assertEquals(0, remixSongs.size());
	}
	
	@Test
	public void testSearchByArtist() throws SQLException {
		SongDAO songDAO = new SongDAO(DbConfigTest.getTestConnection());
		List<Song> artistSongs = songDAO.getSongsBasedOnArtist("artist1");
		Assert.assertEquals(2, artistSongs.size());
		List<String> expectedSongNames = Arrays.asList("song1","song2");
		List<String> actualSongNames = artistSongs.stream().map(song->song.getSongName()).collect(Collectors.toList());
		Assert.assertEquals(expectedSongNames, actualSongNames);
		
	}
	
	@Test
	public void testSearchByNonPresentArtist() throws SQLException {
		SongDAO songDAO = new SongDAO(DbConfigTest.getTestConnection());
		List<Song> artistSongs = songDAO.getSongsBasedOnArtist("missing_artist");
		Assert.assertEquals(0, artistSongs.size());
	}
	
	@Test
	public void testSearchByAlbum() throws SQLException {
		SongDAO songDAO = new SongDAO(DbConfigTest.getTestConnection());
		List<Song> albumSongs = songDAO.getSongsBasedOnAlbum("album1");
		Assert.assertEquals(2, albumSongs.size());
		List<String> expectedSongNames = Arrays.asList("song1","song2");
		List<String> actualSongNames = albumSongs.stream().map(song->song.getSongName()).collect(Collectors.toList());
		Assert.assertEquals(expectedSongNames, actualSongNames);
		
	}

	@Test
	public void testSearchByNonPresentAlbum() throws SQLException {
		SongDAO songDAO = new SongDAO(DbConfigTest.getTestConnection());
		List<Song> albumSongs = songDAO.getSongsBasedOnArtist("missing_album");
		Assert.assertEquals(0, albumSongs.size());
	}
	
	@Test
	public void testSearchByPodcastArtist() throws SQLException {
		PodcastDAO podDAO = new PodcastDAO(DbConfigTest.getTestConnection());
		List<PodcastDetails> artistPodcast = podDAO.getPodcastsBasedOnArtist("artist1");
		Assert.assertEquals(3, artistPodcast.size());
		List<String> expectedPodcastNames = Arrays.asList("ep1","ep2","ep3");
		List<String> actualPodcastNames = artistPodcast.stream().map(podcast->podcast.getEpisodeName()).sorted().collect(Collectors.toList());
		Assert.assertEquals(expectedPodcastNames, actualPodcastNames);
		
	}
	
	@Test
	public void testSearchByPodcastDate() throws SQLException {
		PodcastDAO podDAO = new PodcastDAO(DbConfigTest.getTestConnection());
		List<PodcastDetails> datePodcast = podDAO.getPodcastBasedOnReleaseDate("2021-06-01");
		Assert.assertEquals(2, datePodcast.size());
		List<String> expectedPodcastNames = Arrays.asList("ep1","ep3");
		List<String> actualPodcastNames =datePodcast.stream().map(podcast->podcast.getEpisodeName()).sorted().collect(Collectors.toList());
		Assert.assertEquals(expectedPodcastNames, actualPodcastNames);
		
	}
	
	
	
}
