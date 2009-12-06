package com.intrigueit.myc2i.tutotialtest.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intrigueit.myc2i.common.dao.GenericDaoImpl;
import com.intrigueit.myc2i.tutotialtest.domain.TestResult;

@Repository
@Transactional
public class TestResultDaoImpl extends GenericDaoImpl<TestResult,Long> implements TestResultDao{

}
