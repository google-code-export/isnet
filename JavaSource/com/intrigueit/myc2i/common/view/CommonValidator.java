package com.intrigueit.myc2i.common.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.intrigueit.myc2i.common.ServiceConstants;
import com.intrigueit.myc2i.member.domain.Member;

public class CommonValidator extends BasePage {

	public static boolean isValidPassword(String val) {

		Pattern p = Pattern.compile("((?=.*\\d)(?=.*[A-Z]).{6,20})");

		/** Match the given string with the pattern */
		Matcher m = p.matcher(val);
		if (m.matches()) {
			if (Character.isLetter(val.charAt(0))) {
				return true;
			}
		}
		return false;

	}

	public static boolean isValidEmail(String email) {
		/** Set the email pattern string */
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");

		/** Match the given string with the pattern */
		Matcher m = p.matcher(email);

		/** check whether match is found */
		return m.matches();
	}

	public static boolean isEmpty(String val) {
		if (val == null || val.equals("")) {
			return true;
		}
		return false;
	}

	public static boolean isInvalidListItem(String val) {
		if (val == null || val.equals("-1")) {
			return true;
		}
		return false;
	}

	public static boolean isInvalidListItem(Long val) {
		if (val == null || val.equals(-1L)) {
			return true;
		}
		return false;
	}

	public static boolean isValidZipCode(String zip) {

		return zip.matches("\\d{5}");
	}

	public static boolean isValidZipCode(Long zip) {
		if (zip == null)
			return false;
		return zip.toString().matches("\\d{5}");
	}

	public static boolean isNotValidNumber(Long val) {
		if (val == null) {
			return true;
		}
		if (val.equals(-1L)) {
			return true;
		}
		return false;
	}

	private void appendErrorMessage(StringBuffer errorMessage, String key) {
		errorMessage.append(this.getText("common_error_prefix")).append(" ")
				.append(this.getText(key)).append("<br />");
		;
	}

