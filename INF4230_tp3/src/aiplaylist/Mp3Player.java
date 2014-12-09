package aiplaylist;

import java.io.FileInputStream;

import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

public class Mp3Player extends PlaybackListener implements Runnable {
	
	String libraryPath;
	FileInputStream fis;
	AdvancedPlayer playMP3;
	Thread playerThread;
	
	public Mp3Player(String libraryPath) {
		this.libraryPath = libraryPath;
	}
	
	
	public void play (String song) {
		
		try{

	        fis = new FileInputStream(libraryPath + song);
	        playMP3 = new AdvancedPlayer(fis);

	        playMP3.setPlayBackListener(this);

	        playerThread = new Thread(this, "MP3PlayerThread");

            playerThread.start();
	           
	    }catch(Exception e){
	    	System.out.println(e);
	    }
	}	
	
	public void stop () {
		try{
	        playMP3.close();
	        fis.close();
	           
	    }catch(Exception e){
	    	System.out.println(e);
	    }
	}
	
	public void playbackStarted(PlaybackEvent playbackEvent)
    {
        System.out.println("Started");
    }
	
	public void playbackFinished(PlaybackEvent playbackEvent)
    {
		try{
	        playMP3.close();
	        fis.close();
	        System.out.println("Ended");
	           
	    }catch(Exception e){
	    	System.out.println(e);
	    }
    }


	@Override
	public void run() {
		try{
            this.playMP3.play();
        }
        catch (Exception e){
            e.printStackTrace();
        }
		
	}
	
}
