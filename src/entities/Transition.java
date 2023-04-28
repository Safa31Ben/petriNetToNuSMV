package entities;

import java.util.Map;

public class Transition {

	private int id;
	private Map<Place, Integer> inputPlaces;
	// list of Places with number of token for crossing
	private Map<Place, Integer> outputPlaces;

	// list of Places with number of token products
	public Transition(int id, Map<Place, Integer> inputPlaces, Map<Place, Integer> outputPlaces) {
		this.id = id;
		this.inputPlaces = inputPlaces;
		this.outputPlaces = outputPlaces;
	}

	@Override
	public String toString() {
		return "T" + id;
	}

	public Map<Place, Integer> getInputPlaces() {
		return inputPlaces;
	}

	public Map<Place, Integer> getOutputPlaces() {
		return outputPlaces;
	}

}
