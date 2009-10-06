package com.intrigueit.myc2i.membersearch.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.member.service.MemberService;
import com.intrigueit.myc2i.membersearch.domain.MemberSearch;
import com.intrigueit.myc2i.zipcode.ZipCodeUtil;
import com.intrigueit.myc2i.zipcode.domain.ZipCode;
import com.intrigueit.myc2i.zipcode.service.ZipCodeService;

@Component("memberSearchViewHandler")
@Scope("session")
public class MemberSearchViewHandler extends BasePage {

	private MemberService memberService;
	private ZipCodeService zipCodeService;
	
	private Double dist = 20.0;
	private String srcZipCode;
	private MemberSearch search;
	
	private List<Member> members;
	
	
	/**
	 * 
	 */
	public MemberSearchViewHandler() {
		this.search = new MemberSearch(false,false,false,false);
		this.dist = 20.0;
		this.members = new ArrayList<Member>();
		
	}
	public void executeSearch(){
		try{
			log.debug(this.getDist());
			List<String> zipCodes = this.getZipCodes();
			String conditions = this.getClause(zipCodes);
			if(conditions != null && !conditions.equals("")){
				this.members = this.memberService.getMemberByDynamicHsql(conditions);
			}
			
		}
		catch(Exception ex){
			log.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	private String getClause(List<String> zipCodes){
		String clause = "";
		if(this.search.getIsProfession()){
			clause = "t.profession='"+ this.getMember().getProfession()+"'";
		}
		if(this.search.getIsEhinicity()){
			clause = clause.equals("") ? clause+" t.ethinicity="+ this.getMember().getEthinicity()+"" : clause+" and t.ethinicity="+ this.getMember().getEthinicity()+"";
		}
		if(this.search.getIsMaritalStatus()){
			clause = clause.equals("") ? clause+" t.maritalStatus='"+ this.getMember().getMaritalStatus()+"'" : clause+" and t.maritalStatus='"+ this.getMember().getMaritalStatus()+"'";
		}
		if(this.search.getIsYearOfBirth()){
			clause = clause.equals("") ? clause+" t.birthYear="+this.getMember().getBirthYear() +"" : clause+" and t.birthYear="+this.getMember().getBirthYear() +"";
		}
		if(zipCodes.size() > 0){
			String codes = "";
			for(String str: zipCodes){
				codes = codes.equals("")? str : codes + ","+ str;
			}
			clause = clause.equals("") ? clause+" t.zip in ("+codes +")" : clause+" and t.zip in ("+codes +")";
		}
		return clause;
		
	}
	private List<String> getZipCodes(){
		List<String> zipCodes = new ArrayList<String>();
		
		try{
			//String memberZipCode = this.getMember().getZip().toString();
			ZipCode srcZip = this.zipCodeService.findById(this.getSrcZipCode());
			List<ZipCode> desZipCodes = this.zipCodeService.findAll();
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
	public MemberSearch getSearch() {
		return search;
	}
	public void setSearch(MemberSearch search) {
		this.search = search;
	}
	public List<Member> getMembers() {
		return members;
	}
	public void setMembers(List<Member> members) {
		this.members = members;
	}


	
	  
}
