package cloudoer.hr.action;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import cloudoer.hr.entity.Users;
import cloudoer.hr.service.UsersService;

@Controller
public class LoginAction {

	@Resource(name = "usersService")
	private UsersService usersService;
	
	private Users users;
	
	
	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public String view(){
		return "success";
	}
	
	public String manage(){
		return "success";
	}
	
	public String customer(){
		return "success";
	}

	public String login(){
		Users user = usersService.login(users);
		if (user != null){
			ServletActionContext.getRequest().getSession().setAttribute("user", user);
			if ("0".equals(user.getIsadmin())){
				return "manage";
			}else {
				return "customer";
			}
		}else{
			return "login";
		}
	}
	
	
	
}
