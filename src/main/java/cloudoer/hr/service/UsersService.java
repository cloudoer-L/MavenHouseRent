package cloudoer.hr.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cloudoer.hr.dao.UsersDao;
import cloudoer.hr.entity.Users;

@Service
public class UsersService {

	@Resource(name = "usersDao")
	private UsersDao usersDao;
	
	@Transactional
	public Users login(Users users){
		return usersDao.login(users);
	}
	
}
