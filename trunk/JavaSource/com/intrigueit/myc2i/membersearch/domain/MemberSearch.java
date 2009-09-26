package com.intrigueit.myc2i.membersearch.domain;

public class MemberSearch {

	private Boolean isProfession;
	
	private Boolean isEhinicity;
	
	private Boolean isMaritalStatus;
	
	private Boolean isYearOfBirth;

	
	/**
	 * @param isProfession
	 * @param isEhinicity
	 * @param isMaritalStatus
	 * @param isYearOfBirth
	 */
	public MemberSearch(Boolean isProfession, Boolean isEhinicity,
			Boolean isMaritalStatus, Boolean isYearOfBirth) {
		this.isProfession = isProfession;
		this.isEhinicity = isEhinicity;
		this.isMaritalStatus = isMaritalStatus;
		this.isYearOfBirth = isYearOfBirth;
	}

	public Boolean getIsProfession() {
		return isProfession;
	}

	public void setIsProfession(Boolean isProfession) {
		this.isProfession = isProfession;
	}

	public Boolean getIsEhinicity() {
		return isEhinicity;
	}

	public void setIsEhinicity(Boolean isEhinicity) {
		this.isEhinicity = isEhinicity;
	}

	public Boolean getIsMaritalStatus() {
		return isMaritalStatus;
	}

	public void setIsMaritalStatus(Boolean isMaritalStatus) {
		this.isMaritalStatus = isMaritalStatus;
	}

	public Boolean getIsYearOfBirth() {
		return isYearOfBirth;
	}

	public void setIsYearOfBirth(Boolean isYearOfBirth) {
		this.isYearOfBirth = isYearOfBirth;
	}
	
	
}
