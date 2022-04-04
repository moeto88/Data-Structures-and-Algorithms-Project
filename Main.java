import java.io.IOException;
import java.util.Scanner;

public class Main {

	
	
	public static void main(String[] args) throws IOException {
		
		Dijkstra algo = new Dijkstra("stop_times.txt");
		System.out.println("Type 2 bus stops ID divide by a space");
		Scanner scanner = new Scanner(System.in);
		int bus_stop1 = scanner.nextInt();
		int bus_stop2 = scanner.nextInt();
		algo.DijkstraAlgo(bus_stop1, bus_stop2);
		algo.PrintRoute(bus_stop1, bus_stop2);
		algo.PrintCost(bus_stop2);
	}

}
