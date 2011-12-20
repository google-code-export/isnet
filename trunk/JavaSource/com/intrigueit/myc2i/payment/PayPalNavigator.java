package com.intrigueit.myc2i.payment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.intrigueit.myc2i.common.CommonConstants;
import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.member.service.MemberService;
import com.intrigueit.myc2i.payment.view.PaymentViewHandler;
import com.intrigueit.myc2i.role.domain.RolePageAccess;

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
			 String myc2iPath = request.getContextPath() +"/login.faces";
			 Member member = (Member) session.getAttribute(CommonConstants.SESSION_MEMBER_KEY);
			 WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext()); 
			
			 PaymentViewHandler bean = null;
			 bean = (PaymentViewHandler) ctx.getBean("paymentViewHandler");
			 if(member == null){
			 }
			 else if(!isMemberShipExtended(member.getMemberId(), bean.getMemberService())){
				 
			 }
			 else if(member.getTypeId().equals(CommonConstants.PROTEGE)){
				 
				 myc2iPath = request.getContextPath() + "/pages/secure/protegeDashboard.faces";
				 this.setUserPrivilegePages(member.getTypeId(), request,bean.getMemberService());
			 }
			 else if(member.getTypeId().equals(CommonConstants.MENTOR)){
				 
				 myc2iPath = request.getContextPath() + "/pages/secure/mentorDashboard.faces";
				 this.setUserPrivilegePages(member.getTypeId(), request,bean.getMemberService());
			 }
			 log.debug(myc2iPath);
			 response.sendRedirect(myc2iPath);
		 }
		 catch(Exception ex){
			 log.error(ex.getMessage());
		 }
	 }

	private boolean isMemberShipExtended(Long memberId,MemberService memberService){
		if(memberService.isMembershipExpired(memberId, CommonConstants.DEFAULT_EXPIRY_DATE)){
			return false;
		}
		return true;
	}

	public void setUserPrivilegePages(Long memberTypeId,HttpServletRequest request,MemberService memberService) {

		try {
			List<RolePageAccess> privilegePagesList = memberService.loadUserPrivilegePages(memberTypeId);
			if (privilegePagesList != null) {
				ArrayList<String> userPrivilegePages = new ArrayList<String>();
				for (RolePageAccess rolePageAccess : privilegePagesList) {
					if (rolePageAccess.getApplicationPages() != null) {
						userPrivilegePages.add(rolePageAccess.getApplicationPages().getPageUrl());
					}
					//log.debug(rolePageAccess.getApplicationPages().getPageUrl());
				}
				HttpSession session = request.getSession(false);
				session.setAttribute(CommonConstants.USER_PRIVILEGE_PAGES,userPrivilegePages);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}


}
