package particleSwarm.model;

import java.util.List;
import java.util.function.Function;

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

	protected boolean destroyed = false;
	protected boolean hasNeighbours = false;

	public Particle(final String line, final int id) {
		this.id = id;

		final String parts[] = line.replaceAll("[p=<>va ]", "").split(",");

		int i = 0;
		this.pos = new Vector3(Long.parseLong(parts[i++]), Long.parseLong(parts[i++]), Long.parseLong(parts[i++]));
		this.vel = new Vector3(Long.parseLong(parts[i++]), Long.parseLong(parts[i++]), Long.parseLong(parts[i++]));
		this.acc = new Vector3(Long.parseLong(parts[i++]), Long.parseLong(parts[i++]), Long.parseLong(parts[i++]));

		// System.out.println(this.pos.toString() + "\t" + this.vel.toString() + "\t" +
		// this.acc.toString());
	}

	/* ----------- Init ----------- */

	public void init() {
		this.hasNeighbours = false;
	}

	/* ----------- Neighbours ----------- */

	public void processNeighbours() {
		this.neighbours.removeIf(n -> {
			this.isCollision(n);
			return this.removeNeighbour(n);
		});
		this.hasNeighbours = this.hasNeighbours || !this.neighbours.isEmpty();
	}

	public boolean removeNeighbour(final Particle other) {
		if (!this.canMeet(other) || other.isDestroyed()) {
			return true;
		} else {
			other.hasNeighbours = true;
			return false;
		}
	}

	private void isCollision(Particle other) {
		if (other.getPos().getDeltaValue(this.getPos()) == 0) {
			this.destroyed = true;
			other.destroyed = true;
		}
	}

	public boolean canMeet(final Particle other) {
		final Vector3 dPosition = other.getPos().getDeltaVector(this.getPos()).normalized();
		final Vector3 dVelocity = other.getVel().getDeltaVector(this.getVel()).normalized();
		final Vector3 dAcceleration = other.getAcc().getDeltaVector(this.getAcc()).normalized();

		return this.closingDistanceOnAxis(dPosition, dVelocity, dAcceleration, Vector3::getX)
				&& this.closingDistanceOnAxis(dPosition, dVelocity, dAcceleration, Vector3::getY)
				&& this.closingDistanceOnAxis(dPosition, dVelocity, dAcceleration, Vector3::getZ);
	}

	private boolean closingDistanceOnAxis(final Vector3 dPosition, final Vector3 dVelocity, final Vector3 dAcceleration,
			Function<Vector3, Long> axis) {
		return axis.apply(dPosition) == 0 || (axis.apply(dPosition) * axis.apply(dVelocity) < 0)
				|| (axis.apply(dPosition) * axis.apply(dAcceleration) < 0);
	}

	/* ----------- Advance ----------- */

	public void advance() {
		this.vel.move(this.acc);
		this.pos.move(this.vel);
	}

	/* ----------- Utility ----------- */

	public boolean equals(final Particle other) {
		return other.id == this.id;
	}

	@Override
	public String toString() {
		return "[id: " + this.id + ", neighbourcount: " + this.neighbours.size() + ", destroyed: " + this.destroyed
				+ ", hasNeighbours: " + this.hasNeighbours + ", " + this.pos + ", " + this.vel + ", " + this.acc + "]";
	}

}
