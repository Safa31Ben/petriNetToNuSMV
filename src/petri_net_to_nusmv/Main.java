package petri_net_to_nusmv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import entities.PetriNet;
import entities.Place;
import entities.Transition;

/* 
 * entry: Petri Net
 * result: NuSMV code
 */
public class Main {

	public static void main(String[] args) {
		/*
		 * 1st: from Petri Net to Marking Graph
		 * 2nd: from Marking Graph to NuSMV code
		 */

		/* Mini project exemple */
		ArrayList<Place> places = new ArrayList<>();
		places.add(new Place("P0", 1));
		places.add(new Place("P1", 2));
		places.add(new Place("P2", 0));

		ArrayList<Transition> transitions = new ArrayList<>();
		Map<Place, Integer> inputPlacesT0 = new HashMap<>();
		inputPlacesT0.put(places.get(0), 1);
		inputPlacesT0.put(places.get(1), 2);
		Map<Place, Integer> outputPlacesT0 = new HashMap<>();
		outputPlacesT0.put(places.get(0), 1);
		outputPlacesT0.put(places.get(2), 2);
		transitions.add(new Transition(0, inputPlacesT0, outputPlacesT0));

		Map<Place, Integer> inputPlacesT1 = new HashMap<>();
		inputPlacesT1.put(places.get(2), 1);
		Map<Place, Integer> outputPlacesT1 = new HashMap<>();
		outputPlacesT1.put(places.get(1), 1);
		transitions.add(new Transition(1, inputPlacesT1, outputPlacesT1));

		PetriNet petriNet = new PetriNet(places, transitions);
		NuSMVCode nusmv = new NuSMVCode(new MarkingGraph(petriNet));
		System.out.println(nusmv.tonusmv());

	}

}
