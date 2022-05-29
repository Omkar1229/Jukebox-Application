package org.test;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDao {

	Connection con=null;
	
	public Playlist displayPlaylist(User user) throws SQLException
	{
		Playlist p=null;
		con=DbConfig.getConnection();
		PreparedStatement ps=con.prepareStatement("select p.name,u.name,p.playlistId from playlist p left join user u on p.user_id=u.user_id  where u.name like ?");
		ps.setString(1, user.getUserName());
		ResultSet r=ps.executeQuery();
		try {
			if(r.next())
			{
				throw new EmptyResultsetException("There is no playlist of this user");
			}
			else {
				System.out.println("====================================================");
				System.out.format("%15s %15s\n","Playlists","User name","Playlist Id");
				System.out.println("====================================================");
				while(r.next())
				{
					System.out.format("%15s %15s %15s\n",r.getString(1),r.getString(2),r.getInt(3));
				}
				System.out.println("====================================================");
				
				PreparedStatement p1=con.prepareStatement("select p.name,u.name,p.playlistId from playlist p left join user u on p.user_id=u.user_id  where u.name like ?");
				p1.setString(1, user.getUserName());
				ResultSet r1=p1.executeQuery();
				r1.next();
				p=new Playlist(r1.getString(1),new User(r1.getString(2)));
				p.setPlaylistId(r1.getInt(3));
				return p;
			}
		}
		catch(EmptyResultsetException e)
		{
			e.printStackTrace();
		}
		return p;
	}
	
	public Playlist createPlaylist(String playlistName,User user) throws SQLException
	{
		con=DbConfig.getConnection();
		PreparedStatement ps=con.prepareStatement("insert into playlist(name,user_id) values (?,?)");
		ps.setString(1, playlistName);
		ps.setInt(2,user.getUserId());
		ps.execute();
		ps.close();
		con.close();
		PreparedStatement p1=con.prepareStatement("select p.name,u.name,p.playlistId from playlist p left join user u on p.user_id=u.user_id  where u.name like ? and p.name=?");
		p1.setString(1, user.getUserName());
		p1.setString(2, playlistName);
		ResultSet r1=p1.executeQuery();
		r1.next();
		Playlist p=new Playlist(r1.getString(1),new User(r1.getString(2)));
		p.setPlaylistId(r1.getInt(3));
		return p;
		
	}
	
	public Playlist displaySongsInPlaylist(String playlistName,String userName) throws SQLException
	{
		System.out.println("Songs in the playlist:");
		con=DbConfig.getConnection();
		PreparedStatement ps=con.prepareStatement("select pm.songid,s.songname,s.genre,s.album,a.name,pm.position from playlistMapping pm left join playlist pl on pm.playlistid=pl.playlistid left join user u on pl.user_id=u.user_id left join song s on pm.songid=s.song_id left join artist a on s.artist_id=a.artist_id where pl.name like ? and mediatype like 'song'");
		ps.setString(1, playlistName);
		ResultSet rs=ps.executeQuery();
		System.out.println("==============================================================================================");
		System.out.format("%15s %15s %15s %15s %15s %15s","Song Id","Song name","Genre","Album name","Artist","Position");
		System.out.println("\n============================================================================================");
		while(rs.next())
		{
			System.out.format("%15s %15s %15s %15s %15s %15s\n",rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6));
		}
		System.out.println("==============================================================================================");	
		
		PreparedStatement p1=con.prepareStatement("select pm.podcastid,p.name,pd.episodename,pd.releasedate,a.name,pm.position from playlistMapping pm left join playlist pl on pm.playlistid=pl.playlistid left join user u on pl.user_id=u.user_id left join podcastdetails pd on pm.podcastid=pd.id left join podcast p on pd.podcast_id=p.podcast_id left join artist a on p.artist_id=a.artist_id where pl.name like ? and mediatype like 'podcast'");
		p1.setString(1, playlistName);
		ResultSet r1=p1.executeQuery();
		System.out.println("==========================================================================================================");
		System.out.format("%15s %15s %15s %15s %15s %15s","Podcast Id","Podcast name","Episode name","Release Date","Artist","Position");
		System.out.println("\n========================================================================================================");
		while(r1.next())
		{
			System.out.format("%15s %15s %15s %15s %15s %15s\n",r1.getInt(1),r1.getString(2),r1.getString(3),r1.getDate(4),r1.getString(5),r1.getInt(6));
		}
		System.out.println("==========================================================================================================");	
		
		
		rs.close();
		ps.close();
		PreparedStatement p2=con.prepareStatement("select name,playlistid from playlist where name like ?");
		p2.setString(1, playlistName);
		ResultSet r2=p2.executeQuery();
		r2.next();
		Playlist p=new Playlist(r2.getString(1),new User(userName));
		p.setPlaylistId(r2.getInt(2));
		
		con.close();
		return p;
	}
	
	public void addSongInPlaylist(int playlistId,int songId) throws SQLException
	{
		con=DbConfig.getConnection();
		PreparedStatement p=con.prepareStatement("select count(id)+1 from playlistMapping  where playlistid=?");
		p.setInt(1, playlistId);
		ResultSet r=p.executeQuery();
		r.next();
		int position=r.getInt(1);
		PreparedStatement ps = null;
		if(String.valueOf(songId).startsWith("4"))
		{
			ps=con.prepareStatement("insert into playlistMapping(songid,position,mediatype,playlistId) values(?,?,'song',?)");
		}
		else 
		{
			ps=con.prepareStatement("insert into playlistMapping(podcastid,position,mediatype,playlistId) values(?,?,'podcast',?)");
		}
		ps.setInt(1, songId);
		ps.setInt(2, position);
		ps.setInt(3, playlistId);
		ps.execute();
		System.out.println("Song added");
		ps.close();
		con.close();

	}
	
	public void deleteSongFromPlaylist(int playlistId,int songId) throws SQLException
	{
		con=DbConfig.getConnection();
		PreparedStatement p;
		if(String.valueOf(songId).startsWith("4"))
		{
			p=con.prepareStatement("select position from playlistMapping where songid=?");
		}
		else
		{
			p=con.prepareStatement("select position from playlistMapping where podcastid=?");
		}
		p.setInt(1, songId);
		ResultSet r=p.executeQuery();
		r.next();
		int position=r.getInt(1);
		
		PreparedStatement p1=null;
		if(String.valueOf(songId).startsWith("4"))
		{
			p1=con.prepareStatement("delete from playlistMapping where songid=?");
		}
		else
		{
			p1=con.prepareStatement("delete from playlistMapping where podcastid=?");
		}
		p1.setInt(1, songId);
		p1.execute();
		
		PreparedStatement p2=con.prepareStatement("update playlistMapping set position=position-1 where position > ?");
		p2.setInt(1, position);
		p2.execute();
	}

	
	public List<AudioDetails> playListToList(String query,String plList) throws SQLException
	{
		List<AudioDetails> playlist=new ArrayList<AudioDetails>();
		con=DbConfig.getConnection();
		PreparedStatement p=con.prepareStatement(query);
		p.setString(1, plList);
		ResultSet r=p.executeQuery();
		while(r.next())
		{
			int id=r.getInt(1);
			if(id==0)
			{
				playlist.add(new AudioDetails(r.getInt(4),r.getString(5),r.getString(6)));
			}
			else { 
				playlist.add(new AudioDetails(r.getInt(1),r.getString(2),r.getString(3)));
			}	
		}
		
		return playlist;
	}
}
