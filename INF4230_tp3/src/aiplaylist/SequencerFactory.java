package aiplaylist;

import java.io.File;

public class SequencerFactory {

	public static Sequencer getInstance(String sequencer) {
		if (sequencer == null) {
			return new DefaultSequencer();
		} else if (sequencer.equalsIgnoreCase("default")) {
			return new DefaultSequencer();
		} else if (sequencer.equalsIgnoreCase("apriori")) {
			return new AprioriSequencer(new File(AIPlayList.class
					.getClassLoader().getResource("TransactionDataBase.txt")
					.getFile()), 2);
		}

		return null;
	}

}
