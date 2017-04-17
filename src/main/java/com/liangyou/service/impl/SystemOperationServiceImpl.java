package com.liangyou.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.liangyou.dao.SystemOperationDao;
import com.liangyou.domain.SystemOperation;
import com.liangyou.model.PageDataList;
import com.liangyou.model.SearchParam;
import com.liangyou.model.SearchFilter.Operator;
import com.liangyou.service.SystemOperationService;

@Service(value="systemOperationService")
@Transactional(propagation = Propagation.REQUIRED)
public class SystemOperationServiceImpl extends BaseServiceImpl implements SystemOperationService {

	@Autowired
	private SystemOperationDao systemOperationDao;
	
	@Override
	public SystemOperation find(int id) {
		return this.systemOperationDao.find(id);
	}
	
	
	@Override
	public void save(SystemOperation item) {
		this.systemOperationDao.save(item);
	}

	@Override
	public List<SystemOperation> list(SystemOperation item) {
		SearchParam param = SearchParam.getInstance();
		if(item!=null){
			if(item.getParentId()>=0){
				param.addParam("parentId", Operator.EQ, item.getParentId());
			}
		}
		return this.systemOperationDao.findByCriteria(param);
	}
	
	/**
	 * 查询所有操作类型 ，level = 2
	 * @return
	 */
	@Override
	public List<SystemOperation> getAllOperationType( ) {
		SearchParam param = SearchParam.getInstance();
		param.addParam("level", 2);
		return this.systemOperationDao.findByCriteria(param);
	}
	
	@Override
	public PageDataList<SystemOperation> page(SearchParam param) {
		return this.systemOperationDao.findPageList(param);
	}


	@Override
	public void update(SystemOperation item) {
		this.systemOperationDao.update(item);
	}

}
