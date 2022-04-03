import java.io.IOException;

public class Dijkstra {
	
	DirectedGraph graph;
	double negInfinity = Double.NEGATIVE_INFINITY;
	double posInfinity = Double.POSITIVE_INFINITY;
	
	public Dijkstra(String filename) throws IOException {
		graph = new DirectedGraph(filename);
	}

}
