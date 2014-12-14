package aiplaylist;

public class SupportedItemSet extends ItemSet {
	private int support;
	
	public SupportedItemSet(ItemSet i, int support) {
		super(i);
		this.support = support;
	}
	
	public int getSupport() {
		return support;
	}
}