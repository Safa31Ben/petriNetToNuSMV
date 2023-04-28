package petri_net_to_nusmv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import entities.Marking;
import entities.MarkingGraphNode;
import entities.PetriNet;
import entities.Place;
import entities.Transition;

public class MarkingGraph {

	private PetriNet petriNet;
	private ArrayList<MarkingGraphNode> nodes;

	public MarkingGraph(PetriNet petriNet) {
		this.petriNet = petriNet;
		Map<Place, Integer> tokens = new HashMap<>();
		for (Place place : petriNet.getPlaces()) {
			tokens.put(place, place.getTokens());
		}

		nodes = new ArrayList<>();
		Map<Marking, Transition> outputMarking = new HashMap<>();
		MarkingGraphNode node = new MarkingGraphNode(new Marking(0, tokens), outputMarking);
		nodes.add(new Marking(0, tokens).getId(), node);
	}

	public void calculateOutputNodes() {
		ArrayList<MarkingGraphNode> tempNodes = new ArrayList<>();
		int nodesSize = nodes.size();
		for (MarkingGraphNode node : nodes) {
			Map<Marking, Transition> outputMarking = node.getOutputMarking();
			Marking marking = node.getMarking();

			for (Transition transition : petriNet.getTransitions()) {
				if (crossingOrNot(marking, transition)) {
					// calculate newMarking
					Map<Place, Integer> newTokens = calculateNewMarking(marking, transition);

					// check if newMarking exist
					if (!checkIfNewMarkingExist(nodes, transition, newTokens, outputMarking)
							&& !checkIfNewMarkingExist(tempNodes, transition, newTokens, outputMarking)) {
						Marking newMarking = new Marking(nodesSize, newTokens);
						nodesSize += 1;
						outputMarking.put(newMarking, transition);
						new MarkingGraphNode(newMarking, outputMarking);
						tempNodes.add(new MarkingGraphNode(newMarking, new HashMap<>()));
					}

				}
			}
		}
		for (MarkingGraphNode tempNode : tempNodes) {
			nodes.add(tempNode);
		}
		if (!tempNodes.isEmpty())
			calculateOutputNodes();

	}

	public boolean crossingOrNot(Marking marking, Transition transition) {
		// check if crossing or not
		boolean crossing = true;
		for (Iterator<Place> placeCondition = transition.getInputPlaces().keySet().iterator(); placeCondition
				.hasNext();) {
			Object key = placeCondition.next();
			if (transition.getInputPlaces().get(key) > (marking.getTokens().get(key)))
				crossing = false;
			if (!crossing)
				break;
		}
		return crossing;
	}

	public Map<Place, Integer> calculateNewMarking(Marking marking, Transition transition) {
		// calculate newMarking
		Map<Place, Integer> newTokens = new HashMap<>();
		for (Iterator<Place> place = marking.getTokens().keySet().iterator(); place.hasNext();) {
			Object key = place.next();
			if (transition.getInputPlaces().get(key) != null)
				newTokens.put((Place) key, (marking.getTokens().get(key) - transition.getInputPlaces().get(key)));

			if (transition.getOutputPlaces().get(key) != null)
				newTokens.put((Place) key, (marking.getTokens().get(key) + transition.getOutputPlaces().get(key)));

			if (transition.getOutputPlaces().get(key) == null && transition.getInputPlaces().get(key) == null)
				newTokens.put((Place) key, marking.getTokens().get(key));
		}
		return newTokens;
	}

	public boolean checkIfNewMarkingExist(List<MarkingGraphNode> nodes, Transition transition,
			Map<Place, Integer> newTokens, Map<Marking, Transition> outputMarking) {
		// check if newMarking exist
		boolean newMarkingExist = false;
		for (MarkingGraphNode node : nodes) {
			Marking nodeCheckMarking = node.getMarking();
			if (newTokens.equals(nodeCheckMarking.getTokens())) {
				newMarkingExist = true;
				outputMarking.put(nodeCheckMarking, transition);
				break;
			}
		}
		return newMarkingExist;
	}

	public List<MarkingGraphNode> getNodes() {
		return nodes;
	}
}
