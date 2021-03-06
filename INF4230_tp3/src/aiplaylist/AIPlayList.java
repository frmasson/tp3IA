package aiplaylist;

import java.util.List;

public class AIPlayList {

	private String libraryPath = null;

	private Sequencer sequencer = null;

	private LibraryLoader libraryLoader;

	private Mp3Player audioController;

	private Profile profile = new Profile();

	private UsageStatistics stats = new UsageStatistics();

	private boolean started = false;

	public AIPlayList(String libraryFolder, String sequencer,
			String libraryLoader) {
		this(libraryFolder, SequencerFactory.getInstance(sequencer));
	}

	public AIPlayList(String libraryFolder, Sequencer sequencer) {
		this.libraryPath = libraryFolder.replace('\\', '/');
		this.sequencer = sequencer;
		this.sequencer.setLibrary(libraryFolder);
		this.sequencer.setProfile(profile);
		this.sequencer.setUsageStats(stats);
		this.audioController = new Mp3Player(libraryFolder);
	}

	public Item next() {
		if (!started) {
			stats.setStartingTime();
			started = true;
		}

		audioController.stop();
		Item result = sequencer.next();

		play(result);

		return result;

	}

	private void play(Item result) {
		String path = result.getFeatures().get(Item.PATH);
		path = path.replace('\\', '/');
		path = path.replaceFirst(libraryPath, "");
		audioController.play(path);
	}

	public Item like() {
		audioController.stop();
		Item next = sequencer.finish();
		play(next);
		return next;
	}

	public void setSequencer(Sequencer sequencer) {
		this.sequencer = sequencer;
		this.sequencer.setLibrary(libraryPath);
	}

	public Sequencer getSequencer() {
		return sequencer;
	}

	public List<Item> getLibrary() {
		return libraryLoader.getLibrary();
	}

	public Item next(boolean silentMode) {
		if (!silentMode) {
			return next();
		} else {
			return sequencer.next();
		}
	}

	public Item like(boolean silentMode) {
		if (!silentMode) {
			return like();
		} else {
			return sequencer.finish();
		}

	}

}
