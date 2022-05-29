package org.test;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SimpleAudioPlayer {

	Long currentFrame;
	Clip clip;
	String status;
	AudioInputStream audioInputStream;
	//static String filepath;
	
	public SimpleAudioPlayer(String filepath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		
		audioInputStream = AudioSystem.getAudioInputStream(new File(filepath).getAbsoluteFile());
		clip=AudioSystem.getClip();
		clip.open(audioInputStream);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	
}
