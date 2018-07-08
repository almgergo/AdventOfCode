package particleSwarm.logic;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import particleSwarm.model.Particle;

public class ParticleSwarm implements Runnable {

	private static final long N = 1000;

	@Override
	public void run() {
		System.out.println("Reading data...");
		final List<Particle> originalParticles = this.prepareParticleList("particleSwarm/particles.txt");

		final List<Particle> particlesToProgress = new LinkedList<Particle>(originalParticles);

		System.out.println("Processing data...");
		int it = 0;
		while (particlesToProgress.size() > 0) {
			this.progressSwarm(particlesToProgress, it++);
		}
		System.out.println(
				"iterations: " + it + ", alive: " + originalParticles.stream().filter(p -> !p.isDestroyed()).count());
	}

	public void progressSwarm(final List<Particle> particles, final int it) {
		particles.forEach(p -> p.init());

		particles.forEach(p -> p.processNeighbours());

		particles.removeIf(p -> p.isDestroyed() || !p.isHasNeighbours());

		particles.forEach(p -> p.advance());

		if (it % 10 == 0) {
			System.out.println("it: " + it + ", " + particles.size() + ", neighbours: "
					+ particles.stream().flatMap(p -> p.getNeighbours().stream()).count());
		}
	}

	private List<Particle> prepareParticleList(final String fileName) {
		final List<Particle> particles = new LinkedList<>();

		this.readParticleList(fileName, particles);
		this.setNeighbours(particles);

		return particles;
	}

	private void readParticleList(final String fileName, final List<Particle> particles) {
		final ClassLoader classLoader = this.getClass().getClassLoader();
		final File file = new File(classLoader.getResource(fileName).getFile());

		try (Scanner scanner = new Scanner(file)) {

			int id = 0;
			while (scanner.hasNextLine()) {
				final String line = scanner.nextLine();

				particles.add(new Particle(line, id++));
			}

			scanner.close();

		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	private void setNeighbours(final List<Particle> particles) {
		for (int i = 0; i < particles.size(); i++) {

			final List<Particle> neighbours = new LinkedList<Particle>(particles.subList(i + 1, particles.size()));

			particles.get(i).setNeighbours(neighbours);
		}

	}

}
