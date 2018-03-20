package meetup.ws.rest;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import meetup.ws.services.RSVPService;

@RestController
public class RestServices {
	
	@Autowired
	private RSVPService rsvpService;
	
	@GetMapping(path="/near",produces=MediaType.APPLICATION_JSON)
	public List<String> getNearGroups(double lat,double lon){
		return rsvpService.getNearGroups(lat, lon);
	}
	
	@GetMapping(path="/topCities",produces=MediaType.APPLICATION_JSON)
	public Map<String,Integer> getTopCitiesWithMostPersonsInDate(Date date){
		return rsvpService.getTopCities(date);
	}
	
	@GetMapping(path="/rsvp")
	public void getRSVP(){
		System.out.println("recurso rsvp");
		rsvpService.saveRsvp();
	}

}
