package com.intrigueit.myc2i.tutotialtest.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intrigueit.myc2i.common.dao.GenericDaoImpl;
import com.intrigueit.myc2i.tutotialtest.domain.TestResultDetails;

@Repository
@Transactional
public class TestResultDetailsDaoImpl extends GenericDaoImpl<TestResultDetails,Long> implements TestResultDetailsDao{

}
