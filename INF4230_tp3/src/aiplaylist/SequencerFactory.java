package aiplaylist;

public class SequencerFactory {

	public static Sequencer getInstance(String sequencer) {
		if (sequencer == null) {
			return new DefaultSequencer();
		} else if (sequencer.equals("default")) {
			return new DefaultSequencer();
		}

		return null;
	}

}
