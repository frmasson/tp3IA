package aiplaylist;

import java.util.Collection;

public interface LibraryLoader {
	public Collection<Item> loadLibrary(String libraryPath);
}
