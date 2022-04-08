import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//HAATHERSQUAREBAY1


public class TSTTree {
	DirectedGraph graph;
	Stop stop;
	String str;
	String toSearch;
	TSTNode root;
	ArrayList<String> stopNameArray;
	ArrayList<String> foundStopNameArray;
	Map<String, ArrayList<Integer>> spaceFinder;


	public TSTTree(DirectedGraph graph, Stop stop, String toSearch) {
		this.graph = graph;
		this.stop = stop;
		this.str = toSearch;
		this.toSearch = toSearch.replace(" ", "");
		this.root = null;
		makeStopNameArray(stop);
		putStopNameToTST();
	}


	private void putStopNameToTST() {
		for(String stopName: stopNameArray)
		{
			putStopName(stopName);
		}
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
		spaceFinder = new HashMap<String, ArrayList<Integer>>();
		for(int key: stop.stopLists.keySet())
		{	
			StopDetails std = stop.stopLists.get(key);
			String nonSpaceString = makeFormattedString(std.stop_name);
			stopNameArray.add(nonSpaceString);
		}
	}


	private String makeFormattedString(String stop_name) {
		ArrayList<Integer> spaceIndexList = new ArrayList<Integer>();
		int spaceIndex = -1;
		while(stop_name.indexOf(" ", spaceIndex + 1) != -1)
		{
			spaceIndex = stop_name.indexOf(" ", spaceIndex + 1);
			spaceIndexList.add(spaceIndex);
		}
		String nonSpaceString = stop_name.replace(" ", "");
		spaceFinder.put(nonSpaceString, spaceIndexList);
		return nonSpaceString;
	}


	public void get() {
		TSTNode x = get(root, toSearch.toCharArray(), 0);
		foundStopNameArray = new ArrayList<String>();
		findAllBusStops(x, "", toSearch);
	}


	private void findAllBusStops(TSTNode x, String tmp, String stopName) {
		if (x != null) 
		{
			findAllBusStops(x.left, tmp, stopName);
			tmp = tmp + x.ch;

			if (x.completed) 
			{
				if (stopName.length() == 1) 
				{
					if (stopName.equals(tmp.substring(0, 1)))
					{
						foundStopNameArray.add(stopName + tmp.substring(1));
					}
				} 
				else
				{
					foundStopNameArray.add(stopName + tmp.substring(1));
				}

			}
			
			findAllBusStops(x.middle, tmp, stopName);
			tmp = tmp.substring(0, tmp.length() - 1);
			findAllBusStops(x.right, tmp, stopName);
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
		if(foundStopNameArray.size() == 0)
		{
			System.out.println("There are no bus stops matching your input\n");
		}
		else
		{
			System.out.println("Here are bus stop names starting from " + str + ":");
			
			for(String stopName: foundStopNameArray)
			{
				StringBuilder sb = new StringBuilder();
				sb.append(stopName);
				ArrayList<Integer> space = spaceFinder.get(stopName);
				
				for(int index: space)
				{
					sb.insert(index, " ");
				}
				System.out.println("   " + sb.toString());
			}
			System.out.println();
		}
	}
}
