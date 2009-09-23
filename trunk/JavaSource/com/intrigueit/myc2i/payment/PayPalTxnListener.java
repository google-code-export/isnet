package com.intrigueit.myc2i.payment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.intrigueit.myc2i.payment.domain.PayPalLog;
import com.intrigueit.myc2i.payment.view.PaymentViewHandler;
import com.intrigueit.myc2i.udvalues.domain.UserDefinedValues;

/**
 * Servlet implementation class PayPalTxnListener
 */

public class PayPalTxnListener extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PayPalTxnListener() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.processTransaction(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.processTransaction(request, response);
	}
	@SuppressWarnings({ "unchecked", "deprecation" })
	private void processTransaction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());      
		String strActionURL = null;
		String merchantEmail = null;
		PaymentViewHandler bean = null;
		try{
			bean = (PaymentViewHandler) ctx.getBean("paymentViewHandler");
			List<UserDefinedValues> values = bean.getUdService().findByProperty("udValuesCategory", "PAYPAY_ACTION_URL");
			if(values != null && values.size() > 0){
				strActionURL = values.get(0).getUdValuesValue();
				System.out.print(strActionURL);
			}
			List<UserDefinedValues> merchantEmails = bean.getUdService().findByProperty("udValuesCategory", "MERCHANT_ACCOUNT");
			if(merchantEmails != null && merchantEmails.size() > 0){
				merchantEmail = merchantEmails.get(0).getUdValuesValue();
				System.out.print(merchantEmail);
			}			
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

		Enumeration en = request.getParameterNames();
		String str = "cmd=_notify-validate";
		while(en.hasMoreElements()){
		String paramName = (String)en.nextElement();
		String paramValue = request.getParameter(paramName);
		str = str + "&" + paramName + "=" + URLEncoder.encode(paramValue);
		}

		URL u = new URL(strActionURL);
		URLConnection uc = u.openConnection();
		uc.setDoOutput(true);
		uc.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		PrintWriter pw = new PrintWriter(uc.getOutputStream());
		pw.println(str);
		pw.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
		String txtResponse = in.readLine();
		in.close();
		
		
		txtResponse = "VERIFIED";
		
		
		String itemName = request.getParameter("item_name");
		String itemNumber = request.getParameter("item_number");
		String paymentStatus = request.getParameter("payment_status");
		String paymentAmount = request.getParameter("mc_gross");
		String paymentCurrency = request.getParameter("mc_currency");
		String txnId = request.getParameter("txn_id");
		String receiverEmail = request.getParameter("receiver_email");
		String payerEmail = request.getParameter("payer_email");		
		String txn_type = request.getParameter("txn_type");
		
		String memberId = request.getParameter("custom");
		String paymentDate = request.getParameter("payment_date");
		String notifyVersion = request.getParameter("notify_version");
		
		
		if(txtResponse.equals("VERIFIED")) {
			// check that paymentStatus=Completed
			// check that txnId has not been previously processed
			// check that receiverEmail is your Primary PayPal email
			// check that paymentAmount/paymentCurrency are correct
			// process payment
			if(paymentStatus == null || !paymentStatus.toLowerCase().equals("completed")){
				return;
			}
			if(bean.getPayPalLogService().IsTxnExist(txnId, payerEmail)){
				return;
			}
			if(!receiverEmail.equals(merchantEmail)){
				return;
			}
			PayPalLog log = new PayPalLog();
			try{
				log.setCurrency(paymentCurrency);
				log.setGrossAmount(Double.parseDouble(paymentAmount));
				log.setItemName(itemName);
				log.setMemberId(Long.parseLong(memberId));
				log.setNotifyVersion(Double.parseDouble(notifyVersion));
				log.setPayerEmail(payerEmail);
				DateFormat df = new SimpleDateFormat("HH:mm:ss MMM dd, yyyy z");
				log.setPaymentDate(df.parse(paymentDate));
				log.setPaymentStatus(paymentStatus);
				log.setPayPalTxnId(txnId);
				log.setTxnType(txn_type);
				
				/** Save the transaction log */
				bean.getPayPalLogService().save(log);
				
			}
			catch(Exception ex){
				ex.printStackTrace();
			}

			
		}
		else if(txtResponse.equals("INVALID")) {
			// log for investigation
		}
		else {
			// error
		}		
	}



}
