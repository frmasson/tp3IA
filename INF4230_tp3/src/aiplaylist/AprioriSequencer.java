package aiplaylist;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class AprioriSequencer implements Sequencer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private Collection<Transaction<Item>> transactionDataBase;
	private List<Set<Item>> frequentSet;

	public AprioriSequencer(Collection<Transaction<Item>> transactionDataBase,
			int support) {
		this.transactionDataBase = transactionDataBase;
		this.frequentSet = AIPlayListUtil.getAprioriSet(transactionDataBase,
				support);

	}

	public AprioriSequencer(File dataBase, int support) {
		this.transactionDataBase = AIPlayListUtil
				.getTransactionDataBase(dataBase);
		this.frequentSet = AIPlayListUtil.getAprioriSet(transactionDataBase,
				support);

	}

	@Override
	public Item next() {
		Set<Item> freqtransaction = frequentSet
				.get((int) (Math.random() * frequentSet.size()));

		int itemIndex = (int) (Math.random() * freqtransaction.size());
		Iterator<Item> iTrans = freqtransaction.iterator();
		Item result = iTrans.next();
		for (int i = 0; i < itemIndex; i++) {
			result = iTrans.next();
		}
		return result;
	}

}
