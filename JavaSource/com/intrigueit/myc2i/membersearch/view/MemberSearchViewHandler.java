package com.intrigueit.myc2i.membersearch.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.member.service.MemberService;
import com.intrigueit.myc2i.zipcode.ZipCodeUtil;
import com.intrigueit.myc2i.zipcode.domain.ZipCode;
import com.intrigueit.myc2i.zipcode.service.ZipCodeService;

@Component("memberSearchViewHandler")
@Scope("session")
public class MemberSearchViewHandler extends BasePage {

	private MemberService memberService;
	private ZipCodeService zipCodeService;
	
	private Double dist;
	private String srcZipCode;
	private Member searchMember;
	
	
	/**
	 * 
	 */
	public MemberSearchViewHandler() {
		this.searchMember = new Member();
	}
	public void executeSearch(){
		try{
			log.debug(this.dist);
			List<String> zipCodes = this.getZipCodes();
		}
		catch(Exception ex){
			log.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	private List<String> getZipCodes(){
		List<String> zipCodes = new ArrayList<String>();
		
		try{
			String memberZipCode = this.getMember().getZip().toString();
			ZipCode srcZip = this.zipCodeService.findById(memberZipCode);
			List<ZipCode> desZipCodes = this.zipCodeService.findByState(this.getMember().getState());
			ZipCodeUtil util = new ZipCodeUtil();
			for(ZipCode zip: desZipCodes){
				Double distance = util.getDistance(srcZip, zip);
				if(distance <= this.dist){
					System.out.println("From: "+srcZip.getZipCode() + " Des: "+ zip.getZipCode()+" dis(M): "+ distance);
					zipCodes.add(zip.getZipCode());
				}
				
			}
		}
		catch(Exception ex){
			
		}
		return zipCodes;
	}
	public MemberService getMemberService() {
		return memberService;
	}
	@Autowired
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	public ZipCodeService getZipCodeService() {
		return zipCodeService;
	}
	@Autowired
	public void setZipCodeService(ZipCodeService zipCodeService) {
		this.zipCodeService = zipCodeService;
	}
	public Double getDist() {
		return dist;
	}
	public void setDist(Double dist) {
		this.dist = dist;
	}
	public String getSrcZipCode() {
		return srcZipCode;
	}
	public void setSrcZipCode(String srcZipCode) {
		this.srcZipCode = srcZipCode;
	}
	public Member getSearchMember() {
		return searchMember;
	}
	public void setSearchMember(Member searchMember) {
		this.searchMember = searchMember;
	}
	
	
	  
}
