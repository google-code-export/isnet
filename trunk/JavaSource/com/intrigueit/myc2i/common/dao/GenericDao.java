package com.intrigueit.myc2i.common.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, ID extends Serializable> {
	  
	T loadById(ID id);
	
	void persist(T entity);
	
	void update(T entity);
	
	void delete(T entity);
	
	List<T> loadAll();
	
	List<T> loadByClause(String clause,Object[] params);

}
