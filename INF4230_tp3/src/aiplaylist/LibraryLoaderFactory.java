package aiplaylist;

public class LibraryLoaderFactory {

	public static LibraryLoader getInstance(String libraryLoader) {
		if (libraryLoader == null) {
			// Default Library Loader
			return new DefaultLibraryLoader();
		} else if (libraryLoader.equals("mp3")) {
			return new Mp3LibraryLoader();
		}

		return null;
	}

}
