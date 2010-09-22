package com.intrigueit.myc2i.membersearch.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.CommonConstants;
import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.member.service.MemberService;
import com.intrigueit.myc2i.membersearch.dao.MemberSearchDao;
import com.intrigueit.myc2i.membersearch.domain.MemberSearch;
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
	private int recordCount;
	private MemberSearchDao memberSearchDao;
	
	private List<Member> members;
	private String msg;
	private String init;
	
	private MemberService memberSerivce;
	
	private boolean mentorPaidSubscription;
	
	/**
	 * 
	 */
	public MemberSearchViewHandler() {
		this.search = new MemberSearch(false,false,false,false);
		this.dist = 20.0;
		this.members = new ArrayList<Member>();
		
	}
	
	private void reset(){
		this.msg = "";
		this.members.clear();
		this.recordCount = 0;
	}
	private String getValidZipCode(){
		Long zipCode = 0L;
		try{
			 zipCode = Long.parseLong(this.getSrcZipCode());
		}
		catch(Exception ex){
			zipCode = 0L;
		}
		return ""+zipCode;
	}
	public void executeSearch(){
		try{
			this.reset();

			String searchType = this.getRequest().getParameter("SEARCH_TYPE");
			String clause = null;
			if(searchType == null){
				return;
			}
			if(searchType.equals("PROTEGE")){
				clause = " t.mentoredByMemberId IS NULL and t.typeId ="+ CommonConstants.PROTEGE +" ";
			}
			else if(searchType.equals("MENTOR")){
				clause = " t.typeId ="+ CommonConstants.MENTOR +" and upper(t.isMemberCertified) = 'YES'";
			}
			else if(searchType.equals("LEAD_MENTOR")){
				clause = " t.typeId ="+ CommonConstants.MENTOR +" and t.memberId <> "+ this.getMember().getMemberId()+" and upper(t.isMemberCertified) = 'YES' ";
			}
			
			String currentMemberGender = this.getMember().getGenderInd();
			clause = clause + " and t.genderInd='"+ currentMemberGender + "'"; 
			String conditions = null;
			
			String zipcode = this.getValidZipCode();
			if(zipcode == null ||  zipcode.equals("") ||  zipcode.equals("0")){
				conditions = this.getClause(new ArrayList<String>(),clause);	
				if(conditions.equals(clause)){
					return;
				}
			}
			else{
				ZipCode srcZip = this.zipCodeService.findById(zipcode);
				if(srcZip == null){
					throw new Exception("Not a valid zip code.");
				}
				List<String> zipCodes = this.memberSearchDao.fetchZipCode(srcZip.getLatitude(), srcZip.getLongitude(), this.dist);
				
				conditions = this.getClause(zipCodes,clause);				
			}


			if(conditions != null && !conditions.equals("")){
				this.members = this.memberService.getMemberByDynamicHsql(conditions);
				if(this.members != null){
					this.recordCount = this.members.size();
				}
			}

		}
		catch(Exception ex){
			log.error(ex.getMessage());
			ex.printStackTrace();
			this.msg = ex.getMessage();
		}
	}
	private String getClause(List<String> zipCodes,String clause){
		
		if(this.search.getIsProfession()){
			clause = clause + " and t.profession='"+ this.getMember().getProfession()+"'";
		}
		if(this.search.getIsEhinicity()){
			clause = clause+" and t.ethinicity="+ this.getMember().getEthinicity()+"";
		}
		if(this.search.getIsMaritalStatus()){
			clause =  clause+" and t.maritalStatus='"+ this.getMember().getMaritalStatus()+"'";
		}
		if(this.search.getIsYearOfBirth()){
			clause = clause+" and t.birthYear="+this.getMember().getBirthYear() +"";
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
		if(this.srcZipCode == null || this.srcZipCode.equals("")){
			this.srcZipCode = this.getMember().getZip();
		}
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
	public int getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	
	@Autowired
	public void setMemberSearchDao(MemberSearchDao memberSearchDao) {
		this.memberSearchDao = memberSearchDao;
	}

	public String getMsg() {
		return msg;
	}

	public String getInit() {
		if(this.members != null) reset();
		return init;
	}

	public boolean isMentorPaidSubscription() {
		try{
			Member member = this.memberSerivce.findById(this.getMember().getMemberId());
			Date expiry = member.getMemberShipExpiryDate();
			if(expiry == null){
				return false;
			}
			Date now = new Date();
			if(expiry.before(now)){
				this.mentorPaidSubscription = false;
			}
			else{
				this.mentorPaidSubscription = true;
			}
			
		}
		catch(Exception ex){
			log.error(ex.getMessage(),ex);
			ex.printStackTrace();
		}			
		return mentorPaidSubscription;
	}

	public void setMentorPaidSubscription(boolean mentorPaidSubscription) {
		this.mentorPaidSubscription = mentorPaidSubscription;
	}

	public MemberService getMemberSerivce() {
		return memberSerivce;
	}
	@Autowired
	public void setMemberSerivce(MemberService memberSerivce) {
		this.memberSerivce = memberSerivce;
	}


	
	  
}
