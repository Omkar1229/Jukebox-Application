package org.test;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class JukeboxMain {

	public static void main(String[] args) throws SQLException, UnsupportedAudioFileException, IOException, LineUnavailableException {
		
		Scanner sc=new Scanner(System.in);

		Connection con = DbConfig.getConnection();
		try {
			SongDAO songDao = new SongDAO(con);
			PodcastDAO podcastD = new PodcastDAO(con);
			PlaylistDao playlistD = new PlaylistDao();
			UserDAO userD = new UserDAO();
			User user = null;
			Playlist playlist = null;
			PlayAudio playAudio = new PlayAudio();
			List<AudioDetails> list = new ArrayList<AudioDetails>();

			songDao.displaySong();
			podcastD.displayPodcast();

			int ch = -1;
			while (ch != 0) {
				System.out.println("Enter choice:\n1 - To listen the songs\n2 - To listen podcast\n3 - Create playlist/access playlist\n0 - Exit the jukebox");
				ch = sc.nextInt();
				switch (ch) {
					case 1:
						int sortSongch = -1;
						while (sortSongch != 0) {
							System.out.println("View songs by: \n1 - Genre\n2 - Album\n3 - Artist\n0 - Stop)");
							sortSongch = sc.nextInt();
							switch (sortSongch) {
								case 1:
									System.out.println("Enter genre:");
									sc.nextLine();
									String genre = sc.nextLine();
									if (songDao.displaySongsBasedOnGenre(genre)) {
										System.out.println("Do you want to play songs by genre: (yes/no)");
										String gPlay = sc.next();
										if (gPlay.equalsIgnoreCase("yes")) {
											String query = "select song_id,songname,songurl from song where genre=?";
											list = playlistD.playListToList(query, genre);
											playAudio.playAudio(list, 0);
										}
									}
									break;

								case 2:
									System.out.println("Enter album:");
									sc.nextLine();
									String album = sc.nextLine();
									if (songDao.displaySongsBasedInAlbum(album)) {
										System.out.println("Do you want to play songs by album: (yes/no)");
										String alPlay = sc.next();
										if (alPlay.equalsIgnoreCase("yes")) {
											String query = "select song_id,songname,songurl from song where songurl=?";
											list = playlistD.playListToList(query, album);
											playAudio.playAudio(list, 0);
										}
									}
									break;

								case 3:
									System.out.println("Enter artist:");
									sc.nextLine();
									String artist = sc.nextLine();
									if (songDao.displaySongsBasedOnArtist(artist)) {
										System.out.println("Do you want to play songs of this artists: (yes/no)");
										String aPlay = sc.next();
										if (aPlay.equalsIgnoreCase("yes")) {
											String query = "select song_id,songname,songurl from song s join artist a on s.artist_id =a.artist_id where artist=?";
											list = playlistD.playListToList(query, artist);
											playAudio.playAudio(list, 0);
										}
									}
									break;

								case 0:
									sortSongch = 0;
									break;

								default:
									System.out.println("Invalid choice,enter correct number");
									break;
							}
						}
						break;

					case 2:
						int sortPodcast = -1;
						while (sortPodcast != 0) {
							System.out.println("View podcasts by: 1 - Release Date\n2 - Artist\n0 - To Stop)");
							sortPodcast = sc.nextInt();
							switch (sortPodcast) {
								case 1:
									System.out.println("Enter release date:");
									sc.nextLine();
									String relDate = sc.nextLine();
									if (podcastD.displayPodcastBasedOnReleaseDate(relDate)) {
										System.out.println("Do you want to play podcast of this artists: (yes/no)");
										String dPlay = sc.next();
										if (dPlay.equalsIgnoreCase("yes")) {
											String query = "select pd.id,pd.episodename,pd.podcasturl from podcast p join podcastdetails pd on pd.podcast_id=p.podcast_id left join artist a on p.artist_id=a.artist_id where pd.releasedate=?";
											list = playlistD.playListToList(query, relDate);
											playAudio.playAudio(list, 0);
										}
									}
									break;

								case 2:
									System.out.println("Enter artist name:");
									sc.nextLine();
									String pArtist = sc.nextLine();
									if (podcastD.displayPodcastsBasedOnArtist(pArtist)) {
										System.out.println("Do you want to play podcast of this artists: (yes/no)");
										String aPlay = sc.next();
										if (aPlay.equalsIgnoreCase("yes")) {
											String query = "select pd.id,pd.episodename,pd.podcasturl from podcast p join podcastdetails pd on pd.podcast_id=p.podcast_id left join artist a on p.artist_id=a.artist_id where a.name=?";
											list = playlistD.playListToList(query, pArtist);
											playAudio.playAudio(list, 0);
										}
									}
									break;

								case 0:
									sortPodcast = 0;
									break;

								default:
									System.out.println("Invalid choice,enter correct number");
									break;
							}
						}
						break;

					case 3:
						System.out.println("Choose user: \n1 - Existing user\n2 - New user)");
						int userChoice = sc.nextInt();

						switch (userChoice) {
							case 1:
								if (userD.displayUser()) {
									System.out.println("Choose user by user id:");
									int userId = sc.nextInt();
									user = userD.currentUser(userId);
									System.out.println("User's playlist: ");
									playlistD.displayPlaylist(user);
									System.out.println("Enter the name of playlist you want to access:");
									String playlistName = sc.next();
									playlist = playlistD.displaySongsInPlaylist(playlistName, user.getUserName());
								}
								break;

							case 2:
								System.out.println("Enter user name: ");
								String userName = sc.next();
								User u2 = new User(userName);
								user = userD.newUser(u2);
								System.out.println("Enter new playlist name: ");
								String plName = sc.next();
								playlist = playlistD.createPlaylist(plName, u2);
								break;

							default:
								System.out.println("Invalid choice,no user with above user id");
								break;
						}

						if ((userChoice == 1 && userD.displayUser()) || userChoice == 2) {
							int choice = -1;
							while (choice != 0) {
								System.out.println("Enter operation to perform: \n(1.Add audio in playlist\n2.Delete audio from the playlist\n3.Play audio)");
								choice = sc.nextInt();
								switch (choice) {
									case 1:
										System.out.println("Add song in the playlist: (Enter songId)");
										int songId = sc.nextInt();
										playlistD.addSongInPlaylist(playlist.getPlaylistId(), songId);
										break;

									case 2:
										System.out.println("Delete song from the playlist: (Enter songId)");
										int dSong = sc.nextInt();
										playlistD.deleteSongFromPlaylist(playlist.getPlaylistId(), dSong);
										break;

									case 3:
										System.out.println("Enter playlist name to play: ");
										String plalist = sc.next();
										String query = "select s.song_id,s.songname,s.songurl,pd.id,pd.episodename,pd.podcasturl from playlistMapping pm left join playlist p on pm.playlistid=p.playlistid left join song s on pm.songid=s.song_id left join podcastdetails pd on pm.podcastid=pd.id where p.name like ?";
										list = playlistD.playListToList(query, plalist);
										System.out.println("Enter position of song to play first: ");
										int first = sc.nextInt();
										playAudio.playAudio(list, first);
										break;

									case 4:
										choice = 0;
										break;

									default:
										System.out.println("Invalid choice,enter correct number");
										break;
								}
							}
						}
						break;

					default:
						System.out.println("Invalid choice,enter correct number");
						break;

				}

			}
			sc.close();
		}
		finally {
			if(!con.isClosed()){
				con.close();
			}
		}
	}

}

		
		
