package com.intrigueit.myc2i.common.view;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.member.service.MemberService;

public class BasePageExtended extends BasePage {

	protected String toMemberNameList = "";
	
	/** Contains member id and their email address */
	protected Map<Long, String> toMemberList = new HashMap<Long, String>();
	
	

	public void updateMemberListInServer(){
		try{
			log.debug("Item selected event :");
			String prm1 = this.getParameter("PRM1");
			String prm2 = this.getParameter("PRM2");
			log.debug("++++"+prm1 + " ++++++++ "+ prm2);
			if(prm1 == null || prm1.equals("")){
				return;
			}
			Long memberId = Long.parseLong(prm1);
			if(!toMemberList.containsKey(memberId)){
				this.toMemberList.put(memberId, prm2);
			}
			
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

	}

	protected Set<Member> getReceivers(MemberService memberService)throws Exception{
		Set<Member> receivers = new HashSet<Member>();
		
		Set<Entry<Long, String>>  enty = this.getToMemberList().entrySet();
		for(Entry<Long, String> ent : enty){
			//log.debug(ent.getKey() +" ---------- " + ent.getValue());
			Member member = memberService.findById(ent.getKey());
			
			if(member != null){
				receivers.add(member);
			}
		}
		/** If to member list is empty */
		if( this.getToMemberList().size() < 1){
			receivers = this.getMembersFromEmail(memberService);
		}
		if( receivers == null || receivers.size() < 1){
			throw new Exception("No valid destination address");
		}
		return receivers;
	}
	
	/**
	 * Populate the to member list from the email addresses
	 * @return Set of to member list
	 */
	private Set<Member> getMembersFromEmail(MemberService memberService){
		if(this.toMemberNameList == null || this.toMemberNameList.equals("")){
			return null;
		}
		String emailList[] = this.toMemberNameList.split(",");
		if(emailList.length < 1){
			return null;
		}
		Set<Member> receivers = new HashSet<Member>();
		for(String email: emailList){
			log.debug("-------->"+ email);
			Member member = memberService.loadMemberByEmail(email.trim());
			if(member != null){
				receivers.add(member);
			}
		}
		return receivers;
	}

	public String getToMemberNameList() {
		return toMemberNameList;
	}

	public void setToMemberNameList(String toMemberNameList) {
		this.toMemberNameList = toMemberNameList;
	}

	public Map<Long, String> getToMemberList() {
		return toMemberList;
	}

	public void setToMemberList(Map<Long, String> toMemberList) {
		this.toMemberList = toMemberList;
	}
}
