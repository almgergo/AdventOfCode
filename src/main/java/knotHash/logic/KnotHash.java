package knotHash.logic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

import knotHash.model.Knot;
import knotHash.model.KnotElement;

public class KnotHash implements Runnable {

	private static final String INPUT_FILE = "knotHash/input.txt";
	private static final int SIZE = 256;

	@Override
	public void run() {
		final Knot knot = this.createBaseList();
		Queue<Integer> lengths;

		final boolean part2 = true;
		if (part2) {
			lengths = this.readInputPart2(INPUT_FILE);
			final Queue<Integer> cache = new LinkedBlockingQueue<Integer>();
			cache.addAll(lengths);
			for (int i = 0; i < 63; i++)
				lengths.addAll(cache);

			System.out.println(lengths.size() + ", " + lengths.size() / 64);
		} else {
			lengths = this.readInputPart1(INPUT_FILE);
		}

		final int currentPosition = 0;
		final int skipSize = 0;
		this.reversePart(knot, lengths, currentPosition, skipSize);

		if (part2) {
			KnotElement current = knot.getFirst();
			final List<Integer> denseHash = new ArrayList<>();
			for (int i = 0; i < 256; i += 16) {

				int hash = 0;
				for (int j = 0; j < 16; j++) {
					hash = hash ^ current.getValue().intValue();
					// System.out.println(current.getValue());
					// System.out.println(hash);
					// System.out.println();
					current = current.getNext();
				}

				denseHash.add(hash);
			}

			denseHash.forEach(h -> System.out.println(h + " " + Integer.toHexString(h)));
			System.out.println(
					denseHash.stream().map(h -> String.valueOf(Integer.toHexString(h))).collect(Collectors.joining()));
		}

		// for (int i = 0; i < 2; i++) {
		// System.out.println(knot.get(i).getValue());
		// }
	}

	private void reversePart(Knot knot, Queue<Integer> lengths, int currentPosition, int skipSize) {

		if (lengths.peek() != null) {
			final int length = lengths.poll();
			knot.reverse(currentPosition, currentPosition + length);
			this.reversePart(knot, lengths, (currentPosition += (length + skipSize)) % knot.size(), skipSize + 1);
		}

	}

	private Knot createBaseList() {
		final LinkedList<Integer> knot = new LinkedList<>();
		for (int i = 0; i < SIZE; i++) {
			knot.add(i);
		}
		return new Knot(knot);
	}

	private Queue<Integer> readInputPart1(String fileName) {
		final Queue<Integer> inputs = new LinkedBlockingQueue<Integer>();

		final ClassLoader classLoader = this.getClass().getClassLoader();
		final File file = new File(classLoader.getResource(fileName).getFile());

		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				final String[] line = scanner.nextLine().split(",");

				for (int i = 0; i < line.length; i++) {
					inputs.add(Integer.parseInt(line[i]));
				}
			}
			scanner.close();

		} catch (final IOException e) {
			e.printStackTrace();
		}

		return inputs;
	}

	private Queue<Integer> readInputPart2(String fileName) {
		final Queue<Integer> inputs = new LinkedBlockingQueue<Integer>();

		final ClassLoader classLoader = this.getClass().getClassLoader();
		final File file = new File(classLoader.getResource(fileName).getFile());

		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {

				final String line = scanner.nextLine();

				for (int i = 0; i < line.length(); i++) {
					System.out.println((int) line.charAt(i));
					inputs.add(((int) line.charAt(i)));
				}

				inputs.addAll(Arrays.asList(17, 31, 73, 47, 23));

				// for (int i = 0; i < line.length; i++) {
				// inputs.add(Integer.parseInt(line[i]));
				// }
			}
			scanner.close();

		} catch (final IOException e) {
			e.printStackTrace();
		}

		return inputs;
	}
}
