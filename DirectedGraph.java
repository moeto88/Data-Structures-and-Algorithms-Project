import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DirectedGraph {
	Map<String, ArrayList<Details>> tripLists;
	Map<Integer, ArrayList<Edge>> adjLists;
	ArrayList<StopIDWithTripID> tmpLists;
	int numOfVertices = 50000;

	public DirectedGraph(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		tripLists = new HashMap<String, ArrayList<Details>>();
		tmpLists = new ArrayList<StopIDWithTripID>();
		br.readLine();
		String curLine = br.readLine();
		
		
		while(curLine != null)
		{
			curLine = curLine.replace(", ", ",");
			String arrayForLine[] = curLine.split(",");
			int trip_id = Integer.valueOf(arrayForLine[0]);
			String arraival_time = arrayForLine[1];
			String departure_time = arrayForLine[2];
			int stop_id = Integer.valueOf(arrayForLine[3]);
			int stop_sequence = Integer.valueOf(arrayForLine[4]);
			String stop_headsign = arrayForLine[5];
			int pickup_type = Integer.valueOf(arrayForLine[6]);
			int drop_off_type = Integer.valueOf(arrayForLine[7]);
			String shape_dist_traveled;
			if(arrayForLine.length == 9)
			{
				shape_dist_traveled = arrayForLine[8];
			}
			else
			{
				shape_dist_traveled = "";
			}
			
			StopIDWithTripID id = new StopIDWithTripID(trip_id, stop_id);
			tmpLists.add(id);
			
			ArrayList<Details> list2 = tripLists.get(arraival_time);
			if(list2 == null)
			{
				list2 = new ArrayList<Details>();
			}
			
			Details det = new Details(trip_id, arraival_time, departure_time, stop_id,
					stop_sequence, stop_headsign, pickup_type, drop_off_type, shape_dist_traveled);
			list2.add(det);
			tripLists.put(arraival_time, list2);
			curLine = br.readLine();
		}
		br.close();
		
		adjLists = new HashMap<Integer, ArrayList<Edge>>();
		for(int i = 0; i < tmpLists.size() - 1; i++)
		{
			StopIDWithTripID id1 = tmpLists.get(i);
			i++;
			StopIDWithTripID id2 = tmpLists.get(i);
			
			if(id1.trip_id == id2.trip_id)
			{
				int vertex = id1.stop_id;
				int adjVertex = id2.stop_id;
				double weight = 1;
				ArrayList<Edge> list = adjLists.get(vertex);
				if(list == null)
				{
					list = new ArrayList<Edge>();
				}
				
				boolean foundAdjVertex = false;
				for(Edge edge: list)
				{
					if(adjVertex == edge.adjVertex)
					{
						foundAdjVertex = true;
					}
				}
				if(!foundAdjVertex)
				{
					Edge edge = new Edge(vertex, adjVertex, weight);
					list.add(edge);
					adjLists.put(vertex, list);
				}
			}
		}
		
		br = new BufferedReader(new FileReader("transfers.txt"));
		br.readLine();
		curLine = br.readLine();
		
		while(curLine != null)
		{
			String arrayForLine[] = curLine.split(",");
			int vertex = Integer.valueOf(arrayForLine[0]);
			int adjVertex = Integer.valueOf(arrayForLine[1]);
			int transfer_type = Integer.valueOf(arrayForLine[2]);
			double weight = 0;
			if(transfer_type == 0)
			{
				weight = 2;
			}
			else
			{
				double min_transfer_time = Double.valueOf(arrayForLine[3]);
				weight = min_transfer_time / 100;
			}
			
			ArrayList<Edge> list = adjLists.get(vertex);
			if(list == null)
			{
				list = new ArrayList<Edge>();
				
			}
			Edge edge = new Edge(vertex, adjVertex, weight);
			list.add(edge);
			adjLists.put(vertex, list);
			curLine = br.readLine();
		}
		
		br.close();
	}
}
