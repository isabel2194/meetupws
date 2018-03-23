package meetup.ws.repositories;

import java.util.Map;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import meetup.ws.model.RSVP;

@Repository
public interface RSVPRepository extends MongoRepository<RSVP, Integer> {
	
	
	/**
	 * In sql: SELECT city, count(id) as numPersonas FROM rsvp WHERE date=? GROUP BY city")
	 */
	@Query("[{$group:{_id:'$city', count:{$sum: 1}, date:'$date'}}], {date:?}")
	public Map<String,Integer> findCityAndIdByDateGroupByCity(String date);

}
