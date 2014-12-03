package aiplaylist;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class FrequentSet extends AbstractSet<ItemSet> {

	private Set<ItemSet> frequentSet;

	private Map<ItemSet, Integer> dataSet = null;

	private Collection<ItemSet> transactionDataBase;

	private int support;

	private int degree = 1;

	public FrequentSet(Collection<ItemSet> transactionDataBase2, int support) {
		this.support = support;
		this.dataSet = new TreeMap<ItemSet, Integer>();
		this.frequentSet = new TreeSet<ItemSet>();
		this.transactionDataBase = transactionDataBase2;
		loadTransactionDataBase(this.transactionDataBase);

	}

	private void loadTransactionDataBase(Collection<ItemSet> transactionDataBase) {
		for (ItemSet t : transactionDataBase) {
			for (Item i : t) {
				ItemSet tmpSet = new ItemSet(i);
				Integer iSupport = dataSet.get(tmpSet);
				if (iSupport == null) {
					dataSet.put(tmpSet, 1);
				} else {
					dataSet.put(tmpSet, ++iSupport);
					if (iSupport >= support) {
						frequentSet.add(tmpSet);
					}
				}
			}
		}
	}

	public FrequentSet(int support) {
		this.support = support;
		dataSet = new TreeMap<ItemSet, Integer>();
	}

	public Iterator<ItemSet> iterator() {
		return frequentSet.iterator();
	}

	public int size() {
		return frequentSet.size();
	}

	public void filter(int degree) {
		for (ItemSet i : frequentSet) {
			if (i.size() < degree) {
				frequentSet.remove(i);
			}
		}
		this.degree = degree;
	}

	public FrequentSet nextCandidates() throws CloneNotSupportedException {
		FrequentSet tmp = (FrequentSet) this.clone();
		for (ItemSet i : tmp) {
			for (ItemSet j : this) {
				j.join(i);
			}
		}
		this.filter(++degree);
		reloadTransactionDataBase();
		this.prune();
		return this;
	}

	private void prune() {
		for (ItemSet i : this) {
			if (dataSet.get(i) < support) {
				dataSet.remove(i);
			}

		}
	}

	private void reloadTransactionDataBase() {
		for (ItemSet i : this) {
			for (ItemSet t : transactionDataBase) {
				if (t.containsAll(i)) {
					Integer iSupport = dataSet.get(i);
					if (iSupport == null) {
						dataSet.put(i, 1);
					} else {
						dataSet.put(i, ++iSupport);
					}
				}
			}
		}
	}

}
