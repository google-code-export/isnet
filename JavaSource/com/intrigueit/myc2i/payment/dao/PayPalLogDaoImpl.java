package com.intrigueit.myc2i.payment.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intrigueit.myc2i.common.dao.GenericDaoImpl;
import com.intrigueit.myc2i.payment.domain.PayPalLog;

@Repository
@Transactional
public class PayPalLogDaoImpl extends GenericDaoImpl<PayPalLog,Long> implements PayPalLogDao{

	@Override
	public Boolean IsTxnExist(String txnId, String payerEmail) {
		String clause = " t.payPalTxnId = ?1 and t.payerEmail =?2 ";
		List<PayPalLog> logs = loadByClause(clause, new Object[]{txnId,payerEmail});
		return logs.size()>0;
	}
	
	public PayPalLog getLastPayPalLogById(String hsql) {
	  Query query = entityManager.createQuery(hsql);
    return (PayPalLog) query.getSingleResult();
	}
}
