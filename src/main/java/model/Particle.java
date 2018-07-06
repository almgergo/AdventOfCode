package model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Particle {

	protected int id;
	protected List<Particle> neighbours;
	protected Vector3 pos;
	protected Vector3 vel;
	protected Vector3 acc;

	protected boolean toDestroy = false;

	public Particle(final String line, final int id) {
		this.id = id;

		final String parts[] = line.replaceAll("[p=<>va ]", "").split(",");

		int i = 0;
		this.pos = new Vector3(Long.parseLong(parts[i++]), Long.parseLong(parts[i++]), Long.parseLong(parts[i++]));
		this.vel = new Vector3(Long.parseLong(parts[i++]), Long.parseLong(parts[i++]), Long.parseLong(parts[i++]));
		this.acc = new Vector3(Long.parseLong(parts[i++]), Long.parseLong(parts[i++]), Long.parseLong(parts[i++]));
	}

	public void advance() {
		this.vel.move(this.acc);
		this.pos.move(this.vel);
	}

	public void processNeighbours() {
		this.neighbours.removeIf(n -> !this.keepNeighbour(n));
	}

	public boolean keepNeighbour(final Particle other) {
		if (this.gotCloser(other)) {
			return true;
		} else if (this.deltaVDecreased(other)) {
			return true;
		}

		return false;
	}

	private boolean deltaVDecreased(final Particle other) {
		return other.getVel().getDelta(this.getVel()) > other.nextVelocity().getDelta(this.nextVelocity());
	}

	public boolean gotCloser(final Particle other) {
		final long currentDistance = other.getPos().getDelta(this.getPos());

		if (currentDistance == 0) {
			this.toDestroy = true;
			other.toDestroy = true;
		}
		return currentDistance > other.nextPosition().getDelta(this.nextPosition());
	}

	public Vector3 nextPosition() {
		final Vector3 newVel = this.nextVelocity();

		return this.pos.add(newVel);
	}

	public Vector3 nextVelocity() {
		return this.vel.add(this.acc);
	}

	public boolean equals(final Particle other) {
		return other.id == this.id;
	}

	@Override
	public String toString() {
		return "[id: " + this.id + ", neighbourcount: " + this.neighbours.size() + ", " + this.pos + ", " + this.vel
				+ ", " + this.acc + "]";
	}

}
