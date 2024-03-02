package main;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	Clip clip;
	URL soundURL[] = new URL[10];
	public Sound() {
		try {
			soundURL[0] = new File("res/sounds/Main_sound.wav").toURI().toURL();
			soundURL[1] = new File("res/sounds/jump_sound.wav").toURI().toURL();
			soundURL[2] = new File("res/sounds/run_sound.wav").toURI().toURL();
			soundURL[3] = new File("res/sounds/click_sound.wav").toURI().toURL();
			soundURL[4] = new File("res/sounds/open_sound.wav").toURI().toURL();
			
		}catch(IOException e) {
			//TODO: handle exception
			e.printStackTrace();
		}
	}
	public void setFile(int i) {
		try {
			
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			
		}catch(Exception e) {
			
		}
	}
	public void play() {
		
		clip.start();
	}
	public void loop() {
		
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void stop() {
		
		clip.stop();
	}
}
