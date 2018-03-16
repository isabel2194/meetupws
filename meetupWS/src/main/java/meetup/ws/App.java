package meetup.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import meetup.ws.services.RSVPServiceImpl;

@SpringBootApplication
@EnableBinding(Sink.class)
public class App {
	
	@Autowired
	RSVPServiceImpl rsvpService;

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);

	}

	@StreamListener(Sink.INPUT)
	public void saveRSVP() {
		rsvpService.saveRsvp();
	}

}
