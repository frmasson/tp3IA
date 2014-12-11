package aiplaylist;

public interface Sequencer {

	public abstract Item next();

	public abstract Item finish();

	public abstract void setLibrary(String libraryFolder);

}
