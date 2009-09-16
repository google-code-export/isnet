package com.intrigueit.myc2i.member.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.member.service.MemberService;

@Component("protegeProfileViewHandler")
@Scope("session")
public class ProtegeProfileViewHandler extends BasePage{
	private MemberService memberService;
	private Member currentMember;
	
	private List<Member> myProtegeList;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4277383197550797956L;
	
	@Autowired
	public ProtegeProfileViewHandler(MemberService memberService) {
		this.memberService = memberService;
		this.currentMember = new Member();
	}
	
	public void releaseProtege(){
		String memberId = this.getParameter("MEMBER_ID");
		if(memberId == null || memberId.equals("")){
			return;
		}		
	}
	
	/** Load the selected member */
	public void loadMember(){
		String memberId = this.getParameter("MEMBER_ID");
		if(memberId == null || memberId.equals("")){
			return;
		}
		this.currentMember = this.memberService.findById(Long.parseLong(memberId));
	}
	
	private void loadMyProtege(){
		try{
			Long mentorId = this.getMember().getMemberId();
			this.myProtegeList = this.memberService.getMentorProtege(mentorId);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

	public Member getCurrentMember() {
		return currentMember;
	}

	public void setCurrentMember(Member currentMember) {
		this.currentMember = currentMember;
	}

	public List<Member> getMyProtegeList() {
		this.loadMyProtege();
		return myProtegeList;
	}

	public void setMyProtegeList(List<Member> myProtegeList) {
		this.myProtegeList = myProtegeList;
	}
	
	
	
	
}
