package aiplaylist;

import java.util.ArrayList;
import java.util.List;

public class History {
	private List<Song> playedSongs = new ArrayList<Song>();
	private List<Song> skippedSongs = new ArrayList<Song>();
	private List<Song> history = new ArrayList<Song>();
	
	public void addSong(Song s, boolean played) {
		if (played) {
			playedSongs.add(s);
		} else {
			skippedSongs.add(s);
		}
		
		history.add(s);
	}
	
	public Song getLastPlayedSong() {
		return getLastPlayedSong(1);
	}
	
	public Song getLastPlayedSong(int n) {
		return playedSongs.get(playedSongs.size() - n);
	}
	
	public Song getLastSkippedSong() {
		return getLastSkippedSong(1);
	}
	
	public Song getLastSkippedSong(int n) {
		return skippedSongs.get(playedSongs.size() - n);
	}
	
	/**
	 * @return Toutes les chançons joué depuis l'ouverture du programme
	 */
	public List<Song> getPlayedSongs() {
		return new ArrayList<Song>(playedSongs);
		
	}
	
	/**
	 * @return Toutes les chançons sauté depuis l'ouverture du programme
	 */
	public List<Song> getSkippedSongs() {
		return new ArrayList<Song>(skippedSongs);
		
	}
	
	/**
	 * @return La sequence complete de chançons depuis l'ouverture du programme
	 */
	public List<Song> getSongsSequence() {
		return new ArrayList<Song>(history);
		
	}
}
