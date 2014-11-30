package aiplaylist;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class DefaultLibraryLoader implements LibraryLoader {

	@Override
	public Collection<Item> loadLibrary(String libraryPath) {
		File dir = new File(libraryPath);
		Collection<Item> musicLibrary = new ArrayList<Item>();

		loadFileNames(dir, musicLibrary);
		return musicLibrary;
	}

	private Collection<Item> loadFileNames(File song,
			Collection<Item> musicLibrary) {
		if (song.isDirectory()) {
			for (File f : song.listFiles()) {
				loadFileNames(f, musicLibrary);
			}
		} else {
			Collection<Feature> features = new ArrayList<Feature>();
			features.add(new Feature(Song.TITLE, song.getName()));
			musicLibrary.add(new Song(song.getName(), features));

		}
		return musicLibrary;
	}

}
