package cloudoer.hr.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import cloudoer.hr.entity.Users;

@Repository
public class UsersDao {

	@Resource(name = "sessionFactory")
	private SessionFactory factory;
	
	public List<Users> getAll(){
		String queryString = "from Users";
		Query queryObject = factory.getCurrentSession().createQuery(queryString);
		return queryObject.list();
	}
	
	public Users login(Users users){
		String queryString = "from Users where username=? and password=?";
		Users user = null;
		Query queryObject = factory.getCurrentSession().createQuery(queryString);
		queryObject.setString(0, users.getUsername());
		queryObject.setString(1, users.getPassword());
		List<Users> list = queryObject.list();
		if(list != null && list.size() > 0){
			user = list.get(0);
		}
		return user;
	}
}
