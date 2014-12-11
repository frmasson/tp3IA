package aiplaylist;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class AprioriSequencer implements Sequencer {

	public static void main(String[] args) {

	}

	private Collection<Transaction> transactionDataBase;
	private List<ItemSet> frequentSet;
	private Transaction currentTransaction = null;
	private Item currentItem;
	private int support = 2;
	private LibraryLoader libraryLoader;

	public AprioriSequencer(Collection<Transaction> transactionDataBase,
			int support, LibraryLoader libraryLoader) {
		this.transactionDataBase = transactionDataBase;
		this.support = support;
		this.frequentSet = AIPlayListUtil.getAprioriSet(transactionDataBase,
				support);
		this.libraryLoader = libraryLoader;

	}

	public AprioriSequencer(File dataBase, int support, LibraryLoader lib) {
		this(AIPlayListUtil.getTransactionDataBase(dataBase), support, lib);

	}

	public AprioriSequencer() {
		File dataBase = null;
		JFileChooser fc = new JFileChooser();
		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			dataBase = fc.getSelectedFile();
		}
		this.transactionDataBase = AIPlayListUtil
				.getTransactionDataBase(dataBase);
		this.support = 0;
		if (!transactionDataBase.isEmpty()) {
			this.support = Integer.parseInt(JOptionPane
					.showInputDialog("Entrez le support:"));
		}
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
		Item result = null;
		if (!frequentSet.isEmpty()) {
			freqtransaction = frequentSet
					.get((int) (Math.random() * frequentSet.size()));

			int itemIndex = (int) (Math.random() * freqtransaction.size());
			Iterator<Item> iTrans = freqtransaction.iterator();
			result = iTrans.next();
			for (int i = 0; i < itemIndex; i++) {
				result = iTrans.next();
			}
		} else {
			List<Item> lib = libraryLoader.getLibrary();
			result = lib.get((int) (Math.random() * lib.size()));
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

	@Override
	public void setLibrary(String libraryFolder) {
		this.libraryLoader = new Mp3LibraryLoader(libraryFolder);
	}

}
