package meetup.ws.actor;

import java.util.concurrent.CompletionStage;


import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import scala.concurrent.ExecutionContextExecutor;

import static akka.pattern.PatternsCS.pipe;

public class MeetupwsSupervisor extends AbstractActor{
	
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	  public static Props props() {
	    return Props.create(MeetupwsSupervisor.class);
	  }

	  @Override
	  public void preStart() {
	    log.info("IoT Application started");
	  }

	  @Override
	  public void postStop() {
	    log.info("IoT Application stopped");
	  }

	  
	  final Http http = Http.get(context().system());
      final ExecutionContextExecutor dispatcher = context().dispatcher();
      final Materializer materializer = ActorMaterializer.create(context());

      @Override
      public Receive createReceive() {
        return receiveBuilder()
          .match(String.class, url -> pipe(fetch(url), dispatcher).to(self()))
          .build();
      }

      CompletionStage<HttpResponse> fetch(String url) {
        return http.singleRequest(HttpRequest.create(url));
      }

}
