import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class Dijkstra {
	
	DirectedGraph graph;
	double negInfinity = Double.NEGATIVE_INFINITY;
	double posInfinity = Double.POSITIVE_INFINITY;
	Map<Integer, ArrayList<Route>> routeList;
	ArrayList<Integer> finalRouteList;
	double distTo[];
	
	public Dijkstra(String filename) throws IOException {
		graph = new DirectedGraph(filename);
	}
	
	public void DijkstraAlgo(int start, int stop) {
		Map<Integer, ArrayList<Edge>> adjList = graph.adjLists;
		distTo = new double[graph.maxId];
		Queue<Integer> pq = new PriorityQueue<>(Comparator.comparing(integer -> distTo[integer]));
		routeList = new HashMap<Integer, ArrayList<Route>>();
		
		for(int i = 0; i < graph.maxId; i++)
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
		
		boolean completedVertex[] = new boolean[graph.maxId];
		
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
						ArrayList<Route> tmpList = routeList.get(vertex);
						if(tmpList == null)
						{
							tmpList = new ArrayList<Route>();
						}
						
						Route route = new Route(vertex, connectingVertex);
						tmpList.add(route);
						routeList.put(vertex, tmpList);
					}
				
					pq.remove(connectingVertex);
					pq.add(connectingVertex);
				}
			}
			
			completedVertex[vertex] = true;
		}
	}

	public void PrintRoute(int bus_stop1, int bus_stop2) {
		
		finalRouteList = new ArrayList<Integer>();
        int curStop = bus_stop2;
        finalRouteList.add(curStop);
        int finalStop = bus_stop1;
        while(curStop != finalStop)
        {
       	 curStop = getRoutes(curStop);
        }
        
        for(int i = finalRouteList.size() - 1; i >= 0; i--)
        {
        	System.out.println("Route: " + finalRouteList.get(i));
        }
		
	}

	private int getRoutes(int curStop) {
		Map<Integer, ArrayList<Route>> routeList = getRouteList();
        for(int key: routeList.keySet())
        {
        	ArrayList<Route> a = routeList.get(key);
       	 	for(Route r: a)
       	 	{
       		 
       	 		if(curStop == r.connectingVertex)
       	 		{
       	 			curStop = r.vertex;
       	 			finalRouteList.add(curStop);
       	 		}
       	 	}
        }
        
        return curStop;
	}
	
	private Map<Integer, ArrayList<Route>> getRouteList() {
		return routeList;
	}

	public void PrintCost(int stop) {
		double cost = distTo[stop];
		System.out.println("Cost: " + cost);
	}
}