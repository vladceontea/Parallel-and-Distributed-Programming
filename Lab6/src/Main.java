import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {


        Graph graph = new Graph(5);
        graph.addEdge(0,1);
        graph.addEdge(1,2);
        graph.addEdge(1,3);
        graph.addEdge(2,4);
        graph.addEdge(3,0);
        graph.addEdge(4,1);
        graph.addEdge(4,3);

        System.out.println(graph);

        double startTime = System.nanoTime();

        HamiltonianCycle finder = new HamiltonianCycle(graph);
        List<Integer> path = finder.startSearch();

        double endTime = System.nanoTime();

        if (path.isEmpty()){
            System.out.println("No solution found");
        }
        else{
            System.out.println(path);
        }

        double totalTime = endTime - startTime;
        System.out.println("Elapsed running time: " + totalTime);
    }
}