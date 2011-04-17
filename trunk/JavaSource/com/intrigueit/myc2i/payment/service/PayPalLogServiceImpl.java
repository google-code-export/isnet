package com.intrigueit.myc2i.payment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intrigueit.myc2i.payment.dao.PayPalLogDao;
import com.intrigueit.myc2i.payment.domain.PayPalLog;

@Service
public class PayPalLogServiceImpl implements PayPalLogService{

	private PayPalLogDao payPalLogDao;

	
	/**
	 * @param payPalLogDao
	 */
	@Autowired
	public PayPalLogServiceImpl(PayPalLogDao payPalLogDao) {
		this.payPalLogDao = payPalLogDao;
	}


	public void delete(PayPalLog entity) {
		this.payPalLogDao.delete(entity);
	}


	public PayPalLog findById(Long id) {
		return this.payPalLogDao.loadById(id);
	}


	public List<PayPalLog> findByProperty(String propertyName, Object value) {
		String clause = " t."+propertyName+" = ?1 ";
		return payPalLogDao.loadByClause(clause, new Object[]{value});
	}


	public void save(PayPalLog entity) {
		payPalLogDao.persist(entity);
		
	}


	public PayPalLog update(PayPalLog entity) {
		payPalLogDao.update(entity);
		 return entity;
	}


	public Boolean IsTxnExist(String txnId, String payerEmail) {
		return payPalLogDao.IsTxnExist(txnId,payerEmail);
	}
	

	public PayPalLog getLastPayPalLogById(Long memberId) {
	  StringBuffer clause = new StringBuffer();
	  clause.append(" from PayPalLog ")
	        .append(" where ipnLogId = (select max(ipnLogId) from PayPalLog where memberId = "+memberId+")");
	  return payPalLogDao.getLastPayPalLogById(clause.toString());
	}

}
