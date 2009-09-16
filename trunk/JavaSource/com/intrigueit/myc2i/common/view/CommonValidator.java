package com.intrigueit.myc2i.common.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonValidator {

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
}
