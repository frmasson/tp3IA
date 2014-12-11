package aiplaylist;

public class LibraryLoaderFactory {

	public static final String MP3 = "mp3";

	public static LibraryLoader getInstance(String libraryLoader) {
		if (libraryLoader == null) {
			// Default Library Loader
			return new DefaultLibraryLoader();
		} else if (libraryLoader.equals(MP3)) {
			return new Mp3LibraryLoader();
		}

		return null;
	}

}
