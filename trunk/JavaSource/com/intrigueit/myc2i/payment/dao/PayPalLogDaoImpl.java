package com.intrigueit.myc2i.payment.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intrigueit.myc2i.common.dao.GenericDaoImpl;
import com.intrigueit.myc2i.payment.domain.PayPalLog;

@Repository
@Transactional
public class PayPalLogDaoImpl extends GenericDaoImpl<PayPalLog,Long> implements PayPalLogDao{

	@Override
	public Boolean IsTxnExist(String txnId, String payerEmail) {
		// TODO Auto-generated method stub
		return null;
	}

}
