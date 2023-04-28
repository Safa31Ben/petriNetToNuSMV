package entities;

import java.util.Map;

public class Marking {

	private int id;
	private Map<Place, Integer> tokens;

	public Marking(int id, Map<Place, Integer> tokens) {
		this.id = id;
		this.tokens = tokens;
	}

	public String getName() {
		return "M" + id;
	}

	@Override
	public String toString() {
		return "M" + id + tokens;
	}

	public int getId() {
		return id;
	}

	public Map<Place, Integer> getTokens() {
		return tokens;
	}

}
