package aiplaylist;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultLibraryLoader implements LibraryLoader {

	List<Item> musicLibrary;

	@Override
	public Collection<Item> loadLibrary(String libraryPath) {
		File dir = new File(libraryPath);
		musicLibrary = new ArrayList<Item>();

		loadFileNames(dir, musicLibrary);
		return musicLibrary;
	}

	private Collection<Item> loadFileNames(File song, List<Item> musicLibrary) {
		if (song.isDirectory()) {
			for (File f : song.listFiles()) {
				loadFileNames(f, musicLibrary);
			}
		} else {
			Map<String, String> features = new HashMap<String, String>();
			features.put(Song.TITLE, song.getName());
			musicLibrary.add(new Song(song.getName(), features));

		}
		return musicLibrary;
	}

	@Override
	public List<Item> getLibrary() {
		return musicLibrary;
	}

}
