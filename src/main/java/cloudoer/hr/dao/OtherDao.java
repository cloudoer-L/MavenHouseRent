package cloudoer.hr.dao;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import cloudoer.hr.entity.District;
import cloudoer.hr.entity.Street;
import cloudoer.hr.entity.Type;

/**
 * ����District��Stree��Type
 * @author cloudoer
 *
 */
@Repository
public class OtherDao {

	@Resource(name = "sessionFactory")
	private SessionFactory factory;
	
	
	public List<District> getDistricts(){
		String queryString = "from District";
		Query queryObject = factory.getCurrentSession().createQuery(queryString);
		return queryObject.list();
	}
	
	public District getDistrict(Integer districtId){
		return (District)factory.getCurrentSession().get(District.class, districtId);
	}
	
	public List<Type> getTypes(){
		String queryString = "from Type";
		Query queryObject = factory.getCurrentSession().createQuery(queryString);
		return queryObject.list();
	}
	
	public Type getType(Integer typeId){
		return (Type)factory.getCurrentSession().get(Type.class, typeId);
	}
	
	public Set<Street> getStreets(District district){
		String queryString = "from District where id=?";
		Query queryObject = factory.getCurrentSession().createQuery(queryString);
		queryObject.setInteger(0, district.getId());
		return ((District)queryObject.list().get(0)).getStreets();
	}
	
	public Street getStreet(Integer streetId){
		return (Street)factory.getCurrentSession().get(Street.class, streetId);
	}
}
