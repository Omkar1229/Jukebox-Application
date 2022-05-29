package org.test;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SongDAO {

	private Connection con=null;
	
	
	
	public SongDAO(Connection con) {
		super();
		this.con = con;
	}

	public void displaySong() throws SQLException
	{
		System.out.println("All the songs in the jukebox: ");
		//con=DbConfig.getConnection();
		Statement s=con.createStatement();
		ResultSet rs=s.executeQuery("select song_id,songname,genre,album,a.name from song s join artist a on s.artist_id=a.artist_id order by songname");
		System.out.println("=================================================================================================================================");
		System.out.format("%15s %30s %15s %30s %25s\n","Song Id","Song name","Genre","Album Name","Singer");
		System.out.println("=================================================================================================================================");
		while(rs.next())
		{
			System.out.format("%15s %30s %15s %30s %25s\n",rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
		}
		System.out.println("=================================================================================================================================");
		rs.close();
		
	}
	
	public boolean displaySongsBasedOnGenre(String genre) throws SQLException
	{
	
		List<Song> list = getSongsBasedOnGenre(genre);
		if(list.size()==0)
		{
			return false;
		}
		else {
			printSongs(list);
			return true;
		}
	}
	
	public List<Song> getSongsBasedOnGenre(String genre) throws SQLException
	{
		List<Song> songList=new ArrayList<Song>();
//		con=DbConfig.getConnection();
		PreparedStatement ps=con.prepareStatement("select song_id,songname,genre,album,songurl,a.artist_id,a.name from song s join artist a on s.artist_id=a.artist_id where genre like ? order by songname");
		ps.setString(1,genre);
		ResultSet rs=ps.executeQuery();
		try {
			if(!rs.isBeforeFirst())
			{
				throw new EmptyResultsetException("No songs of the given genre.");
			}
			else{
				while(rs.next())
				{
					songList.add(new Song(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),new Artist(rs.getInt(6),rs.getString(7))));
				}
				rs.close();
				
				return songList;
			}
		}
		catch(EmptyResultsetException e)
		{
			e.printStackTrace();
		}
		return songList;
	}

	public boolean displaySongsBasedInAlbum(String albumName) throws SQLException
	{
		List<Song> list = getSongsBasedOnAlbum(albumName);
		if(list.size()==0)
		{
			return false;
		}
		else {
			printSongs(list);
			return true;
		}
	}
	
	public List<Song> getSongsBasedOnAlbum(String albumName) throws SQLException
	{
		List<Song> songList=new ArrayList<Song>();
//		con=DbConfig.getConnection();
		PreparedStatement ps=con.prepareStatement("select song_id,songname,genre,album,songurl,a.artist_id,a.name from song s join artist a on s.artist_id=a.artist_id where album like ? order by songname");
		ps.setString(1,albumName);
		ResultSet rs=ps.executeQuery();
		try {
			if(!rs.isBeforeFirst())
			{
				throw new EmptyResultsetException("No songs of the given album are available.");
			}
			else{
				while(rs.next())
				{
					songList.add(new Song(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),new Artist(rs.getInt(6),rs.getString(7))));
				}
				rs.close();
				
				return songList;
			}
		}
		catch(EmptyResultsetException e)
		{
			e.printStackTrace();
		}
		return songList;
	}
	
	public boolean displaySongsBasedOnArtist(String artist) throws SQLException
	{
		List<Song> list = getSongsBasedOnArtist(artist);
		if(list.size()==0)
		{
			return false;
		}
		else {
			printSongs(list);
			return true;
		}
	}
	
	public List<Song> getSongsBasedOnArtist(String artist) throws SQLException
	{
		List<Song> songList=new ArrayList<Song>();
//		con=DbConfig.getConnection();
		PreparedStatement ps=con.prepareStatement("select song_id,songname,genre,album,songurl,a.artist_id,a.name from song s join artist a on s.artist_id=a.artist_id where a.name like ? order by songname");
		ps.setString(1,artist);
		ResultSet rs=ps.executeQuery();
		try {
			if(!rs.isBeforeFirst())
			{
				throw new EmptyResultsetException("No songs of this artist is available");
			}
			else {
				while(rs.next())
				{
					songList.add(new Song(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),new Artist(rs.getInt(6),rs.getString(7))));
				}
				rs.close();
				
				return songList;
			}
		}
		catch(EmptyResultsetException e)
		{
			e.printStackTrace();
		}
		return songList;
	}
	
	private void printSongs(List<Song> songList) throws SQLException {
		System.out.println("=================================================================================================================================");
		System.out.format("%15s %25s %15s %25s %25s\n","Song Id","Song name","Genre","Album Name","Singer");
		System.out.println("=================================================================================================================================");
		for(Song s:songList)
		{
			System.out.format("%15s %25s %15s %25s %25s\n",s.getSongId(),s.getSongName(),s.getGenre(),s.getAlbum(),s.getArtist().getArtistName());
		}
		System.out.println("=================================================================================================================================");
	}
	
	
}
