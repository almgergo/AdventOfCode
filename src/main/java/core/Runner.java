package core;

import knotHash.logic.KnotHash;

public class Runner {

	public static void main(final String[] args) {

		// new Thread(new ParticleSwarm()).start();
		new Thread(new KnotHash()).start();

	}

}
