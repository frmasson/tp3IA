package aiplaylist;

import java.util.Collection;

public class Song implements Item {

	public static final String TITLE = "title";
	private long id;
	private String title = null;
	private Collection<Feature> features;

	public Song(String title, Collection<Feature> metadata) {
		this.id = System.currentTimeMillis();
		this.title = title;
		this.features = metadata;
	}

	@Override
	public Collection<Feature> getFeatures() {
		return features;
	}

	@Override
	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public String getDisplayName() {
		return title;
	}

}
