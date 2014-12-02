package aiplaylist;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class Transaction<T> extends AbstractSet<T> {

	private Set<T> transaction;
	private Integer tid;

	public Transaction(Set<T> transaction) {
		this.transaction = transaction;
	}

	public Transaction(Integer tid, TreeSet<Item> items) {
		this.tid = tid;

	}

	@Override
	public int size() {
		return transaction.size();
	}

	@Override
	public Iterator<T> iterator() {
		return transaction.iterator();
	}

}
