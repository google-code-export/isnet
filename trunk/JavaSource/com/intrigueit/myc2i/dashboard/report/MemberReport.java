package com.intrigueit.myc2i.dashboard.report;

import java.util.List;

import com.intrigueit.myc2i.member.domain.Member;

public class MemberReport {

	private Member mentor;

	private List<Member> proteges;
	
	//private MemberReport report;
	
	public Member getMentor() {
		return mentor;
	}

	public void setMentor(Member mentor) {
		this.mentor = mentor;
	}

	public List<Member> getProteges() {
		return proteges;
	}

	public void setProteges(List<Member> proteges) {
		this.proteges = proteges;
	}


}
