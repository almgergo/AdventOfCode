package logic;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.Particle;

public class ParticleSwarm implements Runnable {

	@Override
	public void run() {
		final List<Particle> particlesToProgress = this.prepareParticleList("particles.dat");

		int it = 0;
		while (particlesToProgress.size() > 0) {
			this.progressSwarm(new LinkedList<>(particlesToProgress), it++);

			try {
				Thread.sleep(100L);
			} catch (final InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(particlesToProgress.size() + ", alive: "
				+ particlesToProgress.stream().filter(p -> !p.isToDestroy()).count());
	}

	public void progressSwarm(final List<Particle> particles, final int it) {

		particles.forEach(p -> p.processNeighbours());

		particles.removeIf(p -> p.isToDestroy());
		final List<Integer> toKeep = Stream
				.concat(particles.stream().map(p -> p.getId()),
						particles.stream().flatMap(p -> p.getNeighbours().stream().map(n -> n.getId())))
				.distinct().collect(Collectors.toList());

		particles.removeIf(p -> !toKeep.contains(p.getId()));

		particles.removeIf(p -> p.isToDestroy() || p.getNeighbours().isEmpty());
		particles.forEach(p -> p.advance());

		if (particles.size() < 500 && it % 10 == 0) {
			for (final Particle p : particles) {
				System.out.println(it + ", " + p.getId() + " --- " + p
						.getNeighbours().stream().map(n -> String.valueOf(n.getId())).collect(Collectors.joining(", "))
						+ " --- "
						+ p.getNeighbours().stream()
								.map(n -> String.valueOf(
										n.getPos().getDelta(p.getPos()) > n.nextPosition().getDelta(p.nextPosition())))
								.collect(Collectors.joining(", "))
						+ " --- "
						+ p.getNeighbours().stream()
								.map(n -> String.valueOf(n.getVel().getDelta(p.getVel())) + " "
										+ String.valueOf(n.nextVelocity().getDelta(p.nextVelocity())))
								.collect(Collectors.joining(", ")));

			}
			System.out.println("number of total neighbours: "
					+ particles.stream().map(p -> p.getNeighbours().size()).reduce((s1, s2) -> s1 + s2).orElse(0));
			System.out.println("number of particles: " + particles.size());
			// if (it > 10000) {
			// System.out.println(particles.get(0));
			// System.out.println(particles.get(0).getNeighbours().get(0));
			// System.out.println();
			// }
		}
		// System.out.println("number of total neighbours: "
		// + particles.stream().map(p -> p.getNeighbours().size()).reduce((s1, s2) -> s1
		// + s2).orElse(0));
		// System.out.println("number of particles: " + particles.size());
	}

	private List<Particle> prepareParticleList(final String fileName) {
		final List<Particle> particles = new LinkedList<>();

		this.readParticleList(fileName, particles);
		this.setNeighbours(particles);

		return particles;
	}

	private void readParticleList(final String fileName, final List<Particle> particleMap) {
		final ClassLoader classLoader = this.getClass().getClassLoader();
		final File file = new File(classLoader.getResource(fileName).getFile());

		try (Scanner scanner = new Scanner(file)) {

			int id = 0;
			while (scanner.hasNextLine()) {
				final String line = scanner.nextLine();

				particleMap.add(new Particle(line, id++));
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
