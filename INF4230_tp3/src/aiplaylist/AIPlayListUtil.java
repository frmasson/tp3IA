package aiplaylist;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class AIPlayListUtil {

	public static List<Set<Item>> getAprioriSet(
			Collection<ItemSet> transactionDataBase, int support)
			throws CloneNotSupportedException {
		List<Set<Item>> result = new ArrayList<Set<Item>>();

		FrequentSet frequentItemSet = new FrequentSet(transactionDataBase,
				support);

		result.addAll(frequentItemSet);

		while (!frequentItemSet.isEmpty()) {
			frequentItemSet = frequentItemSet.nextCandidates();
			frequentItemSet.addAll(transactionDataBase);
			result.addAll(frequentItemSet);
		}

		return result;

	}

	public static void main(String[] args) {

	}

	public static Collection<ItemSet> getTransactionDataBase(File dataBase) {
		String sHEADER = "TID\tItems";
		Collection<ItemSet> transDB = new ArrayList<ItemSet>();
		File tdb = dataBase;
		BufferedReader bf = null;
		try {
			bf = new BufferedReader(new FileReader(tdb));
			String nextLine = bf.readLine();
			if (!nextLine.equals(sHEADER)) {
				bf.close();
				return null;
			}
			while ((nextLine = bf.readLine()) != null) {
				String[] entry = nextLine.split("\t");
				TreeSet<Item> items = new TreeSet<Item>();
				Integer tid = Integer.parseInt(entry[0]);
				for (String s : entry[1].split(",")) {
					items.add(new Song(s));
				}
				transDB.add(new Transaction(tid, items));
			}
			bf.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return transDB;
	}
}
