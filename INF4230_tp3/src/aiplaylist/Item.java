package aiplaylist;

import java.util.Collection;

public interface Item {

	Collection<Feature> getFeatures();

	long getId();

	String getDisplayName();

}
