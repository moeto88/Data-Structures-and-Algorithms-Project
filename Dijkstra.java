import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class Dijkstra {
	double negInfinity = Double.NEGATIVE_INFINITY;
	double posInfinity = Double.POSITIVE_INFINITY;
	Map<Integer, ArrayList<Route>> routeList;
	ArrayList<Integer> finalRouteList;
	double costTo[];
	DirectedGraph graph;
	Stop stop;
	int startID;
	int stopID;

	public Dijkstra(DirectedGraph graph, Stop stop, int bus_stop1, int bus_stop2){
		this.graph =  graph;
		this.stop = stop;
		this.startID = bus_stop1;
		this.stopID = bus_stop2;
	}

	public boolean dijkstraAlgo() {
		if(findStopIDs())
		{
			Map<Integer, ArrayList<Edge>> adjList = graph.adjLists;
			costTo = new double[graph.maxId];
			Queue<Integer> pq = new PriorityQueue<>(Comparator.comparing(integer -> costTo[integer]));
			routeList = new HashMap<Integer, ArrayList<Route>>();
			
			for(int i = 0; i < graph.maxId; i++)
			{
				if(i == startID)
				{
					ArrayList<Edge> keyList = adjList.get(startID);
					for(Edge edge: keyList)
					{
						if(startID == edge.adjVertex)
						{
							costTo[i] = edge.weight;
							break;
						}
						else
						{
							costTo[i] = 0;
						}
					}
				}
				else
				{
					costTo[i] = posInfinity;
				}
			}
			
			pq.add(startID);
			
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
						double updatedWeight = costTo[vertex] + edge.weight;
						
						if(updatedWeight < costTo[connectingVertex])
						{
							costTo[connectingVertex] = updatedWeight;
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
			
			return true;
		}
		else
		{
			return false;
		}
	}

	private boolean findStopIDs() {
		StopDetails isFound1 = stop.stopLists.get(startID);
		StopDetails isFound2 = stop.stopLists.get(stopID);
		
		if(isFound1 != null && isFound2 != null)
		{
			return true;
		}
		else
		{
			return false;	
		}
	}

	public void printRoute() {
		
		if(getCost() != 0)
		{
			finalRouteList = new ArrayList<Integer>();
			int curStop = stopID;
			finalRouteList.add(curStop);
			int finalStop = startID;
			while(curStop != finalStop)
			{
				curStop = getRoutes(curStop);
			}
			
			System.out.println("Route from " + startID + " to " + stopID + ": ");
			int index = 1;
			for(int i = finalRouteList.size() - 1; i >= 0; i--)
			{
				int stopID = finalRouteList.get(i);
				System.out.println(index + ": " + stopID);
				StopDetails det = stop.stopLists.get(stopID);
				System.out.println("   stop_code: " + det.stop_code);
				System.out.println("   stop_name: " + det.stop_name);
				System.out.println("   stop_desc: " + det.stop_desc);
				System.out.println("   stop_lat: " + det.stop_lat);
				System.out.println("   stop_lon: " + det.stop_lon);
				System.out.println("   zone_id: " + det.zone_id);
				System.out.println("   stop_url: " + det.stop_url);
				System.out.println("   location_type: " + det.location_type);
				System.out.println("   parent_station: " + det.parent_station + "\n");
				index++;
			}
		}
		else
		{
			System.out.println("There is no route from " + startID + " to " + stopID + "\n");
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

	public void printCost() {
		double cost = getCost();
		if(cost > 0)
		{
			System.out.println("The cost associated with a route from " + startID + " to " + stopID + " is " + cost + "\n");
		}
	}

	private double getCost() {
		
		Map<Integer, ArrayList<Edge>> a = graph.adjLists;
		for(int key: a.keySet())
		{
			ArrayList<Edge> b = a.get(key);
			for(Edge ed: b)
			{
				if(costTo[ed.vertex] == 0)
				{
					System.out.println(ed.vertex);
				}
					
			}
			
		}
		
		
		return costTo[stopID];
	}
}