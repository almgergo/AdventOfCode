package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class World {
	public Map<Integer, Particle> particleMap;
	public Map<Integer, Particle> particleMapNext;

	public World(final Map<Integer, Particle> particleMap) {
		this.particleMap = particleMap;
	}

	public void progressWorld() {
		this.particleMapNext = new HashMap<>();

		for (final Entry<Integer, Particle> entry : this.particleMap.entrySet()) {
			this.particleMapNext.put(entry.getKey(), entry.getValue().progress());
		}
	}

}
