package meetup.ws.services;

import java.util.Map;

/**
 * Interface of the service layer
 * @author Isabel
 */
public interface RSVPService {
	
	public void saveRsvp(String result);
	
	public int getNearGroups(double lat, double lon);
	
	public Map<String, Integer> getTopCities(String date);

}
