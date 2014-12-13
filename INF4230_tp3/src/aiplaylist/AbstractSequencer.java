package aiplaylist;

public abstract class AbstractSequencer implements Sequencer {
	
	public void updateState(Item currentItem, boolean played) {
		this.getProfile().add(currentItem, played);
		this.getUsageStats().notify(played);
	}

}
