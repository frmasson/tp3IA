package aiplaylist;

public class Feature {

	private String type;
	private Object value;

	public Feature(String type, Object value) {
		this.type = type;
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public Object getFeatureObject() {
		return value;
	}

}
