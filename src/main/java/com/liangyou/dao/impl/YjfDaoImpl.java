package com.liangyou.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.liangyou.context.YjfType;
import com.liangyou.dao.YjfDao;
import com.liangyou.domain.YjfPay;
import com.liangyou.model.PageDataList;
import com.liangyou.model.SearchParam;
@Repository
public class YjfDaoImpl extends ObjectDaoImpl<YjfPay> implements YjfDao {
	
	@Override
	/**
	 * 查询标的交易号
	 * @param userId
	 * @param borrowId
	 * @param status
	 * @return
	 */
	public YjfPay  getBorrowTradeNo(String userId, String borrowId , int status){
		StringBuffer sb = new StringBuffer();
		sb.append(" from YjfPay  where borrowid = ?1 and userid = ?2 and status = ?3  ");
	    Query query = em.createQuery(sb.toString());
		query.setParameter(1, borrowId);
		query.setParameter(2, userId);
		query.setParameter(3, status);
		if(query.getResultList().size() > 0){
			return  (YjfPay)query.getResultList().get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public List<YjfPay> getTendersPayed(String borrowId) {
		StringBuffer sb = new StringBuffer();
		sb.append(" from YjfPay  where borrowid = ?1 and status = ?2 and service = ?3 ");
	    Query query = em.createQuery(sb.toString());
		query.setParameter(1, borrowId);
		query.setParameter(2, 1);
		query.setParameter(3, YjfType.TRADEPAYERAPPLYPOOLTOGETHER);
		if(query.getResultList().size() > 0){
			return query.getResultList();
		}else{
			return null;
		}
	}

	@Override
	public PageDataList<YjfPay> getList(SearchParam sp) {
		return this.findPageList(sp);
	}
	
	/**
	 * 满标复审之前，查询是否有投标失败的
	 * @param borrowId
	 * @param service
	 * @return
	 */
	@Override
	public List<YjfPay> getWrongStatusYjfPayByBorrowId(long borrowId,String service){
		String jpql = " from YjfPay where  service = ?1 and ( status = ?2 or status = ?3 ) and borrowid = ?4  ";
		Query query = em.createQuery(jpql);
		query.setParameter(1, service)
		.setParameter(2, 0)
		.setParameter(3, 2)
		.setParameter(4, borrowId +"");
		return query.getResultList();
	}
	
}
