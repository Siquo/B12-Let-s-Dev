package sound;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

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
	private MP3 currentClip;
	private AudioInputStream currentSong;
	private URL url;
    private Player player; 
    
    private Map<MusicType,MP3> songs;
    private Map<SoundEffect,MP3> effects;
    
	public AudioPlayer(URL u){
		super();
		url = u;
		songs = new HashMap<MusicType,MP3>();
		effects = new HashMap<SoundEffect,MP3>();
		songs.put(MusicType.MS_NORMAL, loadSong("SupahSkeery.mp3"));
		songs.put(MusicType.MS_COMBAT, loadSong("BulletHell.mp3"));
		try { 
			player = new Player((InputStream)songs.get(MusicType.MS_NORMAL));
        }
        catch (Exception e) { System.out.println(e); }		
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
		MP3 toPlay = songs.get(tp);
		if(toPlay != currentClip) {
			player.close();
        	try {player = new Player((InputStream)toPlay);}
        	catch (Exception e) { System.out.println(e); }
		    new Thread() {
			    public void run() {
			        try { 
			        	//player.play(); 
			        }
			        catch (Exception e) { System.out.println(e); }
			    }
			}.start();
		}
	}
	
	public void playSoundFX(SoundEffect tp){
		
	}

	
	private MP3 loadSong(String songName){
		MP3 tmp;
	    try {
	    	URL nurl = new URL(url, songName);
	        FileInputStream fis     = new FileInputStream(songName);
	        //BufferedInputStream bis = new BufferedInputStream MP3(fis);
	        MP3 mp3 = new MP3(fis);
	        //tmp = new Clip(fis);
	        //player = new Player(bis);
	        return mp3;
	    }
	    catch (Exception e) {
	        System.out.println("Problem playing file " + songName);
	        System.out.println(e);
	    }
	    return null;



	// run in new thread to play in background
	    /*new Thread() {
		    public void run() {
		        try { player.play(); }
		        catch (Exception e) { System.out.println(e); }
		    }
		}.start();*/
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
	private class MP3 extends BufferedInputStream {
		MP3(FileInputStream i){
			super(i);
		}
	}
}


