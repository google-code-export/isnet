package com.intrigueit.myc2i.protegeevalmentor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intrigueit.myc2i.protegeevalmentor.dao.ProtegeEvalMentorDao;
import com.intrigueit.myc2i.protegeevalmentor.domain.ProtegeEvaluationOfMentor;

@Service
public class ProtegeEvalMentorServiceImpl implements ProtegeEvalMentorService {

  private ProtegeEvalMentorDao protegeEvalMentorDao;

  @Autowired
  public ProtegeEvalMentorServiceImpl(ProtegeEvalMentorDao protegeEvalMentorDao) {
    this.protegeEvalMentorDao = protegeEvalMentorDao;
  }

  public void delete(ProtegeEvaluationOfMentor entity) {
    protegeEvalMentorDao.delete(entity);
  }

  public List<ProtegeEvaluationOfMentor> findAll() {
    return protegeEvalMentorDao.loadAll();
  }

  public ProtegeEvaluationOfMentor findById(Long id) {
    return protegeEvalMentorDao.loadById(id);
  }

  public List<ProtegeEvaluationOfMentor> findByProperty(String propertyName, Object value) {
    String clause = " t." + propertyName + " = ?1 ";
    return protegeEvalMentorDao.loadByClause(clause, new Object[] { value });
  }

  public void save(ProtegeEvaluationOfMentor entity) {
    protegeEvalMentorDao.persist(entity);

  }

  public ProtegeEvaluationOfMentor update(ProtegeEvaluationOfMentor entity) {
    protegeEvalMentorDao.update(entity);
    return entity;
  }
}
