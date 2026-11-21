package galadom.domain.galaxy;

import java.util.List;

public record GalaxyGraph(List<List<Integer>> neighbors) {
    public GalaxyGraph {
        neighbors = neighbors.stream().map(List::copyOf).toList();
    }

    public int starCount() {
        return neighbors.size();
    }
}