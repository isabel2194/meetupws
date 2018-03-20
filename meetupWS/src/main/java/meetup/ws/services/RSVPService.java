package meetup.ws.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface RSVPService {
	
	public boolean saveRsvp();
	
	public List<String> getNearGroups(double lat, double lon);
	
	public Map<String, Integer> getTopCities(Date date);

}
