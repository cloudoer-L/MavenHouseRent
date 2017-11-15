package cloudoer.hr.service;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cloudoer.hr.dao.HouseDao;
import cloudoer.hr.dao.OtherDao;
import cloudoer.hr.entity.District;
import cloudoer.hr.entity.Street;
import cloudoer.hr.entity.Type;

/**
 * ²Ù×÷District¡¢Stree¡¢Type
 * @author cloudoer
 *
 */
@Service
public class OtherService {
	
	@Resource(name = "otherDao")
	private OtherDao otherDao;
	
	@Transactional
	public List<District> getDistricts(){
		return otherDao.getDistricts();
	}
	
	@Transactional
	public District getDistrict(Integer districtId){
		return otherDao.getDistrict(districtId);
	}
	
	@Transactional
	public List<Type> getTypes(){
		return otherDao.getTypes();
	}
	
	@Transactional
	public Type getType(Integer typeId){
		return otherDao.getType(typeId);
	}
	
	@Transactional
	public Set<Street> getStreets(District district){
		return otherDao.getStreets(district);
	}
	
	@Transactional
	public Street getStreet(Integer streetId){
		return otherDao.getStreet(streetId);
	}
}
