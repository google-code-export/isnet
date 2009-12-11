package com.intrigueit.myc2i.membersearch.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.member.service.MemberService;
import com.intrigueit.myc2i.membersearch.dao.MemberSearchDao;
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
	private int recordCount;
	private MemberSearchDao memberSearchDao;
	
	private List<Member> members;
	private String msg;
	
	
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
				clause = " t.mentoredByMemberId IS NULL and t.typeId =17";
			}
			else if(searchType.equals("LEAD_MENTOR")){
				clause = " t.typeId =16";
			}
			else if(searchType.equals("MENTOR")){
				clause = " t.typeId =15";
			}
			String zipcode = this.getValidZipCode();
			if(zipcode == null ||  zipcode.equals("") ||  zipcode.equals("0")){
				return;
			}

			ZipCode srcZip = this.zipCodeService.findById(zipcode);
			this.memberSearchDao.fetchZipCode(srcZip.getLatitude(), srcZip.getLongitude(), this.dist);
			List<String> zipCodes = this.memberSearchDao.fetchZipCode(srcZip.getLatitude(), srcZip.getLongitude(), this.dist);
			
			String conditions = this.getClause(zipCodes,clause);
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
					//log.debug("From: "+srcZip.getZipCode() + " Des: "+ zip.getZipCode()+" dis(M): "+ distance);
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


	
	  
}
