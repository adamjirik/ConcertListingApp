package application.model;

import java.sql.Date;

public class Concert {

	private int id;
	private String artist;
	private String supporting;
	private String venue;
	private String city;
	private double cost;
	private Date date;
	private String genre;
	
	public Concert() {
		
	}
	
	public Concert(String artist, String supporting, String venue, String city, double cost, Date date, String genre) {
		super();
		this.artist = artist;
		this.supporting = supporting;
		this.venue = venue;
		this.city = city;
		this.cost = cost;
		this.date = date;
		this.genre = genre;
	}
	public Concert(int id, String artist, String supporting, String venue, String city, double cost, Date date,
			String genre) {
		super();
		this.id = id;
		this.artist = artist;
		this.supporting = supporting;
		this.venue = venue;
		this.city = city;
		this.cost = cost;
		this.date = date;
		this.genre = genre;
	}
	@Override
	public String toString() {
		return "Concert [id=" + id + ", artist=" + artist + ", supporting=" + supporting + ", venue=" + venue
				+ ", city=" + city + ", cost=" + cost + ", date=" + date + ", genre=" + genre + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getSupporting() {
		return supporting;
	}
	public void setSupporting(String supporting) {
		this.supporting = supporting;
	}
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}

	
	
	
}
