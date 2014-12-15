package aiplaylist;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public class Transaction {

	private Integer tid;
	private static int idCounter = 1;
	private Set<Item> items;

	public Transaction(Integer tid, Collection<Item> items) {
		this.items = new TreeSet<Item>(items);
		this.tid = tid;
		if (tid > idCounter) {
			idCounter = tid;
		}

	}

	public Transaction(Collection<Item> items) {
		this.items = new TreeSet<Item>(items);
		this.tid = ++idCounter;
	}

	public Transaction(Item currentItem) {
		this.items = new TreeSet<Item>();
		items.add(currentItem);
		this.tid = ++idCounter;
	}

	public void add(Item currentItem) {
		items.add(currentItem);
	}

	public static ItemSet toItemSet(Transaction t) {
		return new ItemSet(t.getItems());
	}

	public Set<Item> getItems() {
		return items;
	}
	
	public Integer getTid() {
		return tid;
	}

}
