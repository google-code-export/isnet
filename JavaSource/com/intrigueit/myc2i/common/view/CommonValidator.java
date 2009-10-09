package com.intrigueit.myc2i.common.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.intrigueit.myc2i.common.ServiceConstants;
import com.intrigueit.myc2i.member.domain.Member;

public class CommonValidator extends BasePage {

	public static boolean isValidEmail(String email){
	      /** Set the email pattern string */
	      Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
	      
	      /** Match the given string with the pattern */
	      Matcher m = p.matcher(email);
	      
	      /** check whether match is found */
	      return m.matches();
	}
	
	public static boolean isEmpty(String val){
		if(val == null || val.equals("")){
			return true;
		}
		return false;
	}
	public static boolean isValidZipCode(String zip){
		
        return zip.matches( "\\d{5}" );
	}
	public static boolean isValidZipCode(Long zip){
		if(zip == null)
			return false;
        return zip.toString().matches( "\\d{5}" );
	}
	public static boolean isNotValidNumber(Long val){
		if(val == null ){
			return true;
		}
		if(val.equals(-1L)){
			return true;
		}
		return false;
	}
	
	public boolean validateMember(Member member,String memberType,String action,
	    String confirmPass,StringBuffer errorMessage) {
	  boolean flag = true;	  
    if ( member == null ) {
      errorMessage.append(this.getText("common_system_error"));
      flag = false;
    } else {      
      if( member.getTypeId()==null || member.getTypeId() == 0){
        if ( !flag )errorMessage.append("<br />");
        errorMessage.append(this.getText("common_error_prefix")).append(" ")
                    .append(this.getText("validation_msg_usertype_required"));
        flag = false;
      }
      if(CommonValidator.isEmpty(member.getFirstName())){
        if ( !flag )errorMessage.append("<br />");
        errorMessage.append(this.getText("common_error_prefix")).append(" ")
                    .append(this.getText("member_validation_first_name"));
        flag = false;
      }
      /** Check the last name */
      if(CommonValidator.isEmpty(member.getLastName())){
        if ( !flag )errorMessage.append("<br />");
        errorMessage.append(this.getText("common_error_prefix")).append(" ")
                    .append(this.getText("member_validation_last_name"));
        flag = false;
      }      
      /** Check the Zip code */
      if(!CommonValidator.isValidZipCode(member.getZip())){
        if ( !flag )errorMessage.append("<br />");
        errorMessage.append(this.getText("common_error_prefix")).append(" ")
                    .append(this.getText("member_validation_zip_code"));
        flag = false;
      }         
      /** Check email address */
      if (action.equals(ServiceConstants.ADD)) {
        if(!CommonValidator.isValidEmail(member.getEmail())){
          if ( !flag )errorMessage.append("<br />");
          errorMessage.append(this.getText("common_error_prefix")).append(" ")
                      .append(this.getText("member_validation_email_address"));
          flag = false;
        }
        
        if(CommonValidator.isEmpty(member.getPassword())){
          if ( !flag )errorMessage.append("<br />");
          errorMessage.append(this.getText("common_error_prefix")).append(" ")
                      .append(this.getText("member_validation_password"));
          flag = false;
        }
        if(!confirmPass.equals(member.getPassword())){
          if ( !flag )errorMessage.append("<br />");
          errorMessage.append(this.getText("common_error_prefix")).append(" ")
                      .append(this.getText("member_validation_password_dont_match"));
          flag = false;
        }
      } 
      /** Check the member profession */
      if(CommonValidator.isEmpty(member.getProfession())){
        if ( !flag )errorMessage.append("<br />");
        errorMessage.append(this.getText("common_error_prefix")).append(" ")
                    .append(this.getText("member_validation_profession"));
        flag = false;
      }
      /** Check the Year of birth*/
      if(CommonValidator.isNotValidNumber(member.getBirthYear())){
        if ( !flag )errorMessage.append("<br />");
        errorMessage.append(this.getText("common_error_prefix")).append(" ")
                    .append(this.getText("member_validation_birth_year"));
        flag = false;
      }
      /** Check the member gender */
      if(CommonValidator.isEmpty(member.getGenderInd())){
        if ( !flag )errorMessage.append("<br />");
        errorMessage.append(this.getText("common_error_prefix")).append(" ")
                    .append(this.getText("member_validation_gender"));
        flag = false;
      }
      
      if (action.equals(ServiceConstants.ADD)) {
        if(CommonValidator.isEmpty(member.getAgreePrivacyPolicy()) || member.getAgreePrivacyPolicy().equals("false")){
          if ( !flag )errorMessage.append("<br />");
          errorMessage.append(this.getText("common_error_prefix")).append(" ")
                      .append(this.getText("member_validation_licence_agree"));
          flag = false;
        }
      }
      
      if(CommonValidator.isEmpty(member.getSecurityQuestion1())){
        if ( !flag )errorMessage.append("<br />");
        errorMessage.append(this.getText("common_error_prefix")).append(" ")
                    .append(this.getText("change_password_security_question1_chose"));
        flag = false;
      }   
      if(CommonValidator.isEmpty(member.getSecurityQuestionAns1())){
        if ( !flag )errorMessage.append("<br />");
        errorMessage.append(this.getText("common_error_prefix")).append(" ")
                    .append(this.getText("change_password_security_question1_empty"));
        flag = false;
      } 
      if(CommonValidator.isEmpty(member.getSecurityQuestion2())){
        if ( !flag )errorMessage.append("<br />");
        errorMessage.append(this.getText("common_error_prefix")).append(" ")
                    .append(this.getText("change_password_security_question2_chose"));
        flag = false;
      }   
      if(CommonValidator.isEmpty(member.getSecurityQuestionAns2())){
        if ( !flag )errorMessage.append("<br />");
        errorMessage.append(this.getText("common_error_prefix")).append(" ")
                    .append(this.getText("change_password_security_question2_empty"));
        flag = false;
      } 
    }      
	  return flag;
	}
}
