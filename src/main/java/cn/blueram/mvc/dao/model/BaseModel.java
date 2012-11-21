package cn.blueram.mvc.dao.model;

import cn.blueram.utils.Navigate;

public class BaseModel {

	/**
	 * 分页导航
	 */
	private Navigate navigate = new Navigate();

	public Navigate getNavigate() {
		return navigate;
	}

	public void setNavigate(Navigate navigate) {
		this.navigate = navigate;
	}
}
