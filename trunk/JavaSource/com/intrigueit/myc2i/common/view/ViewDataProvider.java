package com.intrigueit.myc2i.common.view;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.member.service.MemberService;
import com.intrigueit.myc2i.udvalues.domain.UserDefinedValues;
import com.intrigueit.myc2i.udvalues.service.UDValuesService;

@Component("viewDataProvider")
@Scope("request")
public class ViewDataProvider extends BasePage {
	
	private UDValuesService udService;
	
	private MemberService memberService;
	
	private List<SelectItem> memberList;
	
	private List<SelectItem> activityList;
	
	private List<SelectItem> stateList;
	private List<SelectItem> countryList;
	private List<SelectItem> professionList;
	private List<SelectItem> religionList;
	private List<SelectItem> madhabList;
	private List<SelectItem> ethinicityList;
	private ArrayList<SelectItem> userTypes;
	private Hashtable<String, String> memberTypeHash;
	
	/**
     * @return the userTypes
     */
    public ArrayList<SelectItem> getUserTypes() {
      if ( userTypes == null ) {
        this.loadUserTypes();
      }      
      return this.userTypes;
    }

  
    public void loadUserTypes(){
      this.userTypes = new ArrayList<SelectItem>();
      userTypes.add(new SelectItem("",""));
      List<UserDefinedValues> udvList = this.udService.findByProperty("udValuesCategory", "MEMBER_TYPE");
      for (UserDefinedValues userDefinedValues : udvList) {
        userTypes.add(new SelectItem(userDefinedValues.getUdValuesId()+"",userDefinedValues.getUdValuesValue().toString()));
      }   
  }
  
    /**
     * @return the memberType
     */
    public Hashtable<String, String> getMemberTypeHash() {
      if (memberTypeHash == null) {
        loadMemberTypeHash();
        
      }
      return memberTypeHash;
    }
    public void loadMemberTypeHash(){
      this.memberTypeHash = new Hashtable<String, String>();
      List<UserDefinedValues> udvList = this.udService.findByProperty("udValuesCategory", "MEMBER_TYPE");
      for (UserDefinedValues userDefinedValues : udvList) {
        this.memberTypeHash.put(""+userDefinedValues.getUdValuesValue(), ""+userDefinedValues.getUdValuesId());
      }   
    }  
  
  
   public List<SelectItem> getStateList(){
		if(this.stateList == null){
			this.loadStateList();
		}
		return this.stateList;
	}
	public List<SelectItem> getCountryList(){
		if(this.countryList == null){
			this.loadCountryList();
		}
		return this.countryList;
	}
	public List<SelectItem> getEthinicityList(){
		if(this.ethinicityList == null){
			this.loadEthinicity();
		}
		return ethinicityList;
	}

	public List<SelectItem> getMadhabList(){
		if(this.madhabList == null){
			this.loadMadhab();
		}
		return madhabList;
	}
	
	public List<SelectItem> getProfessionList(){
		if(this.professionList == null){
			this.loadProfession();
		}
		return professionList;
	}
	private void loadEthinicity(){
		this.ethinicityList = new ArrayList<SelectItem>();
		ethinicityList.add(new SelectItem("-1","--Select--"));
		List<UserDefinedValues> udvList = this.udService.findByProperty("udValuesCategory", "ETHINICITY");
		for (UserDefinedValues userDefinedValues : udvList) {
			ethinicityList.add(new SelectItem(userDefinedValues.getUdValuesId()+"",userDefinedValues.getUdValuesValue().toString()));
		}	
	}	
	private void loadMadhab(){
		this.madhabList = new ArrayList<SelectItem>();
		madhabList.add(new SelectItem("-1","--Select--"));
		List<UserDefinedValues> udvList = this.udService.findByProperty("udValuesCategory", "MADHAB");
		for (UserDefinedValues userDefinedValues : udvList) {
			madhabList.add(new SelectItem(userDefinedValues.getUdValuesId()+"",userDefinedValues.getUdValuesValue().toString()));
		}	
	}
	
	private void loadProfession(){
		this.professionList = new ArrayList<SelectItem>();
		professionList.add(new SelectItem("-1","--Select--"));
		List<UserDefinedValues> udvList = this.udService.findByProperty("udValuesCategory", "PROFESSION");
		for (UserDefinedValues userDefinedValues : udvList) {
			professionList.add(new SelectItem(userDefinedValues.getUdValuesId()+"",userDefinedValues.getUdValuesValue().toString()));
		}	
	}
	public static ArrayList<SelectItem> getMaritialStatusList(){
		ArrayList<SelectItem> maritialStatusList = new ArrayList<SelectItem>();
		maritialStatusList.add(new SelectItem("-1","--Select--"));
		maritialStatusList.add(new SelectItem("1","Single"));
		maritialStatusList.add(new SelectItem("2","Divorced"));
		maritialStatusList.add(new SelectItem("3","Widow"));
		return maritialStatusList;
	}
	
