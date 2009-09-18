package com.intrigueit.myc2i.dashboard.view;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.view.BasePage;

@Component("dashboardViewHandler")
@Scope("session")
public class DashboardViewHandler extends BasePage implements Serializable{

	private String mostVotedStory;
	
	private String winningStory;

	/**
	 * 
	 */
	public DashboardViewHandler() {
		this.mostVotedStory = "Quoting a witness from \"Balkh\", in his famous work entitled \"Kafi\", the celebrated scholar \"Kulayni\" relates the following";
		this.winningStory = "Winner: Ahmmed Ibne Jafer Quoting a witness from \"Balkh\", in his famous work entitled \"Kafi\", the celebrated scholar \"Kulayni\" relates the following ";
		
	}

	public String getMostVotedStory() {
		return mostVotedStory;
	}

	public void setMostVotedStory(String mostVotedStory) {
		this.mostVotedStory = mostVotedStory;
	}

	public String getWinningStory() {
		return winningStory;
	}

	public void setWinningStory(String winningStory) {
		this.winningStory = winningStory;
	}
	
	
}
