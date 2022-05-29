package org.test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PodcastDAO {

	Connection con=null;
	
	public PodcastDAO(Connection con) {
		super();
		this.con = con;
	}
	
	
	public void displayPodcast() throws SQLException
	{
		System.out.println("\nList of all the podcasts available in jukebox:");
		//con=DbConfig.getConnection();
		Statement s=con.createStatement();
		ResultSet rs=s.executeQuery("select p.name,pd.episodename,pd.id,pd.releasedate,a.name from podcast p join podcastdetails pd on pd.podcast_id=p.podcast_id join artist a on p.artist_id=a.artist_id order by p.name,pd.episodename");
		System.out.println("=======================================================================================================================================================================");
		System.out.format("%40s %40s %20s %25s %25s\n","Podcast name","Episode name","Episode Id","Release Date","Artist Name");
		System.out.println("=======================================================================================================================================================================");
		while(rs.next())
		{
			System.out.format("%40s %40s %20s %25s %25s\n",rs.getString(1),rs.getString(2),rs.getInt(3),rs.getDate(4),rs.getString(5));
		}
		System.out.println("=======================================================================================================================================================================");
		rs.close();
		
	}
	
	public boolean displayPodcastsBasedOnArtist(String artist) throws SQLException
	{
		List<PodcastDetails> list = getPodcastsBasedOnArtist(artist);
		if(list.size()==0)
		{
			return false;
		}
		else {
			printPodcast(list);
			return true;
		}
	}

	public List<PodcastDetails> getPodcastsBasedOnArtist(String artist) throws SQLException
	{
		List<PodcastDetails> podcastList=new ArrayList<>();
		//con=DbConfig.getConnection();
		PreparedStatement ps=con.prepareStatement("select id,p.podcast_id,p.name,episodename,releasedate,podcastUrl,a.Artist_id,a.name from artist a join podcast p on a.artist_id=p.artist_id join podcastdetails pd on p.podcast_id=pd.podcast_id where a.name=?");
		ps.setString(1,artist);
		ResultSet rs=ps.executeQuery();
		try {
			if(!rs.isBeforeFirst())
			{
				throw new EmptyResultsetException("No podcast available for artist name: "+artist);
			}
			else {
				while(rs.next())
				{
					podcastList.add(new PodcastDetails(rs.getInt(1),rs.getString(4),rs.getDate(5),rs.getString(6),new Podcast(rs.getInt(2),rs.getString(3),new Artist(rs.getInt(7),rs.getString(8)))));
				}
				rs.close();
				
				return podcastList;
			}
		}
		catch(EmptyResultsetException e)
		{
			e.printStackTrace();
		}
		return podcastList;
	}

	public boolean displayPodcastBasedOnReleaseDate(String releaseDate) throws SQLException
	{
		List<PodcastDetails> list = getPodcastBasedOnReleaseDate(releaseDate);
		if(list.size()==0)
		{
			return false;
		}
		else {
			printPodcast(list);
			return true;
		}
	}
	
	public List<PodcastDetails> getPodcastBasedOnReleaseDate(String releaseDate) throws SQLException
	{
		List<PodcastDetails> podcastList=new ArrayList<>();
		//con=DbConfig.getConnection();
		PreparedStatement ps=con.prepareStatement("select id,p.podcast_id,p.name,episodename,releasedate,podcastUrl,a.Artist_id,a.name from artist a join podcast p on a.artist_id=p.artist_id join podcastdetails pd on p.podcast_id=pd.podcast_id where pd.releasedate=?");
		ps.setString(1,releaseDate);
		ResultSet rs=ps.executeQuery();
		try {
			if(!rs.isBeforeFirst())
			{
				throw new EmptyResultsetException("No podcast released on "+releaseDate);
			}
			else {
				while(rs.next())
				{
					podcastList.add(new PodcastDetails(rs.getInt(1),rs.getString(4),rs.getDate(5),rs.getString(6),new Podcast(rs.getInt(2),rs.getString(3),new Artist(rs.getInt(7),rs.getString(8)))));
				}
				rs.close();
				
				return podcastList;
			}
		}
		catch(EmptyResultsetException e)
		{
			e.printStackTrace();
		}
		return podcastList;
		
	}
	
	
	private void printPodcast(List<PodcastDetails> list) throws SQLException {
		System.out.println("===============================================================================================================================================================");
		System.out.format("%25s %25s %30s %25s %25s\n","Episode Id","Podcast name","Episode name","Release Date","Artist");
		System.out.println("===============================================================================================================================================================");
		for(PodcastDetails pd:list)
		{
			System.out.format("%25s %25s %30s %25s %25s\n",pd.getPdId(),pd.getPodcast().getPodcastName(),pd.getEpisodeName(),pd.getReleaseDate(),pd.getPodcast().getArtist().getArtistName());
		}
		System.out.println("===============================================================================================================================================================");
	}
}
