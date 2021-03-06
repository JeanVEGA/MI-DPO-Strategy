package cz.cvut.fit.mi_dpo.strategy.traversal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import cz.cvut.fit.mi_dpo.strategy.Matrix;

public abstract class Traversal {

	private Matrix matrix;

	private List<Integer> nodes;

	public Traversal(Matrix matrix) {
		this.matrix = matrix;
		nodes = new ArrayList<>(matrix.getSize() + 2);
		traverse();
	}

	protected final void traverse() {
		Deque<Integer> queue = newDeque();
		Integer root = 0;
		queue.add(root);
		getNodesOrder().add(root);

		doTraverse(queue);
	}

	abstract protected Deque<Integer> newDeque();

	abstract protected void doTraverse(Deque<Integer> queue);

	protected Matrix getMatrix() {
		return matrix;
	}

	protected List<Integer> getNodesOrder() {
		return nodes;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Integer node : getNodesOrder()) {
			sb.append(node);
			sb.append(" ");
		}

		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	protected Integer getUnvisitedChildNode(Integer peeked) {
		List<Integer> children = new ArrayList<>();

		for (int child = peeked; child < getMatrix().getSize(); child++) {
			if (isClosestChild(peeked, child)) {
				children.add(child);
			}
		}

		for (int child = peeked; child >= 0; child--) {
			if (isClosestChild(peeked, child)) {
				children.add(child);
			}
		}
		Collections.sort(children);
		return children.size() > 0 ? children.get(0) : null;
	}

	private boolean isClosestChild(Integer peeked, Integer child) {
		return getMatrix().hasEdge(peeked, child) && !getNodesOrder().contains(child);
	}

}
