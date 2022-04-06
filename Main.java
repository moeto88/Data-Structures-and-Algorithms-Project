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
					+ "\n        2 if you want to search for a bus top"
					+ "\n        3 if you want to search for all trips with a given arrrival time"
					+ "\n        0 if you want to exit the programme");
			int event = scanner.nextInt();
			switch(event) {
			case 0:
				System.out.println("See you!!");
				exit = true;
				break;
				
			case 1:
				System.out.println("Type in 2 bus stop IDs separated by a space");
				int bus_stop1 = scanner.nextInt();
				int bus_stop2 = scanner.nextInt();
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
				break;
				
			case 2:
				break;
				
			case 3:
				System.out.println("Type in an arrival time as hh:mm:ss");
				String arrival_time = scanner.next();
				Trip trip = new Trip(arrival_time, graph);
				if(trip.makeTripListOfArrivalTime() == true)
				{
					trip.printTripDetails();
				}
				else
				{
					System.out.println("There is no such an arrival time you typed in\n");
				}
				break;
				
			default:
				System.out.println("Error: Please type 1, 2, 3, or 0");
			}
		}
		scanner.close();
	}
}
