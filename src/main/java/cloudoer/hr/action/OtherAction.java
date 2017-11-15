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
		//���hibernate��ϵ��ѭ��
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setIgnoreDefaultExcludes(false); //����Ĭ�Ϻ��� 
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//����ѭ������Ϊ����    ���json��ͷ�۵����� ��ѭ��
		jsonConfig.setExcludes(new String[] {"streets"});//�˴������㣬ֻҪ����������ֶμӵ������м���
		
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
