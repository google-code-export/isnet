package com.intrigueit.myc2i.security;

public class MentorTopMenu {
	
	private boolean dashBoard;
	
	private boolean myAccount;
	
	private boolean myResource;
	
	private boolean askQuestion;
	
	private boolean subScription;
	
	private boolean donate;

	public MentorTopMenu(){
		this.dashBoard = true;
		this.myAccount = true;
		this.myResource = true;
		this.askQuestion = true;
		this.subScription = true;
		this.donate = true;
	}
	public boolean isDashBoard() {
		return dashBoard;
	}

	public void setDashBoard(boolean dashBoard) {
		this.dashBoard = dashBoard;
	}

	public boolean isMyAccount() {
		return myAccount;
	}

	public void setMyAccount(boolean myAccount) {
		this.myAccount = myAccount;
	}

	public boolean isMyResource() {
		return myResource;
	}

	public void setMyResource(boolean myResource) {
		this.myResource = myResource;
	}

	public boolean isAskQuestion() {
		return askQuestion;
	}

	public void setAskQuestion(boolean askQuestion) {
		this.askQuestion = askQuestion;
	}

	public boolean isSubScription() {
		return subScription;
	}

	public void setSubScription(boolean subScription) {
		this.subScription = subScription;
	}

	public boolean isDonate() {
		return donate;
	}

	public void setDonate(boolean donate) {
		this.donate = donate;
	}
	

}
