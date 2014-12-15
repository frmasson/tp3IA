package aiplaylist;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AIPlaylistBot {

	public class Statistics {

		public int nextCount = 0;
		public int likeCount = 0;

		@Override
		public String toString() {
			String result = "Statistics:\n";
			result += "\tNext Count = " + nextCount;
			result += "\tLike Count = " + likeCount;
			return result;
		}

	}

	private AIPlayList engine;
	private ArrayList<Item> hateList;
	private ArrayList<Item> likeList;
	// private TreeSet<Item> hateList;
	// private TreeSet<Item> likeList;
	private boolean optimist;
	private AIPlaylistBot.Statistics stats;

	public AIPlaylistBot(String libraryFolder, Sequencer sequencer,
			boolean optimist) {
		this.engine = new AIPlayList(libraryFolder, sequencer);
		this.hateList = new ArrayList<Item>();
		this.likeList = new ArrayList<Item>();
		this.optimist = optimist;
		this.stats = new Statistics();
	}

	public static void main(String[] args) {

		File tdb = new File(args[1]);
		if (!tdb.exists()) {
			try {
				tdb.createNewFile();
				Writer fw = new FileWriter(tdb);
				fw.write("TID\tItems");
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		AIPlaylistBot bot = new AIPlaylistBot(args[0], new AprioriSequencer(
				AIPlayListUtil.getTransactionDataBase(tdb), 2,
				new Mp3LibraryLoader()), Boolean.getBoolean(args[2]));
		bot.loadProfile1();
		bot.simulate(300);
	}

	private void simulate(int i) {
		int count = i;
		Item next = null;
		next = engine.next(true);

		while (count > 0) {
			if (next == null) {
				System.out.println("No more choices.");
				return;
			}
			if (hateList.contains(next)
					|| (!optimist && !likeList.contains(next))) {
				next = engine.next(true);
				stats.nextCount++;
			} else {
				next = engine.like(true);
				stats.likeCount++;
			}
			--count;
		}
		
		((AprioriSequencer) (engine.getSequencer())).getUsageStats()
		.setEndingTime();

		((AprioriSequencer) (engine.getSequencer())).getUsageStats()
		.exportStatistics();
	}

	private ArrayList<Item> generateRandomSet(int bound, List<Item> musicLibrary) {
		ArrayList<Item> random = new ArrayList<Item>();
		Random rand = new Random(System.currentTimeMillis());

		for (int i = 0; i < bound; i++) {
			random.add(musicLibrary.get(rand.nextInt(musicLibrary.size())));
		}

		return random;

	}

	private ArrayList<Item> generateStyleSet(int bound,
			List<Item> musicLibrary, String style) {
		ArrayList<Item> styleSet = new ArrayList<Item>();
		int counter = 0;

		for (Item item : musicLibrary) {

			if (((Song) item).getFeatures().get("xmpDM:genre") == style) {// Metadata.TYPE?
				styleSet.add(item);
				counter++;
			}

			if (counter == bound) {
				break;
			}
		}

		return styleSet;

	}

	private void loadProfile1() {
		likeList.addAll(generateRandomSet(200, engine.getSequencer()
				.getLibrary()));
		optimist = false;
	}

	private void loadProfile2() {
		hateList.addAll(generateStyleSet(200, engine.getSequencer()
				.getLibrary(), "Pop"));
		optimist = true;
	}

}
