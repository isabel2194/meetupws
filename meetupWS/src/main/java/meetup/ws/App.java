package meetup.ws;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

//import meetup.ws.listeners.ContextListener;

@SpringBootApplication
@EnableScheduling
public class App /*extends SpringBootServletInitializer*/ {

	/*@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);

		servletContext.addListener(new ContextListener());
	}*/

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);

	}

}
