package petri_net_to_nusmv;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import entities.Marking;
import entities.MarkingGraphNode;
import entities.Place;

public class NuSMVCode {

	private MarkingGraph markingGraph;
	private Marking initMarking;

	public NuSMVCode(MarkingGraph markingGraph) {
		this.markingGraph = markingGraph;
		markingGraph.calculateOutputNodes();
		initMarking = (markingGraph.getNodes().get(0)).getMarking();
		ArrayList<MarkingGraphNode> nodes = (ArrayList<MarkingGraphNode>) markingGraph.getNodes();
		for (MarkingGraphNode node : nodes) {
			for (Iterator<Marking> mark = node.getOutputMarking().keySet().iterator(); mark.hasNext();) {
				Object keymark = mark.next();
				System.out.println(node.getMarking().toString() + " --"
						+ (node.getOutputMarking().get(keymark)).toString() + "--> " + keymark);
			}
		}

		System.out.println("--------------------------------------\n");
	}

	public String tonusmv() {
		StringBuilder code = new StringBuilder();
		code.append("MODULE main\n");
		code.append("VAR\n");
		code.append("\tstate : {");

		// Define MarkingGraphNode variables
		for (MarkingGraphNode state : markingGraph.getNodes()) {
			code.append(state.getMarking().getName() + ", ");
		}
		code.delete(code.length() - 2, code.length());
		code.append("};\n");

		// place bounded

		Map<Place, Integer> places = initMarking.getTokens();

		for (MarkingGraphNode state : markingGraph.getNodes()) {

			for (Iterator<Place> place = state.getMarking().getTokens().keySet().iterator(); place.hasNext();) {
				Object key = place.next();
				if (state.getMarking().getTokens().get(key) > places.get(key))
					places.replace((Place) key, state.getMarking().getTokens().get(key));
			}
		}
		for (Iterator<Place> place = places.keySet().iterator(); place.hasNext();) {
			Object key = place.next();

			code.append("\t" + ((Place) key).toString() + " : 0.." + places.get(key) + ";\n");
		}
		code.append("ASSIGN\n");
		code.append("\tinit(state) := " + initMarking.getName() + ";\n");
		code.append("\tnext(state) := case\n");

		for (MarkingGraphNode statei : markingGraph.getNodes()) {
			if (!statei.getOutputMarking().isEmpty()) {
				code.append("\t\tstate = " + statei.getMarking().getName() + " : {");
				for (Iterator<Marking> marking = statei.getOutputMarking().keySet().iterator(); marking.hasNext();) {
					Object key = marking.next();
					code.append(((Marking) key).getName() + ", ");
				}
				code.delete(code.length() - 2, code.length());
				code.append("};\n");
			}
		}
		code.append("\t\tTRUE : state;\n");
		code.append("\tesac;\n");

		for (Iterator<Place> place = initMarking.getTokens().keySet().iterator(); place.hasNext();) {
			Object key = place.next();
			code.append("\t" + ((Place) key).toString() + " := case \n");
			for (MarkingGraphNode statej : markingGraph.getNodes()) {
				if (statej.getMarking().getTokens().get(key) > 0)
					code.append("\t\t state = " + statej.getMarking().getName() + " : "
							+ statej.getMarking().getTokens().get(key) + ";\n");
			}
			code.append("\t\tTRUE : 0;\n\tesac;\n");
		}

		code.append("FAIRNESS running\n");
		return code.toString();
	}

}
