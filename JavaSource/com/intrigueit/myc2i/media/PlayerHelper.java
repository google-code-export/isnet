package com.intrigueit.myc2i.media;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.intrigueit.myc2i.tutorial.domain.TestTutorialQuestionAns;


public class PlayerHelper {

	/** Return the first index of the question and answer */
	public static int getModuleQuestionStartIndex(List<TestTutorialQuestionAns> module){
		
		for(int index = 0; index<module.size(); index ++){
			TestTutorialQuestionAns question = module.get(index);
			if(hasQuestion(question)){
				return index;
			}
		}
		return module.size()-1;
	}
	/**
	 * Check if the record has any question answer.
	 * 
	 * @param tQuestionAns
	 * @return
	 */
	public static boolean hasQuestion(TestTutorialQuestionAns tQuestionAns) {
		if (tQuestionAns.getQuestion() != null
				&& !tQuestionAns.getQuestion().equals("")) {
			return true;
		}
		return false;
	}
	public static List<Integer> createRandomQuestionList(Integer startIndex,Integer endIndex,int size){
		
		List<Integer> questions = new ArrayList<Integer>();
		 
		for(int index = startIndex; index <= endIndex; index++){
			questions.add(Integer.valueOf(index));
		}
		
		Collections.shuffle(questions);
		if(questions.size() <= size){
			size = questions.size()-1;
		}
		return questions.subList(0, size);
	}
}
