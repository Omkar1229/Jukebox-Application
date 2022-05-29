package org.test;
import java.io.File;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;



public class PlayAudio {
	
	SimpleAudioPlayer audioPlayer=null;
	String filepath="";
	Connection con=null;
//	private Integer currentplaylistId;
//	private int currentSongId;
	private int curPosition;
	public final long SEEKLENGTH=5000000;
	List<AudioDetails> list;
	Scanner sc=new Scanner(System.in);
	
//	public void playPlaylist(String playlist) throws SQLException, UnsupportedAudioFileException, IOException, LineUnavailableException
//	{
//		con=DbConfig.getConnection();
//		PreparedStatement p=con.prepareStatement("select mediatype,songid,podcastid,position from playlistMapping pm left join playlist p on pm.playlistid=p.playlistid where p.name like ? and position=1 ;");
//		p.setString(1, playlist);
//		ResultSet r=p.executeQuery();
//		r.next();
//		String medType=r.getString(1);
//		int id=0;
//		
//		if(medType.equalsIgnoreCase("song"))
//		{
//			id=r.getInt(2);
//			playAudio(id,1,null);
//		}
//		else
//		{
//			id=r.getInt(3);
//			playAudio(id,2,null);
//		}
//		currentPosition=r.getInt(4);
//	
//	}
	
	
	
//	public void playAudio() throws SQLException, UnsupportedAudioFileException, IOException, LineUnavailableException
//	{
//		System.out.println("Enter type of audio: (1.Song/2.Podcast)");
//		int choice=sc.nextInt();
//		System.out.println("Enter "+(choice==1?"song":"podcast")+" id to play: ");
//		int songId=sc.nextInt();
//		//playAudio(songId, choice, null);
//	}
	
	public void playAudio(List<AudioDetails> plist,int position) throws SQLException, UnsupportedAudioFileException, IOException, LineUnavailableException
	{
//		currentSongId=songId;
//		currentplaylistId=playlistId;
//		con=DbConfig.getConnection();
//		PreparedStatement ps=null;
//		if(mediatype==1)
//		{
//			ps=con.prepareStatement("select songurl from song where song_id=?");
//		}
//		else
//		{
//			ps=con.prepareStatement("select podcasturl from podcastDetails where id=?");
//		}
//		ps.setInt(1, currentSongId);
//		ResultSet r=ps.executeQuery();
//		r.next();
		//filepath=list.get(curPosition).getAudioUrl();
		curPosition=position;
		list=plist;
		filepath=list.get(curPosition).getAudioUrl();
						
		System.out.println("Currently playing : "+list.get(curPosition).getTitle());
		System.out.println("Next              : "+list.get(curPosition+1<list.size()?(curPosition+1):(curPosition-curPosition)).getTitle());
		System.out.println("Previous          : "+list.get(curPosition-1<0?(list.size()-1):(curPosition-1)).getTitle());
		
		audioPlayer=new SimpleAudioPlayer(list.get(curPosition).getAudioUrl());
		play();
		while(true)
		{
			System.out.println("1. PAUSE");
			System.out.println("2. RESUME");
			System.out.println("3. RESTART");
			System.out.println("4. STOP");
			System.out.println("5. FORWARD");
			System.out.println("6. REVERSE");
			System.out.println("7. NEXT");
			System.out.println("8. PREVIOUS");
			System.out.println("9. SHUFFLE");
			
			int c=sc.nextInt();
			gotoChoice(c);
			if(c==4)
			{
				break;
			}
		}
		
		
	}
	
	private void gotoChoice(int c) throws UnsupportedAudioFileException, IOException, LineUnavailableException, SQLException
	{
		switch(c)
		{
			case 1:
				pause();
				break;
				
			case 2:
				resumeAudio();
				break;
				
			case 3:
				restart();
				break;
				
			case 4:
				stop();
				break;
				
			case 5:
				forward();
				break;
				
			case 6:
				reverse();
				break;
				
			case 7:
				next(list, curPosition);
				break;
				
			case 8:
				previous(list, curPosition);
				break;
				
			case 9:
				shuffle(list);
				break;
				
		}
	}
	
	public void play()
	{
		audioPlayer.clip.start();
		audioPlayer.status="play";
	}
	
