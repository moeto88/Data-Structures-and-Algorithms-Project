
public class TripDetails {

	int trip_id;
	int arraival_time;
	int departure_time;
	int stop_id;
	int stop_sequence;
	String stop_headsign;
	int pickup_type;
	int drop_off_type;
	String shape_dist_traveled;

	public TripDetails(int trip_id, int arraival_time, int departure_time, int stop_id, int stop_sequence,
			String stop_headsign, int pickup_type, int drop_off_type, String shape_dist_traveled) {
		this.trip_id = trip_id;
		this.arraival_time = arraival_time;
		this.departure_time = departure_time;
		this.stop_id = stop_id;
		this.stop_sequence = stop_sequence;
		this.stop_headsign = stop_headsign;
		this.pickup_type = pickup_type;
		this.drop_off_type = drop_off_type;
		this.shape_dist_traveled = shape_dist_traveled;
	}

}
