package aiplaylist;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class AprioriSequencer extends AbstractSequencer implements Sequencer {
	
	public static final int FRAME_SIZE = 4;
	
	public static final double MIN_CONF = 0.8;

	public static void main(String[] args) {

	}

	private Collection<Transaction> transactionDataBase;
	private List<ItemSet> frequentSet;
	private Transaction currentTransaction = null;
	private Item currentItem;
	private int support = 80;
	private LibraryLoader libraryLoader;
	private Profile profile;
	private UsageStatistics stats;

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
		updateState(currentItem, false);
		stats.notifyStartGen();
		currentItem = getRandomFromSet(genNexts(MIN_CONF, currentItem));
		
		if (currentItem == null)
			currentItem = getRandomFromApriori();
		
		stats.notifyStopGen();
		
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
	
	private Item getRandomFromSet(ItemSet itemSet) {
		if (itemSet == null) return null;
		return itemSet.items.get((int) (Math.random() * (itemSet.size() - 1)));
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
			result = getItemFromLibrary(result);
		} else {
			List<Item> lib = libraryLoader.getLibrary();
			result = lib.get((int) (Math.random() * lib.size()));
		}
		return result;

	}

	private Item getItemFromLibrary(Item i) {
		List<Item> lib = libraryLoader.getLibrary();
		Item result = null;
		int index = lib.indexOf(i);
		if (index != -1) {
			result = lib.get(index);
		}
		return result;
	}

	synchronized private void updateAprioriset() {
		frequentSet = AIPlayListUtil
				.getAprioriSet(transactionDataBase, support);

	}
	
	private ItemSet genNexts(double minConf, Item lastItem) {
		List<ItemSet> itemSets = AIPlayListUtil.getAprioriSet(transactionDataBase, support);
		ItemSet consequent = null;
		for (ItemSet itemSet : itemSets) {
			SupportedItemSet i = (SupportedItemSet) itemSet;
			if (filterSets(i)) {
				consequent = genConsequents(i, i, minConf, lastItem);
				if (consequent != null) return consequent;
			}
		}
		return null;
	}
	
	private ItemSet genConsequents(SupportedItemSet itemset, SupportedItemSet subset, double minConf, Item lastItem) {
		ItemSet consequent = new ItemSet(itemset);
		List<SupportedItemSet> possibleSubset = genMinusOneSubsets(subset);
		for (SupportedItemSet a : possibleSubset) {
			double conf = itemset.getSupport() / a.getSupport();
			if (conf >= minConf) {
				consequent.removeAll(a);
				if (!(consequent.size() == 1 && consequent.contains(lastItem))) {
					return consequent;
				} else if (a.size() <= 1 && !(consequent.size() > 1 && consequent.contains(lastItem))) {
					return consequent;
				} else if (a.size() > 1) {
					return genConsequents(itemset, a, minConf, lastItem);
				}
			}
			
		}
		return null;
	}
	
	private List<SupportedItemSet> genMinusOneSubsets(SupportedItemSet itemSet) {
		List<ItemSet> subsets = new LinkedList<>();
		List<SupportedItemSet> resultSets = new LinkedList<>();
		
		// Generate subsets
		
		ItemSet set = new ItemSet(itemSet);
		set.items.remove(set.items.size() - 1);
		subsets.add(set);
		
		for (int i = 1; i < itemSet.size(); i++) {
			set = new ItemSet(itemSet);
			set.items.remove(i);
			subsets.add(set);
		}
		
		set = new ItemSet(itemSet);
		set.items.remove(0);
		subsets.add(set);
		
		// Find all available subsets
		
		Map<ItemSet, Integer> itemSets = AIPlayListUtil.getAprioriSupportMap(transactionDataBase, support);
		
		for (ItemSet item : subsets) {
			if (itemSets.containsKey(item))
				resultSets.add(new SupportedItemSet(item, itemSets.get(item)));
		}
		
		
		return resultSets;
	}
	
	private boolean filterSets(SupportedItemSet itemSet) {
		
		for (Item i : getProfile().getFrame(FRAME_SIZE)) {
			if (itemSet.contains(i)) return true;
		}
		return false;
	}

	@Override
	public Item finish() {
		updateState(currentItem, true);
		
		if (currentTransaction == null) {
			currentTransaction = new Transaction(currentItem);
		} else {
			currentTransaction.add(currentItem);
		}
		currentItem = getRandomFromSet(genNexts(MIN_CONF, currentItem));
		
		if (currentItem == null)
			currentItem = getRandomFromApriori();

		return currentItem;
	}

	@Override
	public void setLibrary(String libraryFolder) {
		this.libraryLoader = new Mp3LibraryLoader(libraryFolder);
	}

	@Override
	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	@Override
	public void setUsageStats(UsageStatistics stats) {
		this.stats = stats;
	}

	@Override
	public Profile getProfile() {
		return profile;
	}

	@Override
	public UsageStatistics getUsageStats() {
		return stats;
	}

	@Override
	public List<Item> getLibrary() {
		return libraryLoader.getLibrary();
	}

}