	public void pause()
	{
		if(audioPlayer.status.equals("paused"))
		{
			return;
		}
		audioPlayer.currentFrame=audioPlayer.clip.getMicrosecondPosition();
		audioPlayer.clip.stop();
		audioPlayer.status="paused";
		long total=audioPlayer.clip.getMicrosecondLength()-audioPlayer.clip.getMicrosecondPosition();
		long hours=total/3600000000L;
		long hourRemain=total%3600000000L;
		long min=hourRemain/60000000;
		long minRemain=hourRemain%60000000;
		long seconds=minRemain/1000000;
		System.out.println("Remaining duration: "+hours+"hh :"+min+"mm :"+seconds+"ss");
	}
	
	
	public void resumeAudio() throws UnsupportedAudioFileException, IOException, LineUnavailableException
	{
		if(audioPlayer.status.equals("play"))
		{
			System.out.println("Already playing the audio");
		}
		audioPlayer.clip.close();
		resetAudioStream();
		audioPlayer.clip.setMicrosecondPosition(audioPlayer.currentFrame);
		this.play();
		
	}
	
	public void restart() throws UnsupportedAudioFileException, IOException, LineUnavailableException
	{
		audioPlayer.clip.stop();
		audioPlayer.clip.close();
		resetAudioStream();
		audioPlayer.currentFrame=0L;
		audioPlayer.clip.setMicrosecondPosition(0);
		this.play();
	}
	
	public void stop()
	{
		audioPlayer.currentFrame=0L;
		audioPlayer.clip.stop();
		audioPlayer.clip.close();
	}
	
	public void forward()
	{
		audioPlayer.clip.stop();
		long currentPositon=audioPlayer.clip.getMicrosecondPosition();
		audioPlayer.clip.setMicrosecondPosition(currentPositon+SEEKLENGTH);
		this.play();
		
	}
	
	public void reverse()
	{
		audioPlayer.clip.stop();
		long currentPositon=audioPlayer.clip.getMicrosecondPosition();
		audioPlayer.clip.setMicrosecondPosition(currentPositon-SEEKLENGTH);
		this.play();
		
	}
	
	public void next(List<AudioDetails> list,int curPosition) throws SQLException, UnsupportedAudioFileException, IOException, LineUnavailableException
	{
		stop();
		if(curPosition+1==list.size())
		{
			playAudio(list, curPosition-curPosition);
		}
		else {
			playAudio(list, curPosition+1);
		}
	}
	
	public void previous(List<AudioDetails> list,int curPosition) throws SQLException, UnsupportedAudioFileException, IOException, LineUnavailableException
	{
		stop();
		if(curPosition==0)
		{
			playAudio(list, list.size()-1);
		}
		else {
			playAudio(list, curPosition-1);
		}
	}
	
	public void shuffle(List<AudioDetails> list) throws SQLException, UnsupportedAudioFileException, IOException, LineUnavailableException
	{
		stop();
		Collections.shuffle(list);
		playAudio(list, 1);
	}
	
	
	
	public void resetAudioStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException
	{
		AudioInputStream audioInputStream=AudioSystem.getAudioInputStream(new File(filepath).getAbsoluteFile());
		audioPlayer.clip.open(audioInputStream);
		audioPlayer.clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	
	
}



















//
//public void nextPlaylistSong() throws SQLException, UnsupportedAudioFileException, IOException, LineUnavailableException
//{
//	stop();
//	int id=0;
//	con=DbConfig.getConnection();
//	PreparedStatement p=con.prepareStatement("select songid,podcastid from podcast where position=?+1 and ");
//	p.setInt(1, currentPosition);
//	ResultSet r=p.executeQuery();
//	r.next();
//}
//
//public void nextSong() throws SQLException, UnsupportedAudioFileException, IOException, LineUnavailableException
//{
//	stop();
//	int id=0;
//	con=DbConfig.getConnection();
//	PreparedStatement p=con.prepareStatement("select song_id from song where song_id=?+1");
//	p.setInt(1, currentSongId);
//	ResultSet r=p.executeQuery();
//	if(!r.next())
//	{
//		PreparedStatement p1=con.prepareStatement("select song_id from song limit 1");
//		ResultSet r1=p1.executeQuery();
//		r1.next();
//		id=r1.getInt(1);
//		//playAudio(id, 1, null);
//	}
//	else {
//		id=r.getInt(1);
//		//playAudio(id, 1, null);
//	}
//}
//
//public void prevSong() throws SQLException, UnsupportedAudioFileException, IOException, LineUnavailableException
//{
//	stop();
//	int id=0;
//	con=DbConfig.getConnection();
//	PreparedStatement p=con.prepareStatement("select song_id from song where song_id=?-1");
//	p.setInt(1, currentSongId);
//	ResultSet r=p.executeQuery();
//	if(!r.next())
//	{
//		PreparedStatement p1=con.prepareStatement("select song_id from song order by song_id limit 1");
//		ResultSet r1=p1.executeQuery();
//		r1.next();
//		id=r1.getInt(1);
//		//playAudio(id, 1, null);
//	}
//	else {
//		id=r.getInt(1);
//		//playAudio(id, 1, null);
//	}
//}
