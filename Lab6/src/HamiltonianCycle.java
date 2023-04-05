import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class HamiltonianCycle {
    public Graph graph;
    ExecutorService executor;
    Phaser phaser = new Phaser();

    public HamiltonianCycle(Graph graph) {
        this.graph = graph;
        executor = Executors.newFixedThreadPool(4);
    }

    public List<Integer> startSearch() throws InterruptedException {
        List<Integer> finalPath = new CopyOnWriteArrayList<>();
        List<Integer> path = new ArrayList<>();
        path.add(0);

        int phase = phaser.getPhase();

        phaser.register();
        search(0, path, finalPath);
        phaser.awaitAdvance(phase);

        executor.shutdown();
        executor.awaitTermination(50, TimeUnit.SECONDS);
        return finalPath;

    }

    public void search(int vertex, List<Integer> path, List<Integer> finalPath){

        if (!finalPath.isEmpty()) {
            phaser.arrive();
            return;
        }

        //check if valid solution - change "atomic" list if so
        if (path.size() == graph.getVerticesNumber()) {
            if (graph.checkEdge(path.get(path.size() - 1), 0)) {
                if (finalPath.isEmpty()) {
                    finalPath.addAll(path);
                }
                phaser.arrive();
                return;
            }
        }

        for (int node = 0; node < graph.getVerticesNumber(); node++) {
            if (graph.checkEdge(node, vertex) && !checkVisited(node, path)) {
                path.add(node);

                // copy the path so far into a new array that is used only locally (inside that thread)
                List<Integer> pathCopy = new ArrayList<>(path);
                int finalNode = node;
                phaser.register();
                executor.submit(() -> search(finalNode, pathCopy, finalPath));
                path.remove(path.size()-1);
            }
        }
        phaser.arrive();

    }

    public boolean checkVisited(int node, List<Integer> path) {
        for (var n : path)
            if (n == node)
                return true;
        return false;
    }
}