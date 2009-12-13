package com.intrigueit.myc2i.common.cache;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intrigueit.myc2i.common.CommonConstants;
import com.intrigueit.myc2i.payment.PayPalTxnListener;
import com.intrigueit.myc2i.udvalues.domain.UserDefinedValues;
import com.intrigueit.myc2i.udvalues.service.UDValuesService;

@Service
public  class CachingManager {

	private UDValuesService udService;
	private static List<Long> protegeRequestActivityIds = null;
	private static List<Long> mentorRequestActivityIds = null;
	private static List<Long> mentorRequestActivityIdsForMentor = null;
	
	protected static final Logger log = Logger.getLogger( CachingManager.class );   
	
	@Autowired
	public void setUdService(UDValuesService udService) {
		this.udService = udService;
	}
	
	public List<Long> getProtegeRequestActivityIds(){
		if(protegeRequestActivityIds == null){
			log.debug("from database...");
			protegeRequestActivityIds = new ArrayList<Long>();
			List<UserDefinedValues> values  = this.udService.findByProperty("udValuesCategory", CommonConstants.ACTIVITY_LOG_PROTEGE);
			for(UserDefinedValues val: values){
				if(val.getUdValuesValue().toLowerCase().equals(CommonConstants.ACTIVITY_TYPE_MENTOR_RELEASE.toLowerCase()) ||
				  val.getUdValuesValue().toLowerCase().equals(CommonConstants.ACTIVITY_TYPE_MENTOR_REQUEST.toLowerCase())
						){
					protegeRequestActivityIds.add(val.getUdValuesId());
				}
			}
		}
		else{
			log.debug("from cache...");
		}
		
		return protegeRequestActivityIds;
	}
	public Boolean isProtegeRequestAcityIdExist(Long id){
		this.getProtegeRequestActivityIds();
		for(Long val: protegeRequestActivityIds){
			if(val.equals(id)){
				return true;
			}
		}
		return false;
	}
	
	public Boolean isMentorRequestAcityIdExist(Long id){
		this.getMentorRequestActivityIds();
		for(Long val: mentorRequestActivityIds){
			if(val.equals(id)){
				return true;
			}
		}
		return false;
	}
	public List<Long> getMentorRequestActivityIds(){
		if(mentorRequestActivityIds == null){
			log.debug("from database...");
			mentorRequestActivityIds = new ArrayList<Long>();
			List<UserDefinedValues> values  = this.udService.findByProperty("udValuesCategory", CommonConstants.ACTIVITY_LOG_MENTOR);
			for(UserDefinedValues val: values){
				if(val.getUdValuesValue().toLowerCase().equals(CommonConstants.ACTIVITY_TYPE_PROTEGE_RELEASE.toLowerCase()) ||
				  val.getUdValuesValue().toLowerCase().equals(CommonConstants.ACTIVITY_TYPE_PROTEGE_REQUEST.toLowerCase())
						){
					mentorRequestActivityIds.add(val.getUdValuesId());
				}
			}
		}
		else{
			log.debug("from cache...");
		}
		return mentorRequestActivityIds;
	}

	public  List<Long> getMentorRequestActivityIdsForMentor() {
		if(mentorRequestActivityIdsForMentor == null){
			log.debug("from database...");
			mentorRequestActivityIdsForMentor = new ArrayList<Long>();
			List<UserDefinedValues> values  = this.udService.findByProperty("udValuesCategory", CommonConstants.ACTIVITY_LOG_MENTOR);
			for(UserDefinedValues val: values){
				if(val.getUdValuesValue().toLowerCase().equals(CommonConstants.ACTIVITY_TYPE_MENTOR_REQUEST.toLowerCase())){
					mentorRequestActivityIdsForMentor.add(val.getUdValuesId());
				}
			}
		}
		else{
			log.debug("from cache...");
		}		
		return mentorRequestActivityIdsForMentor;
	}	
	
}
