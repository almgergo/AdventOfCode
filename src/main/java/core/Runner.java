package core;

import logic.ParticleSwarm;

public class Runner {

	public static void main(final String[] args) {

		final ParticleSwarm swarm = new ParticleSwarm();

		new Thread(swarm).start();

	}

}
