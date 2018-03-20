package meetup.ws.services;

import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jayway.restassured.path.json.JsonPath;

import meetup.ws.model.RSVP;
import meetup.ws.repositories.RSVPRepository;

@Service
public class RSVPServiceImpl implements RSVPService {

	@Autowired
	private RSVPRepository rsvpRepository;

	final String uri = "http://stream.meetup.com/2/rsvps?since_count=10";

	@Override
	public boolean saveRsvp() {
		System.out.println("servicio rsvp");

		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(uri, String.class);

		RSVP rsvp = new RSVP();
		rsvp.setCity(JsonPath.from(result).get("group_city"));
		rsvp.setCountry(JsonPath.from(result).get("group_country"));
		rsvp.setState(JsonPath.from(result).get("group_state"));
		rsvp.setLat(JsonPath.from(result).get("group_lat"));
		rsvp.setLon(JsonPath.from(result).get("group_lon"));
		rsvp.setDate(new Date(Long.parseLong(JsonPath.from(result).get("event.time"))));
		rsvpRepository.save(rsvp);

		return false;
	}

	@Override
	public List<String> getNearGroups(double lat, double lon) {

		return null;
	}

	@Override
	public Map<String, Integer> getTopCities(Date date) {
		Map<String, Integer> numPersonas = rsvpRepository.findCityAndIdByDateGroupByCity(date);
		return numPersonas.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue,
						LinkedHashMap::new));
		// return numPersonas.entrySet().stream().sorted(Map.Entry.<String,
		// Integer>comparingByValue().reversed()) ;
	}

}
