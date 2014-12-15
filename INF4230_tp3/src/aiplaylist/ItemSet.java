package aiplaylist;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ItemSet extends AbstractSet<Item> implements Comparable<ItemSet> {

	List<Item> items;
	String id = "";

	public ItemSet(List<Item> items) {
		this.items = new LinkedList<Item>(items);
		Collections.sort(this.items);
		for (Item i : this.items) {
			id += i.getId();
		}

	}

	public ItemSet(Item i) {
		items = new LinkedList<Item>();
		items.add(i);
		id += i.getId();
	}

	public ItemSet(Collection<Item> items) {
		this(new LinkedList<Item>(items));
	}

	@Override
	public Iterator<Item> iterator() {
		return items.iterator();
	}

	@Override
	public int size() {
		return items.size();
	}

	@Override
	public int compareTo(ItemSet o) {
		return this.id.compareTo(o.getId());
	}

	private String getId() {
		return id;
	}

	public ItemSet join(ItemSet other) {
		List<Item> tmp = new LinkedList<Item>(items);
		for (Item j : other) {
			if (!tmp.contains(j)) {
				tmp.add(j);
			}
		}

		return new ItemSet(tmp);
	}

}