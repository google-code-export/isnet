package com.intrigueit.myc2i.common.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, ID extends Serializable> {

	T loadById(ID id);

	void persist(T entity);

	void update(T entity);

	void delete(T entity);

	List<T> loadAll();
	
	List<T> loadByQuery(String sql, Object[] params);
	
	List<T> loadByClause(String clause, Object[] params);
	
	List<T> loadTopResultsByClause(int top, String clause, Object[] params);
	
	List<T> loadTopResultsByConditions(int top,String clause, Object[] params);

	List<T> loadTopResultsByConditions(int first,int resultSize,String clause, Object[] params);
	
	boolean isDuplicateRecord(String clause);

	boolean isDuplicateRecord(String clause, Object[] params);
	
	public Integer deleteByClause(String clause, Object[] params);
}
