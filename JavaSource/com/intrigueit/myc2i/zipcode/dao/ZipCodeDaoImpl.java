package com.intrigueit.myc2i.zipcode.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intrigueit.myc2i.common.dao.GenericDaoImpl;
import com.intrigueit.myc2i.zipcode.domain.ZipCode;

@Repository
@Transactional
public class ZipCodeDaoImpl  extends GenericDaoImpl<ZipCode,String> implements ZipCodeDao{

}