	public boolean validateMember(Member member, String memberType,
			String action, String confirmPass, StringBuffer errorMessage,
			StringBuffer extraParam) {

		/*
		 * Check the extraParam value for chcking the validation is for profile
		 * or not (P for profile)
		 */
		String profile = extraParam.toString();
		extraParam.setLength(0);
		String selTabName = "";
		if (member == null) {
			this.appendErrorMessage(errorMessage, "common_system_error");
		} else {
			/** Check email address */
			if (action.equals(ServiceConstants.ADD)) {
				if (!CommonValidator.isValidEmail(member.getEmail())) {
					selTabName = "idLoginInfoTab";
					this.appendErrorMessage(errorMessage,
							"member_validation_email_address");
				}
				if (!CommonValidator.isValidPassword(member.getPassword())) {
					if (isEmpty(selTabName)) {
						selTabName = "idLoginInfoTab";
					}
					this.appendErrorMessage(errorMessage,
							"member_validation_password");
				}
				if (!member.getPassword().equals(confirmPass)) {
					if (isEmpty(selTabName)) {
						selTabName = "idLoginInfoTab";
					}
					this.appendErrorMessage(errorMessage,
							"member_validation_password_dont_match");
				}
			}
			if (member.getTypeId() == null || member.getTypeId() == 0) {
				if (isEmpty(selTabName)) {
					selTabName = "idBasicInformation";
				}
				this.appendErrorMessage(errorMessage,
						"validation_msg_usertype_required");
			}
			if (CommonValidator.isEmpty(member.getFirstName())) {
				if (isEmpty(selTabName)) {
					selTabName = "idBasicInformation";
				}
				this.appendErrorMessage(errorMessage,
						"member_validation_first_name");
			}
			/** Check the last name */
			if (CommonValidator.isEmpty(member.getLastName())) {
				if (isEmpty(selTabName)) {
					selTabName = "idBasicInformation";
				}
				this.appendErrorMessage(errorMessage,
						"member_validation_last_name");
			}

			if (CommonValidator.isEmpty(member.getCity())) {
				if (isEmpty(selTabName)) {
					selTabName = "idBasicInformation";
				}
				this.appendErrorMessage(errorMessage, "member_validation_city");
			}
			if (member.getState().equals("-1")) {
				if (isEmpty(selTabName)) {
					selTabName = "idBasicInformation";
				}
				this.appendErrorMessage(errorMessage, "member_validation_state");
			}
			/** Check the Zip code */
			if (!CommonValidator.isValidZipCode(member.getZip())) {
				if (isEmpty(selTabName)) {
					selTabName = "idBasicInformation";
				}
				this.appendErrorMessage(errorMessage,
						"member_validation_zip_code");
			}
			if (member.getEthinicity() == -1) {
				if (isEmpty(selTabName)) {
					selTabName = "idDetailsInfoTab";
				}
				this.appendErrorMessage(errorMessage,
						"member_validation_ethinicity");
			}
/*			if (member.getMaritalStatus() != null
					&& member.getMaritalStatus().equals("-1")
					&& !memberType.equals(ServiceConstants.PROTEGE)) {
				if (isEmpty(selTabName)) {
					selTabName = "idDetailsInfoTab";
				}
				this.appendErrorMessage(errorMessage,
						"member_validation_marital_status");
			}*/

			/** Check the member gender */
			if (CommonValidator.isEmpty(member.getGenderInd())) {
				if (isEmpty(selTabName)) {
					selTabName = "idDetailsInfoTab";
				}
				this.appendErrorMessage(errorMessage,
						"member_validation_gender");
			}

			/** Check the Year of birth */
			if (CommonValidator.isNotValidNumber(member.getBirthYear())) {
				if (isEmpty(selTabName)) {
					selTabName = "idDetailsInfoTab";
				}
				this.appendErrorMessage(errorMessage,
						"member_validation_birth_year");
			}

			/** Check the member profession */
			if (member.getProfession().equals("-1")) {
				if (isEmpty(selTabName)) {
					selTabName = "idDetailsInfoTab";
				}
				this.appendErrorMessage(errorMessage,
						"member_validation_profession");
			}

/*			if (!memberType.equals(ServiceConstants.PROTEGE)
					&& member.getMazhab().equals("-1")) {
				if (isEmpty(selTabName)) {
					selTabName = "idDetailsInfoTab";
				}
				this.appendErrorMessage(errorMessage,
						"member_validation_madhab");
			}*/

			if (action.equals(ServiceConstants.ADD)) {
				if (CommonValidator.isEmpty(member.getAgreePrivacyPolicy())
						|| member.getAgreePrivacyPolicy().equals("false")) {
					if (isEmpty(selTabName)) {
						selTabName = "idDetailsInfoTab";
					}
					this.appendErrorMessage(errorMessage,
							"member_validation_licence_agree");
				}
			}

			if (CommonValidator.isEmpty(member.getSecurityQuestion1())) {
				if (isEmpty(selTabName)) {
					selTabName = "seqQueTab";
				}
				this.appendErrorMessage(errorMessage,
						"change_password_security_question1_chose");
			}
			if (CommonValidator.isEmpty(member.getSecurityQuestionAns1())) {
				if (isEmpty(selTabName)) {
					selTabName = "seqQueTab";
				}
				this.appendErrorMessage(errorMessage,
						"change_password_security_question1_empty");
			}
			if (CommonValidator.isEmpty(member.getSecurityQuestion2())) {
				if (isEmpty(selTabName)) {
					selTabName = "seqQueTab";
				}
				this.appendErrorMessage(errorMessage,
						"change_password_security_question2_chose");
			}
			if (CommonValidator.isEmpty(member.getSecurityQuestionAns2())) {
				if (isEmpty(selTabName)) {
					selTabName = "seqQueTab";
				}
				this.appendErrorMessage(errorMessage,
						"change_password_security_question2_empty");
			}
		}

		if (profile.equals("P") && !isEmpty(selTabName)
				&& !selTabName.equals("seqQueTab")) {
			selTabName = "idBasicInformation";
		}

		extraParam.append(selTabName);

		return isEmpty(errorMessage.toString());
	}
}
