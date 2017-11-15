package cloudoer.hr.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * House entity. @author MyEclipse Persistence Tools
 */

public class House implements java.io.Serializable {

	// Fields

	private Integer id;
	private String title;
	private String description;
	private Double price;
	private String pubdate;
	private Integer floorage;
	private String contact;
	
	private Street street;
	private Type type;
	private Users user;

	// Constructors

	/** default constructor */
	public House() {
	}

	/** minimal constructor */
	public House(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public House(Users user, Type type, String title,
			String description, Double price, String pubdate, Integer floorage,
			String contact, Street street) {
		this.title = title;
		this.description = description;
		this.price = price;
		this.pubdate = pubdate;
		this.floorage = floorage;
		this.contact = contact;
		this.user = user;
		this.type = type;
		this.street = street;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public Street getStreet() {
		return street;
	}

	public void setStreet(Street street) {
		this.street = street;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getPubdate() {
		return this.pubdate;
	}

	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}

	public Integer getFloorage() {
		return this.floorage;
	}

	public void setFloorage(Integer floorage) {
		this.floorage = floorage;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "House [id=" + id + ", title=" + title + ", description="
				+ description + ", price=" + price + ", pubdate=" + pubdate
				+ ", floorage=" + floorage + ", contact=" + contact
				+ ", street=" + street + ", type=" + type + ", user=" + user
				+ "]";
	}
	

}