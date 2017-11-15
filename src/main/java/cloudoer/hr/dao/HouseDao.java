package cloudoer.hr.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cloudoer.hr.entity.House;
import cloudoer.hr.entity.Street;
import cloudoer.hr.entity.Type;


@Repository
public class HouseDao {

	@Resource(name = "sessionFactory")
	private SessionFactory factory;
	
	public List<House> getAll(){
		String queryString = "from House";
		Query queryObject = factory.getCurrentSession().createQuery(queryString);
		return queryObject.list();
	}
	
	public List<House> getByPage(int pageNo, int pageSize){
		String queryString = "from House";
		Query queryObject = factory.getCurrentSession().createQuery(queryString);
		queryObject.setFirstResult((pageNo - 1) * pageSize);  
		queryObject.setMaxResults(pageSize);
		return queryObject.list();
	}
	
	public List<House> getByPage(int pageNo, int pageSize, Type type, Street street){
		Criteria criteria = factory.getCurrentSession().createCriteria(House.class);
		if (type != null){
			criteria.add(Restrictions.eq("type", type));
		}
		if (street != null){
			criteria.add(Restrictions.eq("street", street));
		}
		criteria.setFirstResult((pageNo - 1) * pageSize);  
		criteria.setMaxResults(pageSize);
		return criteria.list();
	}
	
	public Integer save(House house){
		return (Integer)factory.getCurrentSession().save(house);
	}
	
	public void update (House house){
		factory.getCurrentSession().update(house);
	}
	
	public void delete (House house){
		factory.getCurrentSession().delete(house);
	}
	
	public House getHouse(Integer houseId){
		return (House)factory.getCurrentSession().get(House.class, houseId);
	}
	
	public Integer getCount(Type type, Street street){
		Criteria criteria = factory.getCurrentSession().createCriteria(House.class);
		if (type != null){
			criteria.add(Restrictions.eq("type", type));
		}
		if (street != null){
			criteria.add(Restrictions.eq("street", street));
		}
		criteria.setProjection(Projections.rowCount()); 
		return Integer.parseInt(criteria.uniqueResult().toString());
	}
	
	public Integer getCount(){
		Query query = factory.getCurrentSession().createQuery("select count(id) from House");
		return ((Number)query.uniqueResult()).intValue();
	}
}
