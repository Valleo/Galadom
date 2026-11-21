package galadom.domain.galaxy;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

public final class GalaxyGenerator {
    private static final double SECOND_NEIGHBOR_PROBABILITY = 0.9;

    private final RandomGenerator random;

    public GalaxyGenerator(RandomGenerator random) {
        this.random = random;
    }

    public GalaxyGraph generate(int starCount) {
        if (starCount < 2) {
            throw new IllegalArgumentException("Une galaxie doit contenir au moins deux étoiles.");
        }

        List<List<Integer>> neighbors = new ArrayList<>(starCount);
        neighbors.add(new ArrayList<>());
        neighbors.add(new ArrayList<>());
        connect(neighbors, 0, 1);

        for (int star = 2; star < starCount; star++) {
            neighbors.add(new ArrayList<>());
            List<Integer> candidates = new ArrayList<>(star);
            for (int existing = 0; existing < star; existing++) {
                candidates.add(existing);
            }

            int firstNeighbor = random.nextInt(candidates.size());
            connect(neighbors, star, candidates.remove(firstNeighbor));

            double probability = SECOND_NEIGHBOR_PROBABILITY;
            while (!candidates.isEmpty() && random.nextDouble() < probability) {
                int nextNeighbor = random.nextInt(candidates.size());
                connect(neighbors, star, candidates.remove(nextNeighbor));
                probability /= 2;
            }
        }
        return new GalaxyGraph(neighbors);
    }

    private static void connect(List<List<Integer>> neighbors, int first, int second) {
        neighbors.get(first).add(second);
        neighbors.get(second).add(first);
    }
}