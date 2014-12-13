package aiplaylist;

import java.util.ArrayList;
import java.util.List;

public class History {
	private List<Item> playedSongs = new ArrayList<Item>();
	private List<Item> skippedSongs = new ArrayList<Item>();
	private List<Item> history = new ArrayList<Item>();
	
	public void addSong(Item s, boolean played) {
		if (played) {
			playedSongs.add(s);
		} else {
			skippedSongs.add(s);
		}
		
		history.add(s);
	}
	
	public Item getLastPlayedItem() {
		return getLastPlayedItem(1);
	}
	
	public Item getLastPlayedItem(int n) {
		return playedSongs.get(playedSongs.size() - n);
	}
	
	public Item getLastSkippedItem() {
		return getLastSkippedItem(1);
	}
	
	public Item getLastSkippedItem(int n) {
		return skippedSongs.get(playedSongs.size() - n);
	}
	
	/**
	 * @return Toutes les chançons joué depuis l'ouverture du programme
	 */
	public List<Item> getPlayedSongs() {
		return new ArrayList<Item>(playedSongs);
		
	}
	
	/**
	 * @return Toutes les chançons sauté depuis l'ouverture du programme
	 */
	public List<Item> getSkippedSongs() {
		return new ArrayList<Item>(skippedSongs);
		
	}
	
	/**
	 * @return La sequence complete de chançons depuis l'ouverture du programme
	 */
	public List<Item> getSongsSequence() {
		return new ArrayList<Item>(history);
		
	}
}
