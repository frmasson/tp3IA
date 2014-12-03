package aiplaylist;

import java.util.Collection;

public class Song implements Item {

	public static final String TITLE = "title";
	private String id;
	private String title = null;
	private Collection<Feature> features;

	public Song(String title, Collection<Feature> metadata) {
		this.id = title;
		this.title = title;
		this.features = metadata;
	}

	public Song(String s) {
		this.id = s;
	}

	@Override
	public Collection<Feature> getFeatures() {
		return features;
	}

	@Override
	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public String getDisplayName() {
		return title;
	}

	@Override
	public int compareTo(Item o) {
		return this.id.compareTo(o.getId());
	}

	@Override
	public String toString() {
		return id;
	}

}
