import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class Dijkstra {
	
	DirectedGraph graph;
	double negInfinity = Double.NEGATIVE_INFINITY;
	double posInfinity = Double.POSITIVE_INFINITY;
	
	public Dijkstra(String filename) throws IOException {
		graph = new DirectedGraph(filename);
	}
	
	public double[] DijkstraAlgo(int start) {
		Map<Integer, ArrayList<Edge>> adjList = graph.adjLists;
		double distTo[] = new double[graph.numOfVertices];
		Queue<Integer> pq = new PriorityQueue<>(Comparator.comparing(integer -> distTo[integer]));
		
		for(int i = 0; i < graph.numOfVertices; i++)
		{
			if(i == start)
			{
				ArrayList<Edge> keyList = adjList.get(start);
				for(Edge edge: keyList)
				{
					if(start == edge.adjVertex)
					{
						distTo[i] = edge.weight;
						break;
					}
					else
					{
						distTo[i] = 0;
					}
				}
			}
			else
			{
				distTo[i] = posInfinity;
			}
		}
		
		pq.add(start);
		
		boolean completedVertex[] = new boolean[graph.numOfVertices];
		
		while(pq.isEmpty() != true)
		{
			int vertex = pq.remove();
			
			
			ArrayList<Edge> keyList = adjList.get(vertex);
			if(keyList == null)
			{
				keyList = new ArrayList<Edge>();
			}
			
			for(Edge edge: keyList)
			{
				int connectingVertex = edge.adjVertex;
				
				if(completedVertex[connectingVertex] == false)
				{
					double updatedWeight = distTo[vertex] + edge.weight;

					if(updatedWeight < distTo[connectingVertex])
					{
						distTo[connectingVertex] = updatedWeight;
					}
				
					pq.remove(connectingVertex);
					pq.add(connectingVertex);
				}
			}
			
			completedVertex[vertex] = true;
		}
		
		return distTo;
	}

}
