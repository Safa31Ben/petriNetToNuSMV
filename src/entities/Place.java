package entities;

public class Place {

	private String id;
	private int tokens;

	public Place(String id, int tokens) {
		this.id = id;
		this.tokens = tokens;
	}

	@Override
	public String toString() {
		return id;
	}

	public int getTokens() {
		return tokens;
	}

}
