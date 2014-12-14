package aiplaylist;

import java.util.List;

public class Profile {
	
	private History history = new History();
	
	public List<Item> getFrame(int frame) {
		int max = history.getPlayedSongs().size();
		if (frame > max) frame = max;
		
		return history.getPlayedSongs().subList(max - frame, max);
		
	}
	
	public History getHistory() {
		return history;
		
	}
	
	public void add(Item s, boolean played) {
		history.addSong(s, played);
	}
}
