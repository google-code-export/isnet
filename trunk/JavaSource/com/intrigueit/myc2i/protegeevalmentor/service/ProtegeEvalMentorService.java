package com.intrigueit.myc2i.protegeevalmentor.service;
// default package

import java.util.List;

import com.intrigueit.myc2i.protegeevalmentor.domain.ProtegeEvaluationOfMentor;


public interface ProtegeEvalMentorService {


  public void save(ProtegeEvaluationOfMentor entity);

  public void delete(ProtegeEvaluationOfMentor entity);

	public ProtegeEvaluationOfMentor update(ProtegeEvaluationOfMentor entity);
	
	public ProtegeEvaluationOfMentor findById( Long id);

	public List<ProtegeEvaluationOfMentor> findByProperty(String propertyName, Object value);

	public List<ProtegeEvaluationOfMentor> findAll();	
	
}