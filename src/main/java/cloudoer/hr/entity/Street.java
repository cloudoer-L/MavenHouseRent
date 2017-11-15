package cloudoer.hr.entity;

/**
 * Street entity. @author MyEclipse Persistence Tools
 */

public class Street implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	
	private District district;

	// Constructors

	/** default constructor */
	public Street() {
	}

	/** minimal constructor */
	public Street(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public Street(Integer id, String name, District district) {
		this.id = id;
		this.name = name;
		this.district = district;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
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


	@Override
	public String toString() {
		return "Street [id=" + id + ", name=" + name + ", district=" + district + "]";
	}
	

}