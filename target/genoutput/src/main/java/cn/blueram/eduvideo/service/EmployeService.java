package cn.blueram.eduvideo.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.blueram.eduvideo.dao.mybatis.mapper.EmployeMapper;
import cn.blueram.mvc.service.*;

/**
 * 
 * <br>
 * <b>功能：</b>EmployeService
 * @author renjunjie
 */
@Service("employeService")
public class EmployeService<T> extends BaseService<T> {
	
	private final static Logger log= LoggerFactory.getLogger(EmployeService.class);
	

	@Autowired
    private EmployeMapper<T> mapper;

		
	public EmployeMapper<T> getMapper() {
		return mapper;
	}

}
