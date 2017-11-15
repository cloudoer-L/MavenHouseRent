package cloudoer.hr.entity;

import java.util.Set;

/**
 * District entity. @author MyEclipse Persistence Tools
 */

public class District implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	
	
	private Set<Street> streets;

	// Constructors

	/** default constructor */
	public District() {
	}

	/** minimal constructor */
	public District(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public District(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Set<Street> getStreets() {
		return streets;
	}

	public void setStreets(Set<Street> streets) {
		this.streets = streets;
	}

	@Override
	public String toString() {
		return "District [id=" + id + ", name=" + name + "]";
	}
	

}