package meetup.ws.services;

import java.util.Date;
import java.util.List;

public interface RSVPService {
	
	public boolean saveRsvp();
	
	public List<String> getNearGroups(double lat, double lon);
	
	public List<String> getTopCities(Date date);

}
