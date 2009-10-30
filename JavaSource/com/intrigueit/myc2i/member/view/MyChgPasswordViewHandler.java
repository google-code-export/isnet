package com.intrigueit.myc2i.member.view;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.ServiceConstants;
import com.intrigueit.myc2i.common.utility.CryptographicUtility;
import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.common.view.CommonValidator;
import com.intrigueit.myc2i.member.domain.Member;
import com.intrigueit.myc2i.member.service.MemberService;

@Component("myChgPasswordViewHandler")
@Scope("session")
public class MyChgPasswordViewHandler extends BasePage implements Serializable {

	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -8195696485898880496L;

	/** Initialize the Logger */
	protected static final Logger logger = Logger
			.getLogger(MyChgPasswordViewHandler.class);

	private MemberService memberService;
	private Member currentMember;

	private String oldPassword;

	private String newPassword;

	private String confirmPassword;

	/** Available transfer methods */
	@Autowired
	public MyChgPasswordViewHandler(MemberService memberService) {
		this.memberService = memberService;
		this.initialize();
	}

	public void initialize() {
		setSecHeaderMsg("");
		loadMember();
	}

	public void loadMember() {
		try {
			logger.debug(" Load Member ");
			Long recordId = this.getMember().getMemberId();
			this.currentMember = memberService.findById(recordId);
			this.setActionType(ServiceConstants.UPDATE);
		} catch (Exception ex) {
			logger.error("Unable to load Members:" + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	private String appendErrorMessage(String key){
		String msg = this.getText(key);
		String prefix = this.getText("common_error_prefix");
		return "".concat("<br/>").concat(prefix).concat(msg);
	}
	public boolean validated() throws Exception{
		StringBuffer errorMessage = new StringBuffer();
		CryptographicUtility crp = new CryptographicUtility();
		boolean isValid = true;
		
		if (this.currentMember == null) {
			errorMessage.append(this.getText("common_system_error"));
			isValid = false;	
		}
		String oldPass = crp.getDeccryptedText(this.currentMember.getPassword());
		if (oldPass == null || !oldPass.equals(this.getOldPassword())) {
			errorMessage.append(this.appendErrorMessage("change_password_validation_old_password_invalid"));
			isValid = false;	
		}
		
		if (!CommonValidator.isValidPassword(this.getNewPassword())) {
			errorMessage.append(this.appendErrorMessage("member_validation_password"));
			isValid = false;			
		}
		if (!CommonValidator.isValidPassword(this.getConfirmPassword())) {
			errorMessage.append(this.appendErrorMessage("member_validation_password"));
			isValid = false;			
		}
		if(!this.getConfirmPassword().equals(this.getNewPassword())){
			errorMessage.append(this.appendErrorMessage("member_validation_password_dont_match"));
			isValid = false;			
		}
		if(!isValid) {
			setErrorMessage(this.getText("common_error_header")+ errorMessage.toString());
		}
		
		return isValid;
	}
	public boolean xxxxvalidate() throws Exception {
		logger.debug(" Validating member ");
		boolean flag = true;
		StringBuffer errorMessage = new StringBuffer();
		if (this.currentMember == null) {
			errorMessage.append(this.getText("common_system_error"));
			flag = false;
		} else {
			boolean isPassNotEmpty = true;
			if (CommonValidator.isEmpty(this.getOldPassword())) {
				isPassNotEmpty = false;
				if (!flag)
					errorMessage.append("<br />");
				errorMessage.append(this.getText("common_error_prefix"))
						.append(" ").append(
								this.getText("member_validation_old_password"));
				flag = false;
			}
			if (CommonValidator.isEmpty(this.getNewPassword())) {
				isPassNotEmpty = false;
				if (!flag)
					errorMessage.append("<br />");
				errorMessage.append(this.getText("common_error_prefix"))
						.append(" ").append(
								this.getText("member_validation_new_password"));
				flag = false;
			}
			if (CommonValidator.isEmpty(this.getConfirmPassword())) {
				isPassNotEmpty = false;
				if (!flag)
					errorMessage.append("<br />");
				errorMessage
						.append(this.getText("common_error_prefix"))
						.append(" ")
						.append(
								this
										.getText("member_validation_confirm_password"));
				flag = false;
			}
			if (isPassNotEmpty
					&& !this.getConfirmPassword().equals(this.getNewPassword())) {
				if (!flag)
					errorMessage.append("<br />");
				errorMessage
						.append(this.getText("common_error_prefix"))
						.append(" ")
						.append(
								this
										.getText("member_validation_password_dont_match"));
				flag = false;
			}
			if (flag) {
				CryptographicUtility crp = new CryptographicUtility();
				String oldPass = crp.getDeccryptedText(this.currentMember
						.getPassword());
				if (oldPass == null || !oldPass.equals(this.getOldPassword())) {
					errorMessage
							.append(this.getText("common_error_prefix"))
							.append(" ")
							.append(
									this
											.getText("user_password_miss_match_msg"));
					flag = false;
				}
				if (!this.getNewPassword().equals(this.getConfirmPassword())) {
					if (!flag)
						errorMessage.append("<br />");
					errorMessage
							.append(this.getText("common_error_prefix"))
							.append(" ")
							.append(
									this
											.getText("user_oldpass_mewpass_notmatch"));
					flag = false;
				}
			}
		}
		if (!flag)
			setErrorMessage(this.getText("common_error_header")
					+ errorMessage.toString());
		return flag;
	}

	public void clearForm() {
		this.setOldPassword("");
		this.setNewPassword("");
		this.setConfirmPassword("");
	}

	public void resetUserPassword() {
		logger.debug(" Change user password ");
		setErrorMessage("");
		try {
			if (validated()) {
				CryptographicUtility crp = new CryptographicUtility();
				this.currentMember.setPassword(crp.getEncryptedText(this.getNewPassword()));
				memberService.update(this.currentMember);
				this.setErrorMessage(this.getText("update_password_success_message"));
				this.setMsgType(ServiceConstants.INFO);
			}
		} catch (Exception e) {
			setErrorMessage(this.getText("common_system_error"));
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @return the currentMember
	 */
	public Member getCurrentMember() {
		if (currentMember == null) {
			System.out.println(" Init member ");
			currentMember = new Member();
		}
		return currentMember;
	}

	/**
	 * @return the oldPassword
	 */
	public String getOldPassword() {
		return oldPassword;
	}

	/**
	 * @param oldPassword
	 *            the oldPassword to set
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * @param newPassword
	 *            the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPassword
	 *            the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
