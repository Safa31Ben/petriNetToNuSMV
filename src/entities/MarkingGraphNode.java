package entities;

import java.util.Map;

public class MarkingGraphNode {
	private Marking marking;
	private Map<Marking, Transition> outputMarking;

	public MarkingGraphNode(Marking marking, Map<Marking, Transition> outputMarking) {
		this.marking = marking;
		this.outputMarking = outputMarking;
	}

	public Marking getMarking() {
		return marking;
	}

	public Map<Marking, Transition> getOutputMarking() {
		return outputMarking;
	}

}
