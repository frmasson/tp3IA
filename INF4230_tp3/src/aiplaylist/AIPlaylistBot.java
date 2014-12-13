package aiplaylist;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.TreeSet;

public class AIPlaylistBot {

	public class Statistics {

		public int nextCount = 0;
		public int likeCount = 0;

		@Override
		public String toString() {
			String result = "Statistics:\n";
			result += "\tNext Count = " + nextCount;
			result += "\tLike Count = " + nextCount;
			return result;
		}

	}

	private AIPlayList engine;
	private TreeSet<Item> hateList;
	private TreeSet<Item> likeList;
	private boolean optimist;
	private AIPlaylistBot.Statistics stats;

	public AIPlaylistBot(String libraryFolder, Sequencer sequencer,
			boolean optimist) {
		this.engine = new AIPlayList(libraryFolder, sequencer);
		this.hateList = new TreeSet<Item>();
		this.likeList = new TreeSet<Item>();
		this.optimist = optimist;
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		AIPlaylistBot bot = new AIPlaylistBot(args[0], new AprioriSequencer(
				AIPlayListUtil.getTransactionDataBase(tdb), 2,
				new Mp3LibraryLoader()), Boolean.getBoolean(args[2]));
		bot.simulate(100);
	}

	private void simulate(int i) {
		int count = i;
		Item next = null;
		while (count > 0) {
			next = engine.next();
			if (next == null) {
				System.out.println("No more choices.");
				return;
			}
			if (hateList.contains(next)
					|| (!optimist && !likeList.contains(next))) {
				next = engine.next();
				stats.nextCount++;
			} else {
				engine.like();
				stats.likeCount++;
			}
		}

		System.out.println(stats);
	}

}
