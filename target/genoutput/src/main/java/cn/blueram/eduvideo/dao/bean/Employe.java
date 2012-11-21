package cn.blueram.eduvideo.dao.bean;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.blueram.mvc.dao.bean.*;
/**
 * 
 * @author renjunjie
 *
 */
public class Employe extends BaseBean {
	
	private final static Logger log= LoggerFactory.getLogger(Employe.class);

	
		private Long id;//   主键id	private String name;//   姓名	private Integer sex;//   性别 0=保密，1=男，2=女	private Integer age;//   年龄	private String remark;//   备注	public Long getId() {	    return this.id;	}	public void setId(Long id) {	    this.id=id;	}	public String getName() {	    return this.name;	}	public void setName(String name) {	    this.name=name;	}	public Integer getSex() {	    return this.sex;	}	public void setSex(Integer sex) {	    this.sex=sex;	}	public Integer getAge() {	    return this.age;	}	public void setAge(Integer age) {	    this.age=age;	}	public String getRemark() {	    return this.remark;	}	public void setRemark(String remark) {	    this.remark=remark;	}
}
