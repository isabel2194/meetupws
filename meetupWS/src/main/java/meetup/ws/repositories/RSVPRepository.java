package meetup.ws.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import meetup.ws.model.RSVP;

public interface RSVPRepository extends MongoRepository<RSVP, Integer> {

}
