package meetup.ws.rest;

import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import meetup.ws.services.RSVPService;

/**
 * Controller responsible for generating the services rest of our application.
 * 
 * @author Isabel
 */
@RestController
public class RestServices {
	
	static Logger log = Logger.getLogger(RestServices.class);

	@Autowired
	private RSVPService rsvpService;

	/**
	 * Service that allows us to obtain the number of groups near the location
	 * provided.
	 * 
	 * @param lat
	 *            Latitude of the reference point.
	 * @param lon
	 *            Longitude of the reference point.
	 * @return The number of groups near the location.
	 */
	@GetMapping(path = "/near", produces = MediaType.APPLICATION_JSON)
	public int getNearGroups(double lat, double lon) {
		log.info("/near lat="+lat+"lon="+lon);
		return rsvpService.getNearGroups(lat, lon);
	}

	/**
	 * Service that allows us to obtain key-value pairs of the ranking of cities
	 * with the largest number of attendees to the event given a date.
	 * 
	 * @param date
	 *            Date of the events
	 * @return key-value pairs with the name of the city and the number of
	 *         attendees.
	 */
	@GetMapping(path = "/topCities", produces = MediaType.APPLICATION_JSON)
	public Map<String, Integer> getTopCitiesWithMostPersonsInDate(String date) {
		log.info("/topCities date"+date);
		return rsvpService.getTopCities(date);
	}

}
