import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class Trip {
	String arrival_time;
	DirectedGraph graph;
	Map<Integer, TripDetails> tripListsOfArrivalTimeWithTripId;
	int sortedList[];

	public Trip(String arrival_time, DirectedGraph graph) {
		this.arrival_time = arrival_time;
		this.graph = graph;
	}

	public boolean makeTripListOfArrivalTime() {
		int arrivalTime = Integer.valueOf(arrival_time.replaceAll("[^0-9]", ""));
		ArrayList<TripDetails> tripList = graph.tripLists.get(arrivalTime);
		if(tripList != null)
		{
			tripListsOfArrivalTimeWithTripId = new HashMap<Integer, TripDetails>();;
			int tmpList[] = new int[tripList.size()];
			int index = 0;
			for(TripDetails td: tripList)
			{
				tmpList[index] = td.trip_id;
				index++;
				
				TripDetails det = new TripDetails(td.trip_id, td.arraival_time, td.departure_time, td.stop_id,
						td.stop_sequence, td.stop_headsign, td.pickup_type, td.drop_off_type, td.shape_dist_traveled);
				tripListsOfArrivalTimeWithTripId.put(td.trip_id, det);
			}
			
			sortedList = mergeSortIterative(tmpList);
			return true;
		}
		else
		{
			return false;
		}
	}

	private int[] mergeSortIterative(int a[]) {
		int n = a.length;
    	int[] arr = new int[n];
    	
    	for(int i = 1; i < n; i = i * 2)
    	{
    		for(int lo = 0; lo < n - i; lo = lo + i * 2)
    		{
    			merge(a, arr, lo, lo + i - 1, Math.min(lo + i * 2 - 1, n - 1));
    		}
    	}
    	
    	return a;
	}

	private void merge(int[] a, int[] arr, int lo, int mid, int hi) {
    	for(int k = lo; k <= hi; k++)
    	{
    		arr[k] = a[k];
    	}
		int i = lo; //0
		int j = mid + 1; //1
		for(int k = lo; k <= hi; k++)
		{
			if(i > mid)
			{
				a[k] = arr[j++];
			}
			else if(j > hi)
			{
				a[k] = arr[i++];
			}
			else if(arr[j] < arr[i])
			{
				a[k] = arr[j++];
			}
			else
			{
				a[k] = arr[i++];
			}
		}
	}

	public void printTripDetails() {
		System.out.println("Arrival time: " + arrival_time);
		System.out.println("Full details of all trips starting from " + arrival_time + "\n");
		for(int i = 0; i < sortedList.length; i++)
		{
			TripDetails td = tripListsOfArrivalTimeWithTripId.get(sortedList[i]);
			System.out.println("   trip_id: " + td.trip_id);
			System.out.println("   departure_time: " + td.departure_time);
			System.out.println("   stop_id: " + td.stop_id);
			System.out.println("   stop_sequence: " + td.stop_sequence);
			System.out.println("   stop_headsign: " + td.stop_headsign);
			System.out.println("   pickup_type: " + td.pickup_type);
			System.out.println("   drop_off_type: " + td.drop_off_type);
			System.out.println("   shape_dist_traveled: " + td.shape_dist_traveled + "\n");
		}
	}
}
