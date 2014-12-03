package aiplaylist;

import java.util.ArrayList;
import java.util.Collection;

public class Transaction extends ItemSet {

	private Integer tid;
	private static int idCounter = 1;

	public Transaction(Integer tid, Collection<Item> items) {
		super(items);
		this.tid = tid;
		if (tid > idCounter) {
			idCounter = tid;
		}

	}

	public Transaction(Collection<Item> items) {
		super(items);
		this.tid = ++idCounter;
	}

	public Transaction() {
		this(new ArrayList<Item>());
	}

}
