package meetup.ws.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Model with the data that we will need to save from the rsvp messages.
 * @author Isabel
 */
@Document(collection = "rsvp")
public class RSVP {

	@Id
	private int id;

	//@DateTimeFormat(iso = ISO.DATE_TIME , pattern="yyyy-MM-dd HH:mm:ss")
	private String date;

	private double lat;

	private double lon;

	private String city;
	
	private String country;
	
	private String state;
	
	public RSVP(){}

	public int getId() {
		return id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
