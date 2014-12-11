package aiplaylist;

import gui.AIPlayListGUI;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class AprioriSequencer implements Sequencer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private Collection<Transaction> transactionDataBase;
	private List<ItemSet> frequentSet;
	private Transaction currentTransaction = null;
	private Item currentItem;
	private int support = 2;

	public AprioriSequencer(Collection<Transaction> transactionDataBase,
			int support) {
		this.transactionDataBase = transactionDataBase;
		this.support = support;
		this.frequentSet = AIPlayListUtil.getAprioriSet(transactionDataBase,
				support);

	}

	public AprioriSequencer(File dataBase, int support) {
		this.transactionDataBase = AIPlayListUtil
				.getTransactionDataBase(dataBase);
		this.support = support;
		this.frequentSet = AIPlayListUtil.getAprioriSet(transactionDataBase,
				support);

	}

	public AprioriSequencer() {
		File dataBase = null;
		JFileChooser fc  = new JFileChooser();
		if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			dataBase = fc.getSelectedFile();
		}
		this.transactionDataBase = AIPlayListUtil
				.getTransactionDataBase(dataBase);
		
		this.support = Integer.parseInt(JOptionPane.showInputDialog("Entrez le support:"));
		this.frequentSet = AIPlayListUtil.getAprioriSet(transactionDataBase,
				support);
	}

	@Override
	public Item next() {
		currentItem = getRandomFromApriori();
		if (currentTransaction != null) {
			transactionDataBase.add(currentTransaction);
			currentTransaction = null;
			new Thread(new Runnable() {

				@Override
				public void run() {

					updateAprioriset();
				}
			}).start();
		}

		return currentItem;
	}

	private Item getRandomFromApriori() {

		ItemSet freqtransaction;
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
		frequentSet = AIPlayListUtil
				.getAprioriSet(transactionDataBase, support);

	}

	@Override
	public Item finish() {
		if (currentTransaction == null) {
			currentTransaction = new Transaction(currentItem);
		} else {
			currentTransaction.add(currentItem);
		}
		currentItem = getRandomFromApriori();
		return currentItem;
	}

}
