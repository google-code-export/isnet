package com.intrigueit.myc2i.payment.dao;

import com.intrigueit.myc2i.common.dao.GenericDao;
import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.payment.domain.PayPalLog;

public interface PayPalLogDao extends GenericDao<PayPalLog,Long>{

	public Boolean IsTxnExist(String txnId, String payerEmail);
}
