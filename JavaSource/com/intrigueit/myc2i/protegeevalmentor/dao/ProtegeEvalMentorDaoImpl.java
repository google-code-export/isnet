package com.intrigueit.myc2i.protegeevalmentor.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intrigueit.myc2i.common.dao.GenericDaoImpl;
import com.intrigueit.myc2i.protegeevalmentor.domain.ProtegeEvaluationOfMentor;

@Repository
@Transactional
public class ProtegeEvalMentorDaoImpl extends
    GenericDaoImpl<ProtegeEvaluationOfMentor, Long> implements
    ProtegeEvalMentorDao {

}
