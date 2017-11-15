package cloudoer.hr.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cloudoer.hr.dao.HouseDao;
import cloudoer.hr.entity.House;
import cloudoer.hr.entity.Street;
import cloudoer.hr.entity.Type;

@Service
public class HouseService {

	@Resource(name = "houseDao")
	private HouseDao houseDao;
	
	@Transactional
	public List<House> getAll(){
		return houseDao.getAll();
	}
	
	@Transactional
	public List<House> getByPage(int pageNo, int pageSize){
		return houseDao.getByPage(pageNo, pageSize);
	}
	
	@Transactional
	public List<House> getByPage(int pageNo, int pageSize, Type type, Street street){
		return houseDao.getByPage(pageNo, pageSize, type, street);
	}
	
	@Transactional
	public Integer add(House house){
		return houseDao.save(house);
	}
	
	@Transactional
	public void update (House house){
		houseDao.update(house);
	}
	
	@Transactional
	public void delete (Integer houseId){
		House house = houseDao.getHouse(houseId);
		houseDao.delete(house);
	}
	
	@Transactional
	public House getHouse(Integer houseId){
		return houseDao.getHouse(houseId);
	}
	
	@Transactional
	public Integer getCount(Type type, Street street){
		return houseDao.getCount(type, street);
	}

	@Test
	public void test(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("bean-base.xml");
		HouseService houseService = (HouseService)ac.getBean("houseService");
		List<House> list = houseService.getByPage(1, 2);
		for (House h : list){
			System.out.println(h);
		}
	}
}
