package com.intrigueit.myc2i.qa;

public class IslamCityQuestion {

	private String detailsQnALink;
	
	private String questionReference;
	
	private String questionDetails;
	
	private String questionNo;
	
	public String getDetailsQnALink() {
		return detailsQnALink;
	}

	public void setDetailsQnALink(String detailsQnALink) {
		this.detailsQnALink = detailsQnALink;
	}

	public String getQuestionReference() {
		return questionReference;
	}

	public void setQuestionReference(String questionReference) {
		this.questionReference = questionReference;
	}

	public String getQuestionDetails() {
		return questionDetails;
	}

	public void setQuestionDetails(String questionDetails) {
		this.questionDetails = questionDetails;
	}
	public String getQuestion() {
		int start = this.questionDetails.indexOf("Question:")+ 10 ;
		int end = this.questionDetails.length() - 5;
		return this.questionDetails.substring(start, end);
	}
	@Override
	public String toString() {
		return "Topic: "+ this.getTopic() +" :: "+ this.getQuestion() + " :: "+questionReference +" :: "+questionDetails ;
	}

	public String getTopic() {
		int end = this.questionDetails.indexOf("Question:");
		return this.questionDetails.substring(6,end);
	}

	public String getQuestionNo() {
		return questionNo;
	}

	public void setQuestionNo(String questionNo) {
		this.questionNo = questionNo;
	}
	
}
