package aiplaylist;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

public class AIPlayListUtil {

	public static List<ItemSet> getAprioriSet(
			Collection<Transaction> transactionDataBase, int support) {
		List<ItemSet> result = new ArrayList<ItemSet>();

		Collection<ItemSet> itemSetDataBase = new ArrayList<ItemSet>();
		for (Transaction t : transactionDataBase) {
			itemSetDataBase.add(Transaction.toItemSet(t));
		}

		FrequentSet frequentItemSet = new FrequentSet(itemSetDataBase, support);

		result.addAll(frequentItemSet.getAllSupportedItemSet());

		while (!frequentItemSet.isEmpty()) {
			frequentItemSet = frequentItemSet.nextCandidates();
			result.addAll(frequentItemSet.getAllSupportedItemSet());
		}

		return result;

	}
	
	public static HashMap<ItemSet, Integer> getAprioriSupportMap(
			Collection<Transaction> transactionDataBase, int support) {
		
		HashMap<ItemSet, Integer> map = new HashMap<>();
		
		Collection<ItemSet> itemSetDataBase = new ArrayList<ItemSet>();
		for (Transaction t : transactionDataBase) {
			itemSetDataBase.add(Transaction.toItemSet(t));
		}

		FrequentSet frequentItemSet = new FrequentSet(itemSetDataBase, support);

		for( SupportedItemSet i :frequentItemSet.getAllSupportedItemSet()) {
			map.put(i, i.getSupport());
		}

		while (!frequentItemSet.isEmpty()) {
			frequentItemSet = frequentItemSet.nextCandidates();
			for( SupportedItemSet i :frequentItemSet.getAllSupportedItemSet()) {
				map.put(i, i.getSupport());
			}
		}
		
		return map;
	}

	public static Collection<Transaction> getTransactionDataBase(File dataBase) {
		if (dataBase == null) {
			return new ArrayList<Transaction>();
		}
		String sHEADER = "TID\tItems";
		Collection<Transaction> transDB = new ArrayList<Transaction>();
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
				for (String s : entry[1].split(";")) {
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
	
	public static void writeTransactionDataBase (Collection<Transaction> transactionDataBase) {
		Writer writer;
		String lines = "";
		String header = "TID\tItems\n";
		
		for (Transaction t:transactionDataBase){
			lines += t.getTid() + "\t";
			for (Item i:t.getItems()) {
				lines += i.getId() + ";";
			}
			
			lines = lines.substring(0, lines.length() -1);
			lines += "\n";
		}
		
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("ResultTDB.txt")));
			writer.write(header);
			writer.write(lines);
			writer.close();
		} catch (IOException ex) {
			System.err.println("Error while writing TDB to file !");
		}
	}
}
