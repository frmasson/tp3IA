package aiplaylist;

import java.util.Collection;

public interface Item extends Comparable<Item> {

	Collection<Feature> getFeatures();

	String getId();

	String getDisplayName();

}
