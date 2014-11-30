package aiplaylist;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.List;

public class Transaction<T> extends AbstractSet<T> {

	private List<T> transaction;

	public Transaction(List<T> transaction) {
		this.transaction = transaction;
	}

	public T get(int i) {
		return transaction.get(i);
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
