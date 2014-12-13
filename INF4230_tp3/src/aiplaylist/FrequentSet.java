package aiplaylist;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class FrequentSet extends AbstractSet<ItemSet> {

	private Set<ItemSet> frequentSet;

	private Map<ItemSet, Integer> dataSet = null;

	private Collection<ItemSet> transactionDataBase;

	private int minSupport;

	private int degree = 1;

	public FrequentSet(Collection<ItemSet> transactionDataBase2, int support) {
		this.dataSet = new TreeMap<ItemSet, Integer>();
		this.frequentSet = new TreeSet<ItemSet>();
		this.transactionDataBase = transactionDataBase2;
		loadTransactionDataBase(this.transactionDataBase, support);

	}

	private void loadTransactionDataBase(
			Collection<ItemSet> transactionDataBase, int support) {
		int transactionCount = 0;
		for (ItemSet t : transactionDataBase) {
			for (Item i : t) {
				ItemSet tmpSet = new ItemSet(i);
				Integer iSupport = dataSet.get(tmpSet);
				if (iSupport == null) {
					dataSet.put(tmpSet, 1);
				} else {
					dataSet.put(tmpSet, ++iSupport);
				}
			}
			transactionCount++;
		}
		minSupport = (int) (((double) support / 100) * transactionCount);
		if (minSupport > 0) {
			for (Map.Entry<ItemSet, Integer> i : dataSet.entrySet()) {
				if (i.getValue() >= minSupport) {
					frequentSet.add(i.getKey());
				}
			}
		}

	}

	public FrequentSet(int support) {
		this.minSupport = support;
		dataSet = new TreeMap<ItemSet, Integer>();
	}

	public FrequentSet(FrequentSet other) {
		this.degree = other.degree;
		this.minSupport = other.minSupport;
		this.frequentSet = new TreeSet<ItemSet>();
		for (ItemSet i : other) {
			frequentSet.add(new ItemSet(i));
		}
	}

	public FrequentSet(Collection<ItemSet> transactionDataBase, int support,
			Set<ItemSet> freqSet) {
		this.transactionDataBase = transactionDataBase;
		this.minSupport = support;
		this.frequentSet = freqSet;
		this.dataSet = new TreeMap<ItemSet, Integer>();
	}

	public Iterator<ItemSet> iterator() {
		return frequentSet.iterator();
	}

	public int size() {
		return frequentSet.size();
	}

	public void filter(int degree) {
		List<ItemSet> toBeRemoved = new ArrayList<ItemSet>();
		for (ItemSet i : frequentSet) {
			if (i.size() != degree) {
				toBeRemoved.add(i);
			}
		}
		frequentSet.removeAll(toBeRemoved);
		this.degree = degree;
	}

	public FrequentSet nextCandidates() {
		Set<ItemSet> freqSet = new TreeSet<ItemSet>();
		for (ItemSet i : this) {
			for (ItemSet j : this) {
				freqSet.add(j.join(i));
			}
		}
		FrequentSet result = new FrequentSet(transactionDataBase, minSupport,
				freqSet);
		result.filter(++degree);
		result.reloadTransactionDataBase();
		result.prune();
		return result;
	}

	private void prune() {
		List<ItemSet> toBeRemoved = new LinkedList<ItemSet>();
		for (ItemSet i : this) {
			if (dataSet.get(i) < minSupport) {
				dataSet.remove(i);
				toBeRemoved.add(i);
			}
		}
		frequentSet.removeAll(toBeRemoved);
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
