package aiplaylist;

public interface Sequencer {

	public abstract Item next();

	public abstract Item finish();

	public abstract void setLibrary(String libraryFolder);

	public abstract void setProfile(Profile profile);
	
	public abstract void setUsageStats(UsageStatistics stats);
	
	public abstract Profile getProfile();
	
	public abstract UsageStatistics getUsageStats();
	
}
