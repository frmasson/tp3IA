package aiplaylist;

import java.util.Collection;
import java.util.Iterator;

public class DefaultSequencer extends AbstractSequencer implements Sequencer {

	Collection<Item> items = null;

	Iterator<Item> currentItem;
	
	private Item lastItem = null;

	private Mp3LibraryLoader libraryLoader;
	
	private UsageStatistics stats;
	
	private Profile profile;

	public DefaultSequencer() {
	}

	@Override
	public Item next() {
		if (lastItem != null) {
			updateState(lastItem, false);
		}
		
		lastItem = currentItem.hasNext() ? (Item) currentItem.next() : null;
		
		return lastItem;
	}

	public void setLibrary(Collection<Item> library) {
		this.items = library;
		this.currentItem = items.iterator();
	}

	@Override
	public Item finish() {
		if (lastItem != null) {
			updateState(lastItem, true);
		}
		lastItem = currentItem.hasNext() ? (Item) currentItem.next() : null;

		return lastItem;
	}

	@Override
	public void setLibrary(String libraryFolder) {
		this.libraryLoader = new Mp3LibraryLoader(libraryFolder);
	}

	@Override
	public void setProfile(Profile profile) {
		this.profile = profile;
		
	}

	@Override
	public void setUsageStats(UsageStatistics stats) {
		this.stats = stats;
		
	}

	@Override
	public Profile getProfile() {
		return profile;
	}

	@Override
	public UsageStatistics getUsageStats() {
		return stats;
	}

}
