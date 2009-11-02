package com.intrigueit.myc2i.qa.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.qa.IslamCityQuestion;
import com.intrigueit.myc2i.qa.SimpleDataExtractor;

@Component("questionViewHandler")
@Scope("session")	
public class QuestionViewHandler extends BasePage implements Serializable {

	private String questionNo;
	
	private String topic;
	
	private String term;
	
	private String actionURL;
	
	private List<IslamCityQuestion> questions;
	
	
	public QuestionViewHandler() {
		this.questions = new ArrayList<IslamCityQuestion>();
	}

	/** Call search in Islam City */
	public void executeSearch(){
		try{
			if(this.getTopic().equals("")){
				return;
			}
			log.debug( this.getActionURL());
			SimpleDataExtractor de = new SimpleDataExtractor(this.getActionURL());
			this.questions = de.extract();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void resetFields(){
		this.questionNo = "";
		this.topic = "";
		this.term = "";
	}

	public String getQuestionNo() {
		return questionNo;
	}

	public void setQuestionNo(String questionNo) {
		this.questionNo = questionNo;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getActionURL() {
		this.actionURL = "http://www.islamicity.com/qa/action.lasso.asp?-op=eq&-db=services&-lay=Ask&Answer_flag=X&-format=result.asp&-Error=result.asp&-Sortfield=Question_Date&-Sortorder=Descending&-Max=100&-op=eq&Adminfilter=2&-op=eq&Number="+ this.getQuestionNo() +"&-op=cn&Topic="+ this.getTopic() +"&-op=cn&Search_t="+ this.getTerm() +"&-find=Start+Search";
		return actionURL;
	}

	public void setActionURL(String actionURL) {
		this.actionURL = actionURL;
	}

	public List<IslamCityQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(List<IslamCityQuestion> questions) {
		this.questions = questions;
	}
}
