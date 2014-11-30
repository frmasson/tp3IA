package aiplaylist;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class FrequentSet<T> extends AbstractSet<Set<T>> {

	private Set<Set<T>> frequentSet;

	private Map<Set<T>, Integer> dataSet = null;

	private int support;

	private int degree = 1;

	public FrequentSet(Collection<Transaction<T>> transactionDataBase,
			int support) {
		this.support = support;
		dataSet = new TreeMap<Set<T>, Integer>();
		for (Transaction<T> t : transactionDataBase) {
			for (T i : t) {
				TreeSet<T> tmpSet = new TreeSet<T>();
				tmpSet.add(i);
				add(tmpSet);

			}
		}

	}

	public FrequentSet(int support) {
		this.support = support;
		dataSet = new TreeMap<Set<T>, Integer>();
	}

	public boolean add(Set<T> o) {

		for (Map.Entry<Set<T>, Integer> i : dataSet.entrySet()) {
			if (o.containsAll(i.getKey())) {
				Integer iSupport = i.getValue();
				if (++iSupport >= support) {
					frequentSet.add(i.getKey());
				}

			}

		}

		return true;
	}

	public Iterator<Set<T>> iterator() {
		return frequentSet.iterator();
	}

	public int size() {
		return frequentSet.size();
	}

	public Set<Set<T>> prune(int degree) {
		dataSet.clear();
		for (Set<T> i : frequentSet) {
			if (i.size() < degree) {
				frequentSet.remove(i);
			}
		}
		return frequentSet;
	}

	public FrequentSet<T> selfJoin() {
		FrequentSet<T> freqSet = this.clone();
		for (Set<T> i : freqSet) {
			for (Set<T> j : freqSet) {
				i.addAll(j);
			}
		}
		return freqSet;
	}

	@Override
	public FrequentSet<T> clone() {
		FrequentSet<T> result = new FrequentSet<T>(support);
		for (Set<T> i : this) {
			result.add(i);
		}
		return result;

	}

	public void addAll(Set<Set<T>> col) {
		for (Set<T> o : col) {
			this.add(o);
		}
	}

	public FrequentSet<T> nextCandidates() {
		FrequentSet<T> result = this.selfJoin();
		result.prune(degree + 1);
		return result;
	}
}
