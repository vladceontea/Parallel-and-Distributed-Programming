
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph {
    private int verticesNumber;
    private Map<Integer, Set<Integer>> vertices;

    public Graph(int verticesNumber) {
        this.verticesNumber = verticesNumber;

        vertices = new HashMap<>();
        for (int node = 0; node < verticesNumber; node++) {
            vertices.put(node, new HashSet<>());
        }
    }

    public int getVerticesNumber(){
        return verticesNumber;
    }

    public void addEdge(int startVertex, int endVertex) {
        vertices.get(startVertex).add(endVertex);
        vertices.get(endVertex).add(startVertex);
    }

    public boolean checkEdge(int fromVertex, int toVertex) {
        return vertices.get(fromVertex).contains(toVertex);
    }

    public String toString(){
        return this.vertices.toString();
    }
}