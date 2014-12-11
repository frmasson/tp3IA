package aiplaylist;

import java.io.File;

public class SequencerFactory {

	public static final String APRIORI = "apriori";
	private static final String DEFAULT = "default";

	public static Sequencer getInstance(String sequencer) {
		if (sequencer == null) {
			return new DefaultSequencer();
		} else if (sequencer.equalsIgnoreCase(DEFAULT)) {
			return new DefaultSequencer();
		} else if (sequencer.equalsIgnoreCase(APRIORI)) {
			return new AprioriSequencer();
		}

		return null;
	}

}
