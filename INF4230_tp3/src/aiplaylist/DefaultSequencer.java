package aiplaylist;

import java.util.Collection;
import java.util.Iterator;

public class DefaultSequencer implements Sequencer {

	Collection<Item> items = null;

	Iterator<Item> currentItem;

	public DefaultSequencer() {
	}

	@Override
	public Item next() {
		return currentItem.hasNext() ? (Item) currentItem.next() : null;
	}

	public void setLibrary(Collection<Item> library) {
		this.items = library;
		this.currentItem = items.iterator();
	}

	@Override
	public Item finish() {
		return next();
	}

}
