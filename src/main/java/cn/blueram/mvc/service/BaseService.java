package cn.blueram.mvc.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import cn.blueram.mvc.dao.model.BaseModel;
import cn.blueram.mvc.dao.mybatis.mapper.BaseMapper;

public class BaseService<T> {

	private BaseMapper<T> mapper;
	
	public BaseMapper<T> getMapper() throws Exception{
		return mapper;
	}
	
	/**
	 * 插入记录
	 * @return
	 * @throws Exception
	 */
	public void insert(T t) throws Exception{
		getMapper().insert(t);
	}
	
	/**
	 * 修改记录
	 * @return
	 * @throws Exception
	 */
	public void update(T t) throws Exception{
		getMapper().update(t);
	}
	
	/**
	 * 修改记录，至修改不为空的字段
	 * @param t
	 * @throws Exception
	 */
	public void updateBySelective(T t) throws Exception{
		getMapper().updateBySelective(t);
	}
	
	/**
	 * 根据id删除记录
	 * @param ids
	 * @throws Exception
	 */
	public void delete(Long... ids) throws Exception{
		if(ids == null || ids.length< 1){
			return ;
		}
		for(Long id : ids){
			getMapper().delete(id);
		}
	}
	
	/**
	 * 根据id查找记录记录，返回单条记录
	 * @param Id
	 * @return
	 * @throws Exception
	 */
	public T selectById(Long id) throws Exception{
		return getMapper().selectById(id);
	}
	
	/**
	 * 根据model查询总数
	 * @param Id
	 * @return
	 * @throws Exception
	 */
	public Integer selectByModelCount(BaseModel model) throws Exception{
		return getMapper().selectByModelCount(model);
	}
	
	/**
	 * 根据map查询总数
	 * @param Id
	 * @return
	 * @throws Exception
	 */
	public Integer selectByMapCount(Map  map) throws Exception{
		return getMapper().selectByMapCount(map);
	}
	
	/**
	 * 根据map查询list记录，分页,默认10条
	 * @param Id
	 * @return
	 * @throws Exception
	 */
	public List<T> selectByModel(BaseModel model) throws Exception{
		Integer rowCount = selectByModelCount(model);
		model.getNavigate().setRowCount(rowCount);
		return getMapper().selectByModel(model);
	}
	
	/**
	 * 根据map查询list记录，不分页。
	 * @param Id
	 * @return
	 * @throws Exception
	 */
	public List<T> selectByMap(Map  map) throws Exception{
		return getMapper().selectByMap(map);
	}
	
}
