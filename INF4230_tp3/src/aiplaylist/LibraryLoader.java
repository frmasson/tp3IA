package aiplaylist;

import java.util.Collection;
import java.util.List;

public interface LibraryLoader {
	public Collection<Item> loadLibrary(String libraryPath);

	public List<Item> getLibrary();
}
