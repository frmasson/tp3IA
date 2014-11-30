package aiplaylist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class AIPlayListUtil {

	public static List<Set<Item>> getAprioriSet(
			Collection<Transaction<Item>> transactionDataBase, int support) {
		List<Set<Item>> result = new ArrayList<Set<Item>>();

		FrequentSet<Item> frequentItemSet = new FrequentSet<Item>(
				transactionDataBase, support);

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

}
