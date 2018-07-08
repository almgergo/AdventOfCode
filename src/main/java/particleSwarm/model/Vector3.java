package particleSwarm.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vector3 {

	private long x;
	private long y;
	private long z;

	public Vector3(final long x, final long y, final long z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void move(final Vector3 other) {
		this.x += other.x;
		this.y += other.y;
		this.z += other.z;
	}

	public Vector3 add(final Vector3 other) {
		return new Vector3(other.x + this.x, other.y + this.y, other.z + this.z);
	}

	public long getDeltaValue(final Vector3 other) {
		return Math.abs(other.x - this.x) + Math.abs(other.y - this.y) + Math.abs(other.z - this.z);
	}

	public Vector3 getDeltaVector(final Vector3 other) {
		return new Vector3(other.x - this.x, other.y - this.y, other.z - this.z);
	}

	public long abs() {
		return Math.abs(this.x) + Math.abs(this.y) + Math.abs(this.z);
	}

	@Override
	public String toString() {
		return "[" + this.x + ", " + this.y + ", " + this.z + "]";
	}

	public Vector3 normalized() {
		return new Vector3(Long.signum(this.x), Long.signum(this.y), Long.signum(this.z));
	}

	public boolean equals(Vector3 other) {
		return this.x == other.x && this.y == other.y && this.z == other.z;
	}

}
