package knotHash.model;

import java.util.LinkedList;

import lombok.Getter;

@Getter
public class Knot {
	private KnotElement first;
	private final int size;

	public Knot(LinkedList<Integer> list) {
		KnotElement current = null;
		this.size = list.size();

		for (final Integer i : list) {
			final KnotElement newElement = new KnotElement(i);

			if (this.first == null) {
				this.first = newElement;
			} else {
				current.setNext(newElement);
				newElement.setPrevious(current);
			}

			current = newElement;
		}

		current.setNext(this.first);
		this.first.setPrevious(current);
	}

	public int size() {
		return this.size;
	}

	public void reverse(int start, int end) {
		final int num = end - start - 1;
		if (num < 1) {
			return;
		}

		final KnotElement first = this.get(start);
		final KnotElement last = this.get(start + num);
		final KnotElement current = first.getNext();

		final int firstIndex = this.reverseMiddleNodes(last, current);
		this.replaceFirst(num, first, last, firstIndex);

		if (this.first == first) {
			this.first = last;
		} else if (this.first == last) {
			this.first = first;
		}

		this.handleEdges(num, first, last);

	}

	private void handleEdges(final int num, final KnotElement first, final KnotElement last) {
		if (num < this.size - 1) {
			first.getPrevious().setNext(last);
			last.getNext().setPrevious(first);

			final KnotElement tmp = first.getPrevious();
			first.setPrevious(first.getNext());
			first.setNext(last.getNext());
			last.setNext(last.getPrevious());
			last.setPrevious(tmp);
		} else {
			first.reverse();
			last.reverse();
		}
	}

	private int reverseMiddleNodes(final KnotElement last, KnotElement current) {
		int firstIndex = 0;
		int i = 1;
		while (current != last) {
			if (this.first == current) {
				firstIndex = i;
			}
			i++;

			current.reverse();
			current = current.getPrevious();
		}
		return firstIndex;
	}

	private void replaceFirst(final int num, final KnotElement first, final KnotElement last, int firstIndex) {
		KnotElement current;
		int i = 1;
		current = first.getNext();
		while (current != last) {
			if (num - i == firstIndex) {
				this.first = current;
				break;
			}
			i++;
			current = current.getPrevious();
		}
	}

	public KnotElement get(int id) {
		KnotElement current = this.first;
		for (int i = 0; i < id % this.size; i++) {
			current = current.getNext();
		}

		return current;
	}
}
