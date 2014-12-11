package aiplaylist;

import java.util.Map;

public interface Item extends Comparable<Item> {

	String PATH = "path";

	Map<String, String> getFeatures();

	String getId();

	String getDisplayName();

}