	public  ArrayList<SelectItem> getDateList(){
		ArrayList<SelectItem> dayList = new ArrayList<SelectItem>();
		dayList.add(new SelectItem("0","Today"));
		dayList.add(new SelectItem("-1","Yesterday"));
		dayList.add(new SelectItem("-7","Last Week"));
		dayList.add(new SelectItem("-30","Last Month"));
		dayList.add(new SelectItem("-90","Last Quarter"));
		return dayList;
	}
	
	public ArrayList<SelectItem> getPaymentTerms(){
    ArrayList<SelectItem> payTermList = new ArrayList<SelectItem>();
    payTermList.add(new SelectItem("1","One Year"));
    return payTermList;
  }
	
	public static ArrayList<SelectItem> getYearList(){
		ArrayList<SelectItem> birthYearList = new ArrayList<SelectItem>();
		birthYearList.add(new SelectItem("-1","--Select--"));
		for(int index=2000;index > 1900;index--){
			birthYearList.add(new SelectItem(""+index,""+index));
		}
		return birthYearList;
	}	
	public static ArrayList<SelectItem> getKnowledgeLevelList(){
		ArrayList<SelectItem> knowledgeLevelList = new ArrayList<SelectItem>();
		knowledgeLevelList.add(new SelectItem("-1","--Select--"));
		for(int index=1; index <= 10; index++){
			knowledgeLevelList.add(new SelectItem(""+index,index+" on a of scale 10"));
		}
		return knowledgeLevelList;
	}	
	
	public List<SelectItem> getReligionList(){
		if(this.religionList == null){
			this.loadReligion();
		}
		return religionList;
	}	
	
	private void loadReligion(){
		this.religionList = new ArrayList<SelectItem>();
		religionList.add(new SelectItem("-1","--Select--"));
		List<UserDefinedValues> udvList = this.udService.findByProperty("udValuesCategory", "RELIGION");
		for (UserDefinedValues userDefinedValues : udvList) {
			religionList.add(new SelectItem(userDefinedValues.getUdValuesId()+"",userDefinedValues.getUdValuesValue().toString()));
		}	
	}	
	public ArrayList<SelectItem> getQuestionList(){
		ArrayList<SelectItem> questionList = new ArrayList<SelectItem>();
		questionList.add(new SelectItem("","--Select--"));
		for(int i=1;i<= 5;i++){
			String key = "security_question_"+i;
			String question = this.getText(key);
			questionList.add(new SelectItem(""+i,question));
		}
	
		return questionList;
	}
	
	private void getActivityTypeList(){
		activityList = new ArrayList<SelectItem>();
		List<UserDefinedValues> udvList = this.udService.findByProperty("udValuesCategory", "ACTIVITY_LOG");
		for (UserDefinedValues userDefinedValues : udvList) {
			activityList.add(new SelectItem(userDefinedValues.getUdValuesId()+"",userDefinedValues.getUdValuesValue().toString()));
		}
	}
	private void loadMemberList(){
		this.memberList = new ArrayList<SelectItem>();
		List<Member> members = this.memberService.findAll();
		for (Member member : members) {
			this.memberList.add(new SelectItem( member.getMemberId().toString(),member.getFirstName()+ " "+member.getLastName()));
		}
	}
	/** Load state list */
	private void loadStateList(){
		this.stateList = new ArrayList<SelectItem>();
		stateList.add(new SelectItem("-1","--Select--"));
		List<UserDefinedValues> udvList = this.udService.findByProperty("udValuesCategory", "STATE");
		for (UserDefinedValues userDefinedValues : udvList) {
			stateList.add(new SelectItem(userDefinedValues.getUdValuesId()+"",userDefinedValues.getUdValuesDesc().toString()));
		}		
		
	}
	private void loadCountryList(){
		this.countryList = new ArrayList<SelectItem>();
		countryList.add(new SelectItem("-1","--Select--"));
		List<UserDefinedValues> udvList = this.udService.findByProperty("udValuesCategory", "COUNTRY");
		for (UserDefinedValues userDefinedValues : udvList) {
			countryList.add(new SelectItem(userDefinedValues.getUdValuesId()+"",userDefinedValues.getUdValuesValue().toString()));
		}		
	}
	public UDValuesService getUdService() {
		return udService;
	}
	
	@Autowired
	public void setUdService(UDValuesService udService) {
		this.udService = udService;
	}
	public List<SelectItem> getMemberList() {
		if(memberList == null){
			this.loadMemberList();
		}
		return memberList;
	}
	public List<SelectItem> getActivityList() {
		if(activityList == null){
			this.getActivityTypeList();
		}
		return activityList;
	}
	
	public void setMemberList(List<SelectItem> memberList) {
		this.memberList = memberList;
	}

	public void setActivityList(List<SelectItem> activityList) {
		this.activityList = activityList;
	}
	public MemberService getMemberService() {
		return memberService;
	}
	@Autowired
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	
}
