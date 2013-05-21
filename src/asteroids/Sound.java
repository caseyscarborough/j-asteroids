package asteroids;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Holds the locations of the sound files and handles playing of the sounds for the game.
 * @author Casey Scarborough
 * @see GameBoard
 * @see Asteroid
 */
public class Sound {
	public static final String thrust = "file:./resources/sounds/thrust.au";
	public static final String laser = "file:./resources/sounds/laser.wav";
	public static final String explosion = "file:./resources/sounds/explosion.aiff";
	
	/**
	 * Used to play sound.
	 * @param soundToPlay a String specifying the location of the sound file.
	 */
	public static void playSoundEffect(String soundToPlay) {
		URL soundLocation;
		try {
			soundLocation = new URL(soundToPlay);
			Clip clip = null;
			clip = AudioSystem.getClip();
			AudioInputStream inputStream;
			inputStream = AudioSystem.getAudioInputStream(soundLocation);
			clip.open(inputStream);
			clip.loop(0);
			clip.start();
			
			// Used to kill sound thread
			clip.addLineListener(new LineListener() {
				public void update (LineEvent evt) {
					if (evt.getType() == LineEvent.Type.STOP) {
						evt.getLine().close();
					}
				}
			});
			
		} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
			System.out.println(e.getMessage());
		}
	}

}
