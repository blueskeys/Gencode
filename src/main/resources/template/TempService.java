package ${packagename}.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${packagename}.dao.mybatis.mapper.${className}Mapper;
import cn.blueram.mvc.service.*;

/**
 * 
 * <br>
 * <b>功能：</b>${className}Service
 * @author renjunjie
 */
@Service("$!{lowerName}Service")
public class ${className}Service<T> extends BaseService<T> {
	
	private final static Logger log= LoggerFactory.getLogger(${className}Service.class);
	

	@Autowired
    private ${className}Mapper<T> mapper;

		
	public ${className}Mapper<T> getMapper() {
		return mapper;
	}

}
