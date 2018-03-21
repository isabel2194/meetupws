package meetup.ws.rest;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import meetup.ws.services.RSVPService;

@RestController
public class RestServices {
	final ActorSystem system = ActorSystem.create();


	@Autowired
	private RSVPService rsvpService;

	@GetMapping(path = "/near", produces = MediaType.APPLICATION_JSON)
	public List<String> getNearGroups(double lat, double lon) {
		system.stop(ActorRef.noSender());
		return rsvpService.getNearGroups(lat, lon);
	}

	@GetMapping(path = "/topCities", produces = MediaType.APPLICATION_JSON)
	public Map<String, Integer> getTopCitiesWithMostPersonsInDate(Date date) {
		system.stop(ActorRef.noSender());
		return rsvpService.getTopCities(date);
	}

	@GetMapping(path = "/rsvp")
	public void getRSVP() {
		System.out.println("recurso rsvp");
				
		CompletionStage<HttpResponse> responseFuture =
		  Http.get(system)
		      .singleRequest(HttpRequest.create("http://stream.meetup.com/2/rsvps"));
		System.out.println(responseFuture);
		//rsvpService.saveRsvp(responseFuture);
	}

}
