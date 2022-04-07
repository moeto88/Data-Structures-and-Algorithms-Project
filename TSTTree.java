import java.util.ArrayList;

public class TSTTree {
	DirectedGraph graph;
	Stop stop;
	String toSearch;
	TSTNode root;
	ArrayList<String> stopNameArray;
	StringBuilder sb;


	public TSTTree(DirectedGraph graph, Stop stop, String toSearch) {
		this.graph = graph;
		this.stop = stop;
		this.toSearch = toSearch;
		this.root = null;
		makeStopNameArray(stop);
		putStopNameToTST();
	}


	private void putStopNameToTST() {
		
		for(String stopName: stopNameArray)
		{
			putStopName(stopName);
		}
		
		
		/*
		putStopName("HASTINGS ST FS HOLDOM AVE- WB ");
		 * 
		 */


	}


	private void putStopName(String stopName) {
		root = put(root, stopName.toCharArray(), 0);

	}


	private TSTNode put(TSTNode x, char[] stopName, int index) {
		if(x == null)
		{
			x = new TSTNode(stopName[index]);
		}

		if(stopName[index] == x.ch)
		{
			if(index + 1 >= stopName.length)
			{
				x.completed = true;
			}
			else
			{
				x.middle = put(x.middle, stopName, index + 1);	
			}
		}
		else if(stopName[index] < x.ch)
		{
			x.left = put(x.left, stopName, index);
		}
		else
		{
			x.right = put(x.right, stopName, index);
		}

		return x;
	}


	private void makeStopNameArray(Stop stop) {
		stopNameArray = new ArrayList<String>();
		for(int key: stop.stopLists.keySet())
		{
			StopDetals std = stop.stopLists.get(key);
			stopNameArray.add(std.stop_name);
		}
	}


	public void get() {
		TSTNode x = get(root, toSearch.toCharArray(), 0);
		sb = new StringBuilder();
		findAllBusStops(x, "", sb, toSearch);
		System.out.println(sb.toString());
	}


	private void findAllBusStops(TSTNode x, String tmp, StringBuilder sb, String stopName) {
		if (x != null) 
		{
			findAllBusStops(x.left, tmp, sb, stopName);
			tmp = tmp + x.ch;

			if (x.completed) 
			{
				if (stopName.length() == 1) 
				{
					if (stopName.equals(tmp.substring(0, 1)))
					{
						sb.append(stopName + tmp.substring(1) + "\n");
					}
				} 
				else
				{
					sb.append(stopName + tmp.substring(1) + "\n");
				}

			}
			
			findAllBusStops(x.middle, tmp, sb, stopName);
			tmp = tmp.substring(0, tmp.length() - 1);
			findAllBusStops(x.right, tmp, sb, stopName);
		}
	}


	private TSTNode get(TSTNode x, char[] stopName, int index) {
		if (x == null)
		{
			return null;
		}

		if(stopName[index] == x.ch)
		{
			if (index == stopName.length - 1)
			{
				return x;
			}
			else
			{
				return get(x.middle, stopName, index + 1);
			}
		}
		else if(stopName[index] < x.ch)
		{
			return get(x.left, stopName, index);
		}
		else
		{
			return get(x.right, stopName, index);
		}	
	}


	public void print() {
		if(sb.toString().equals(""))
		{
			System.out.println("There are no bus stops matching your input\n");
		}
		else
		{
			System.out.println(sb.toString());
		}
	}
}
