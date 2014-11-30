package aiplaylist;

public class AIPlayList {

	private String libraryPath = null;

	private Sequencer sequencer = null;

	private LibraryLoader libraryLoader;

	public AIPlayList(String libraryFolder, String sequencer,
			String libraryLoader) {
		this.libraryPath = libraryFolder;
		this.sequencer = SequencerFactory.getInstance(sequencer);
		this.libraryLoader = LibraryLoaderFactory.getInstance(libraryLoader);
	}

	public Item next() {
		return sequencer.next();

	}

}
