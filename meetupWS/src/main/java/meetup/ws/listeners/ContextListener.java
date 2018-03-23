package meetup.ws.listeners;

import java.util.concurrent.CompletionStage;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import meetup.ws.services.RSVPService;

public class ContextListener implements ServletContextListener {
	
	static Logger log = Logger.getLogger(ContextListener.class);


	@Autowired
	private RSVPService rsvpService;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		log.info("Start contextListener.");
		ActorSystem system=ActorSystem.create();
		Materializer materializer = ActorMaterializer.create(system);

		CompletionStage<HttpResponse> responseFuture = Http.get(system)
				.singleRequest(HttpRequest.create("http://stream.meetup.com/2/rsvps"))
				.thenApplyAsync(response -> {rsvpService.saveRsvp(
						response.entity().toStrict(new Long(20), materializer).toString()); return response;});
		log.debug(responseFuture);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		log.info("Shutting down!");

	}

}