//		
//		User u=new User("John");
//		
//		s.displaySong();
//		s.displaySongsBasedOnGenre("rock");
//		s.displaySongsBasedInAlbum("album1");
//		s.displaySongsBasedOnArtist("artist3");
//		
//		p.displayPodcast();
//		p.displayPodcastsBasedOnArtist("artist1");
//		p.displayPodcastBasedOnReleaseDate("2021-06-01");
//		
//		u1.newUser(u);
//		//pl.createPlaylist("english", u);
//		pl.displaySongsInPlaylist("adventure");
//		
//		pl.displayPlaylist(u);
		
		
		
		
//		int choice=-1;
//		while(choice!=0) {
//			System.out.println("Enter operation to perform: \n(1.Add song in playlist\n2.Delete song in playlist\n3.Play song)");
//			choice=sc.nextInt();
//		switch(choice)
//		{
//			case 1:
//				System.out.println("Add song in the playlist: (Enter songId)");
//				int songId=sc.nextInt();
//				pl.addSongInPlaylist(p1.getPlaylistId(),songId);
//				break;
//				
//			case 2:
//				System.out.println("Delete song from the playlist: (Enter songId)");
//				int dSong=sc.nextInt();
//				pl.deleteSongFromPlaylist(p1.getPlaylistId(), dSong);
//				break;
//				
//			case 3:
//				//p3.playPlaylist(p1.getPlaylistId());
//				break;
//				
//			case 4:
//				choice=0;
//				break;
//				
//		}
//		}
//		
	