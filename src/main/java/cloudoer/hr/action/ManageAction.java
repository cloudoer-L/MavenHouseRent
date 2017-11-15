package cloudoer.hr.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import cloudoer.hr.entity.House;
import cloudoer.hr.entity.Street;
import cloudoer.hr.entity.Type;
import cloudoer.hr.entity.Users;
import cloudoer.hr.service.HouseService;
import cloudoer.hr.service.OtherService;


@Controller
public class ManageAction {

	@Resource(name = "houseService")
	private HouseService houseService;
	@Resource(name = "otherService")
	private OtherService otherService;
	
	private House house;

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}
	
	public String addUI(){
		return "success";
	}
	
	public String showUI(){
		System.out.println(ServletActionContext.getRequest().getParameter("houseId"));
		Integer houseId = Integer.parseInt(ServletActionContext.getRequest().getParameter("houseId"));
		ServletActionContext.getRequest().setAttribute("houseId", houseId);
		return "success";
	}
	
	public String updateUI(){
		Integer houseId = Integer.parseInt(ServletActionContext.getRequest().getParameter("houseId"));
		ServletActionContext.getRequest().setAttribute("houseId", houseId);
		return "success";
	}
	
	private void ajaxJson(Object object) throws IOException{
		//解决hibernate关系死循环
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setIgnoreDefaultExcludes(false); //设置默认忽略 
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//设置循环策略为忽略    解决json最头疼的问题 死循环
		jsonConfig.setExcludes(new String[] {"streets"});//此处是亮点，只要将所需忽略字段加到数组中即可
		
		JSONArray jsonArray = JSONArray.fromObject(object,jsonConfig);
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		ServletActionContext.getResponse().getWriter().print(jsonArray);
	}
	
	public String getHouseAll() throws IOException{
		List<House> list = houseService.getAll();
		ajaxJson(list);
		return null;
	}
	
	public String getHousePage() throws IOException{
		int pageNo = Integer.parseInt(ServletActionContext.getRequest().getParameter("pageNo"));
		String streetId = ServletActionContext.getRequest().getParameter("streetId");
		String typeId = ServletActionContext.getRequest().getParameter("typeId");
		Type type = null;
		Street street = null;
		System.out.println(streetId);
		try {
			type = otherService.getType(Integer.parseInt(typeId));
		} catch (Exception e) {
			type = null;
		}
		try {
			street = otherService.getStreet(Integer.parseInt(streetId));
		} catch (Exception e) {
			street = null;
		}
		System.out.println(street);
		List<House> list = houseService.getByPage(pageNo, 2, type, street);
		ajaxJson(list);
		return null;
	}
	
	public String addHouse() throws IOException{
		Users user = (Users) ServletActionContext.getRequest().getSession().getAttribute("user");
		house.setUser(user);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		String date = dateFormat.format(new Date());
		house.setPubdate(date);
		Integer typeId = Integer.parseInt(ServletActionContext.getRequest().getParameter("typeId"));
		Integer streetId = Integer.parseInt(ServletActionContext.getRequest().getParameter("streetId"));
		Type type = otherService.getType(typeId);
		Street street = otherService.getStreet(streetId);
		house.setType(type);
		house.setStreet(street);
		Integer houseId = houseService.add(house);
		if (houseId == null || houseId <= 0){
			return "failure";
		}else {
			return "success";
		}
	}
	
	public String updateHouse(){
		Users user = (Users) ServletActionContext.getRequest().getSession().getAttribute("user");
		house.setUser(user);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		String date = dateFormat.format(new Date());
		house.setPubdate(date);
		Integer typeId = Integer.parseInt(ServletActionContext.getRequest().getParameter("typeId"));
		Integer streetId = Integer.parseInt(ServletActionContext.getRequest().getParameter("streetId"));
		Type type = otherService.getType(typeId);
		Street street = otherService.getStreet(streetId);
		house.setType(type);
		house.setStreet(street);
		houseService.update(house);
		return "success";
	}
	
	public String getHouseById() throws IOException{
		Integer houseId = Integer.parseInt(ServletActionContext.getRequest().getParameter("houseId"));
		House house = houseService.getHouse(houseId);
		ajaxJson(house);
		return null;
	}
	
	public String deleteHouse() throws IOException{
		Integer houseId = Integer.parseInt(ServletActionContext.getRequest().getParameter("houseId"));
		houseService.delete(houseId);
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		ServletActionContext.getResponse().getWriter().print("删除成功");
		return null;
	}
	
	public String getHouseCount() throws IOException{
		String streetId = ServletActionContext.getRequest().getParameter("streetId");
		String typeId = ServletActionContext.getRequest().getParameter("typeId");
		Type type = null;
		Street street = null;
		System.out.println(streetId);
		try {
			type = otherService.getType(Integer.parseInt(typeId));
		} catch (Exception e) {
			type = null;
		}
		try {
			street = otherService.getStreet(Integer.parseInt(streetId));
		} catch (Exception e) {
			street = null;
		}
		int pageNo = houseService.getCount(type,street)/2;
		pageNo = houseService.getCount(type,street)%2==0?pageNo:(pageNo+1);
		ServletActionContext.getResponse().getWriter().print(pageNo);
		return null;
	}
}
