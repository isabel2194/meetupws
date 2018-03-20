package meetup.ws.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.annotation.Autowired;

import meetup.ws.services.RSVPService;

public class ContextListener implements ServletContextListener{
	
	@Autowired
	private RSVPService rsvpService;

	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("en contextInitialized");
		rsvpService.saveRsvp();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		 System.out.println("Shutting down!");
		 
	}

}
