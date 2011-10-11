package sound;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javazoom.spi.mpeg.sampled.file.IcyListener;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;
import javazoom.spi.mpeg.sampled.file.tag.IcyInputStream;
import javazoom.jl.player.Player;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Control;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;


public class AudioPlayer {
//	private MpegAudioFileReaderWorkaround aReader;
	private Clip currentClip;
	private AudioInputStream currentSong;
	private URL url;
    private Player player; 
    
    
	public AudioPlayer(URL u){
		super();
		url = u;
		//aReader = new MpegAudioFileReaderWorkaround();
		
	}
	
	public enum MusicType {
		MS_NORMAL,
		MS_COMBAT
	}
	public enum SoundEffect {
		FX_BLASTERFIRE,
		FX_LASERSWORD
	}
	
	public void playMusic(MusicType tp){
		switch(tp){
			case MS_NORMAL:{
				loadSong("Supah Skeery v1.0.mp3");
			}
			case MS_COMBAT:{
				loadSong("Supah Skeery v1.0.mp3");
			}
		}
		if(currentClip != null){
			//currentClip.start();
			currentClip.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}
	
	public void playSoundFX(SoundEffect tp){
		
	}

	
	private void loadSong(String songName){
	    try {
	    	URL nurl = new URL(url, songName);
	        InputStream fis     = nurl.openStream();
	        BufferedInputStream bis = new BufferedInputStream(fis);
	        player = new Player(bis);
	    }
	    catch (Exception e) {
	        System.out.println("Problem playing file " + songName);
	        System.out.println(e);
	    }



	// run in new thread to play in background
	    new Thread() {
		    public void run() {
		        try { player.play(); }
		        catch (Exception e) { System.out.println(e); }
		    }
		}.start();
	}
/*	
	private void loadSong(String songName){
		try { 
			currentSong = aReader.getAudioInputStream(new URL(url.toString()+songName));
		} catch (UnsupportedAudioFileException e)
		{
			return;
		} catch (IOException e)
		{
			return;
		}

		AudioFormat format = currentSong.getFormat();
        DataLine.Info info = new DataLine.Info(
                Clip.class, 
                currentSong.getFormat(), 
                ((int) currentSong.getFrameLength() *
                    format.getFrameSize()));
        try{
        	Clip clip = AudioSystem.getClip();
    		//clip.addLineListener((LineListener) this);
    		clip.open(currentSong);
    		currentClip = clip;
        } catch (LineUnavailableException e) {
			return;
        } catch (IOException e)
		{
			return;
		}
	}
	
    public void run() {

    }

	@Override
	public void update(LineEvent arg0) {
		// TODO Auto-generated method stub
		
	}*/
}
