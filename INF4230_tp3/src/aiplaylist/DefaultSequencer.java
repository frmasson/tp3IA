package aiplaylist;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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
		stats.notifyStartGen();
		lastItem = currentItem.hasNext() ? (Item) currentItem.next() : null;
		stats.notifyStopGen();
		
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
		stats.notifyStartGen();
		lastItem = currentItem.hasNext() ? (Item) currentItem.next() : null;
		stats.notifyStopGen();
		
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

	@Override
	public List<Item> getLibrary() {
		return (List<Item>) items;
	}

	@Override
	public Collection<Transaction> getTransactionDataBase() {
		return null;
	}

}
