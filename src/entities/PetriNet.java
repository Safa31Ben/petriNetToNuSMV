package entities;

import java.util.ArrayList;
import java.util.List;

public class PetriNet {

	private ArrayList<Place> places;
	private ArrayList<Transition> transitions;

	public PetriNet(List<Place> places, List<Transition> transitions) {
		this.places = (ArrayList<Place>) places;
		this.transitions = (ArrayList<Transition>) transitions;
	}

	public List<Place> getPlaces() {
		return places;
	}

	public List<Transition> getTransitions() {
		return transitions;
	}

}
