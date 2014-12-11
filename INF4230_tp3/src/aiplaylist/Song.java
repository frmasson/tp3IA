package aiplaylist;

import java.util.Collection;
import java.util.Map;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;

public class Song implements Item {

	public static final String TITLE = "title";
	private String id;
	private String title = null;
	private Map<String, String> features;

	public Song(String title, Map<String, String> features2) {
		this.id = title;
		this.title = title;
		this.features = features2;
	}

	public Song(String s) {
		this.id = s;
	}

	@Override
	public Map<String, String> getFeatures() {
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
		String result = title == null ? id : title;

		String author = features.get(Metadata.AUTHOR);
		if (author != null) {
			result += " - " + author;
		}
		return result;
	}

	@Override
	public int compareTo(Item o) {
		return this.id.compareTo(o.getId());
	}

	@Override
	public String toString() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Song) {
			return id.equals(((Song) o).getId());
		}
		return false;

	}

}
