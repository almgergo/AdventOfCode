package knotHash.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KnotElement {

	private KnotElement previous;
	private KnotElement next;
	private Integer value;

	public KnotElement(int value) {
		this.value = value;
	}

	public void reverse() {
		final KnotElement tmp = this.next;
		this.next = this.previous;
		this.previous = tmp;
	}

	@Override
	public String toString() {
		return this.value.toString();
	}
}
