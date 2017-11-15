package cloudoer.hr.action;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import cloudoer.hr.entity.District;
import cloudoer.hr.entity.Street;
import cloudoer.hr.entity.Type;
import cloudoer.hr.service.HouseService;
import cloudoer.hr.service.OtherService;


@Controller
public class OtherAction {

	@Resource(name = "otherService")
	private OtherService otherService;
	
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
	
	public String getDistricts() throws IOException{
		List<District> list = otherService.getDistricts();
		ajaxJson(list);
		return null;
	}
	
	public String getTypes() throws IOException{
		List<Type> list = otherService.getTypes();
		ajaxJson(list);
		return null;
	}
	
	public String getSteets() throws IOException{
		int districtId = Integer.parseInt(ServletActionContext.getRequest().getParameter("districtId"));
		Set<Street> set = otherService.getStreets(new District(districtId));
		ajaxJson(set);
		return null;
	}
}
