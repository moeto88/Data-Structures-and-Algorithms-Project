import java.io.IOException;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);
		Stop stop = new Stop("stops.txt");
		DirectedGraph graph = new DirectedGraph("stop_times.txt");
		boolean exit = false;
		while(!exit)
		{
			System.out.println("Type in 1 if you want to find a shortest paths between 2 bus stops"
					+ "\n        2 if you want to search for bus stops"
					+ "\n        3 if you want to search for all trips with a given arrrival time"
					+ "\n        0 if you want to exit the programme");
			String eventString = scanner.next();
			int event;
			if(eventString.matches("^[0-9]+$"))
			{
				event = Integer.valueOf(eventString);
			}
			else
			{
				event = 100;
			}
			switch(event) {
			case 0:
				System.out.println("See you!!");
				exit = true;
				break;
				
			case 1:
				System.out.println("Type in 2 bus stop IDs separated by a space");
				String input1 = scanner.next();
				String input2 = scanner.next();
				
				if(input1.matches("^[0-9]+$") && input2.matches("^[0-9]+$"))
				{
					int bus_stop1 = Integer.valueOf(input1);
					int bus_stop2 = Integer.valueOf(input2);
					
					Dijkstra algo = new Dijkstra(graph, stop, bus_stop1, bus_stop2);
					if(algo.dijkstraAlgo() == true)
					{
						algo.printRoute();
						algo.printCost();
					}
					else
					{
						System.out.println("There is no bus stop IDs you typed in\n");
					}	
				}
				else
				{
					System.out.println("Please type in valid bus stop IDs --like 1887 1888\n");
				}
				
				
				break;
				
			case 2:
				System.out.println("Type in a name or characters to search for bus stops");
				String toSearch = scanner.next();
				if(toSearch.matches("^[A-Z ]+$"))
				{
					TSTTree tst = new TSTTree(graph, stop, toSearch);
					tst.get();
					tst.print();
				}
				else
				{
					System.out.println("Please type in valid name or characters -- it should be UPPERCASE like HASTINGS\n");
				}
				
				break;
				
			case 3:
				System.out.println("Type in an arrival time as hh:mm:ss");
				String arrival_time = scanner.next();
				if(arrival_time.matches("^[0-9:]+$"))
				{
					Trip trip = new Trip(arrival_time, graph);
					if(trip.makeTripListOfArrivalTime() == true)
					{
						trip.printTripDetails();
					}
					else
					{
						System.out.println("There is no such an arrival time you typed in\n");
					}
				}
				else
				{
					System.out.println("Please type in a valid arrival time -- like 19:00:00\n");
				}
				break;
				
			default:
				System.out.println("Error: Please type 1, 2, 3, or 0");
			}
		}
		scanner.close();
	}
}
