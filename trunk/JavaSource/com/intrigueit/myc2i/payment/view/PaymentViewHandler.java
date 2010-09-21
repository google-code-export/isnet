/**
 * 
 */
package com.intrigueit.myc2i.payment.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.member.service.MemberService;
import com.intrigueit.myc2i.payment.service.PayPalLogService;
import com.intrigueit.myc2i.udvalues.domain.UserDefinedValues;
import com.intrigueit.myc2i.udvalues.service.UDValuesService;

/**
 * @author Shamim Ahmmed
 *
 */
@Component("paymentViewHandler")
@Scope("session")
public class PaymentViewHandler extends BasePage {

	private String merchantEmailAccount;
	private String payPalActionURL;
	
	/** Services ref */
	private UDValuesService udService;
	private PayPalLogService payPalLogService;
	private MemberService memberService;
	
	
	public UDValuesService getUdService() {
		return udService;
	}
	
	@Autowired
	public void setUdService(UDValuesService udService) {
		this.udService = udService;
	}
	public PaymentViewHandler() {
	}

	public String getMerchantEmailAccount() {
		try{
			List<UserDefinedValues> values = this.udService.findByProperty("udValuesCategory", "MERCHANT_ACCOUNT");
			if(values != null && values.size() > 0){
				this.merchantEmailAccount = values.get(0).getUdValuesValue();
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return merchantEmailAccount;
	}

	public void setMerchantEmailAccount(String merchantEmailAccount) {
		this.merchantEmailAccount = merchantEmailAccount;
	}

	public String getPayPalActionURL() {
		try{
			List<UserDefinedValues> values = this.udService.findByProperty("udValuesCategory", "PAYPAY_ACTION_URL");
			if(values != null && values.size() > 0){
				this.payPalActionURL = values.get(0).getUdValuesValue();
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return payPalActionURL;
	}

	public void setPayPalActionURL(String payPalActionURL) {
		this.payPalActionURL = payPalActionURL;
	}

	public PayPalLogService getPayPalLogService() {
		return payPalLogService;
	}
	
	@Autowired
	public void setPayPalLogService(PayPalLogService payPalLogService) {
		this.payPalLogService = payPalLogService;
	}

	public MemberService getMemberService() {
		return memberService;
	}
	
	@Autowired
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}



	
}
