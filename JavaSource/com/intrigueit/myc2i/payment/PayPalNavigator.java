package com.intrigueit.myc2i.payment;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.intrigueit.myc2i.common.CommonConstants;
import com.intrigueit.myc2i.member.domain.Member;

/**
 * Servlet implementation class PayPalTxnListener
 */

public class PayPalNavigator extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static final Logger log = Logger.getLogger( PayPalNavigator.class );   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PayPalNavigator() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.processRequest(request, response);
	}

	 private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 try{
			 HttpSession session = request.getSession();
			 String myc2iPath = "";
			 Member member = (Member) session.getAttribute(CommonConstants.SESSION_MEMBER_KEY);
			 if(member == null){
				 
			 }
			 else if(member.getTypeId().equals(CommonConstants.PROTEGE)){
				 
				 myc2iPath = request.getContextPath() + "/pages/secure/protegeDashboard.faces";
			 }
			 else if(member.getTypeId().equals(CommonConstants.MENTOR)){
				 
				 myc2iPath = request.getContextPath() + "/pages/secure/mentorDashboard.faces";
			 }
			 log.debug(myc2iPath);
			 response.sendRedirect(myc2iPath);
		 }
		 catch(Exception ex){
			 log.error(ex.getMessage());
		 }
	 }




}
