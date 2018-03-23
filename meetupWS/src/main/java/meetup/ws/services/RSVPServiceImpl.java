package meetup.ws.services;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jayway.restassured.path.json.JsonPath;

import meetup.ws.model.RSVP;
import meetup.ws.repositories.RSVPRepository;

/**
 * Service that is responsible for processing the data of the rsvp
 * @author Isabel
 */
@Service
public class RSVPServiceImpl implements RSVPService {
	
	static Logger log = Logger.getLogger(RSVPServiceImpl.class);

	@Autowired
	private RSVPRepository rsvpRepository;

	final String uri = "http://stream.meetup.com/2/rsvps?since_count=10";

	/**
	 * Save the rsvp messages of the meetup API.
	 * @param result the json message
	 */
	@Override
	public void saveRsvp(String result) {
		log.info("Guardando rsvp");
		log.info(result);
		RSVP rsvp = new RSVP();
		rsvp.setCity(JsonPath.from(result).get("group_city"));
		rsvp.setCountry(JsonPath.from(result).get("group_country"));
		rsvp.setState(JsonPath.from(result).get("group_state"));
		rsvp.setLat(JsonPath.from(result).get("group_lat"));
		rsvp.setLon(JsonPath.from(result).get("group_lon"));
		//rsvp.setDate(new Date(Long.parseLong(JsonPath.from(result).get("event.time"))));
		rsvp.setDate(JsonPath.from(result).get("event.time"));
		rsvpRepository.save(rsvp);
	}

	/**
	 * Calculate the number of groups near a point.
	 * 
	 * @param lat
	 *            Latitude of the reference point.
	 * @param lon
	 *            Longitude of the reference point.
	 * @return The number of groups near a point.
	 */
	@Override
	public int getNearGroups(double lat, double lon) {
		log.info("Obteniendo número de grupos cercanos.");
		List<RSVP> rsvps = rsvpRepository.findAll();
		int numGroups = 0;

		for (RSVP rsvp : rsvps) {
			if (calculateDistance(lat, lon, rsvp.getLat(), rsvp.getLon()) < 20) {
				numGroups++;
			}
		}

		return numGroups;
	}

	/**
	 * Given a date, get the top cities with the largest number of attendees to
	 * an event on that date.
	 * 
	 * @param date
	 *            Date of the event.
	 * @return Returns key value map with the name of each city as key and the
	 *         number of attendees as value.
	 */
	@Override
	public Map<String, Integer> getTopCities(String date) {
		log.info("Obteniendo el top de ciudades con mayor numero de asistentes.");
		Map<String, Integer> numPersonas = rsvpRepository.findCityAndIdByDateGroupByCity(date);
		return numPersonas.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue,
						LinkedHashMap::new));
	}

	/**
	 * Calculate the distance between two Cartesian points.
	 * 
	 * @param lat1
	 *            Latitude point 1
	 * @param lng1
	 *            Longitude point 1
	 * @param lat2
	 *            Latitude point 2
	 * @param lng2
	 *            Longitude point 2
	 * @return The distance between the points in kilometers.
	 */
	private double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
		double radioTierra = 6371;// kilometers
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double sindLat = Math.sin(dLat / 2);
		double sindLng = Math.sin(dLng / 2);
		double va1 = Math.pow(sindLat, 2)
				+ Math.pow(sindLng, 2) * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
		double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));
		double distancia = radioTierra * va2;

		return distancia;
	}

}
