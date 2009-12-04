package com.intrigueit.myc2i.payment.service;

import java.util.List;

import com.intrigueit.myc2i.payment.domain.PayPalLog;

public interface PayPalLogService {
	
    public void save(PayPalLog entity);

    public void delete(PayPalLog entity);

	public PayPalLog update(PayPalLog entity);
	
	public PayPalLog findById( Long id);

	public List<PayPalLog> findByProperty(String propertyName, Object value);
	
	public Boolean IsTxnExist(String txnId, String payerEmail);
	
	public PayPalLog getLastPayPalLogById(Long memberId);	

}
