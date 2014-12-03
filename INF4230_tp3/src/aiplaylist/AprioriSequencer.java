package aiplaylist;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class AprioriSequencer implements Sequencer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private Collection<ItemSet> transactionDataBase;
	private List<Set<Item>> frequentSet;
	private Transaction currentTransaction = new Transaction();
	private Item currentItem;

	public AprioriSequencer(Collection<ItemSet> transactionDataBase, int support) {
		this.transactionDataBase = transactionDataBase;
		try {
			this.frequentSet = AIPlayListUtil.getAprioriSet(
					transactionDataBase, support);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public AprioriSequencer(File dataBase, int support) {
		this.transactionDataBase = AIPlayListUtil
				.getTransactionDataBase(dataBase);
		try {
			this.frequentSet = AIPlayListUtil.getAprioriSet(
					transactionDataBase, support);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public AprioriSequencer() {
		this(new File(AIPlayList.class.getClassLoader()
				.getResource("TransactionDataBase.txt").getFile()), 2);
	}

	@Override
	public Item next() {
		transactionDataBase.add(currentTransaction);
		currentTransaction.clear();
		Item result = getRandomFromApriori();
		new Thread(new Runnable() {

			@Override
			public void run() {

				updateAprioriset();
			}
		});
		return result;
	}

	private Item getRandomFromApriori() {

		Set<Item> freqtransaction;
		freqtransaction = frequentSet.get((int) (Math.random() * frequentSet
				.size()));

		int itemIndex = (int) (Math.random() * freqtransaction.size());
		Iterator<Item> iTrans = freqtransaction.iterator();
		Item result = iTrans.next();
		for (int i = 0; i < itemIndex; i++) {
			result = iTrans.next();
		}
		return result;

	}

	synchronized private void updateAprioriset() {
		try {
			frequentSet = AIPlayListUtil.getAprioriSet(transactionDataBase, 5);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Item finish() {
		currentTransaction.add(currentItem);
		return getRandomFromApriori();
	}

}
