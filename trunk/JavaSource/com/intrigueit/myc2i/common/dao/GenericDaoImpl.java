package com.intrigueit.myc2i.common.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class GenericDaoImpl<T, ID extends Serializable> implements
		GenericDao<T, ID> {

	protected final Log log = LogFactory.getLog(getClass());
	private Class<T> persistentClass;

	protected EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public GenericDaoImpl() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	public T loadById(ID id) {
		return entityManager.find(persistentClass, id);
	}

  public void persist(T entity) {
    entityManager.persist(entity);
    entityManager.flush();
    entityManager.refresh(entity);
  }

	public void update(T entity) {
		Object ob = entityManager.merge(entity);
		entityManager.flush();
		entityManager.refresh(ob);
	}

	public void delete(T entity) {
		entityManager.remove(entity);
	}
	 
	@SuppressWarnings("unchecked")
	public List<T> loadAll() {
		return entityManager.createQuery(
				"Select t from " + persistentClass.getName() + " t")
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> loadByClause(String clause, Object[] params) {
		String hsql = "Select t from " + persistentClass.getName()
				+ " t where " + clause;
		//log.debug(hsql);
		Query query = entityManager.createQuery(hsql);
		/** bind parameters */
		for (int i = 0; params != null && i < params.length; i++) {
			query.setParameter(i + 1, params[i]);
		}
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> loadByQuery(String sql, Object[] params) {
		Query query = entityManager.createQuery(sql);
		/** bind parameters */
		for (int i = 0; params != null && i < params.length; i++) {
			query.setParameter(i + 1, params[i]);
		}
		return query.getResultList();
	}	
	@SuppressWarnings("unchecked")
	public List<T> loadTopResultsByClause(int top,String clause, Object[] params) {
		String hsql = "Select t from " + persistentClass.getName()
				+ " t where " + clause;
		//log.debug(hsql);
		Query query = entityManager.createQuery(hsql);
		query.setMaxResults(top);
		/** bind parameters */
		for (int i = 0; params != null && i < params.length; i++) {
			query.setParameter(i + 1, params[i]);
		}
		return query.getResultList();
	}
	public List<T> loadTopResultsByConditions(int top,String clause, Object[] params) {
		String hsql = "Select t from " + persistentClass.getName()
				+ " t " + clause;
		//log.debug(hsql);
		Query query = entityManager.createQuery(hsql);
		query.setMaxResults(top);
		/** bind parameters */
		for (int i = 0; params != null && i < params.length; i++) {
			query.setParameter(i + 1, params[i]);
		}
		return query.getResultList();
	}	
	public List<T> loadTopResultsByConditions(int first,int resultSize,String clause, Object[] params) {
		String hsql = "Select t from " + persistentClass.getName()
				+ " t " + clause;
		Query query = entityManager.createQuery(hsql);
		query.setFirstResult(first);
		query.setMaxResults(resultSize);
		/** bind parameters */
		for (int i = 0; params != null && i < params.length; i++) {
			query.setParameter(i + 1, params[i]);
		}
		return query.getResultList();
	}
	@SuppressWarnings("unchecked")
	public boolean isDuplicateRecord(String clause) {
		String hsql = "Select t from " + persistentClass.getName()
				+ " t where " + clause;
		//log.debug(hsql);
		Query query = entityManager.createQuery(hsql);
		List<T> recordList = query.getResultList();
		return recordList.size() > 0;
	}

	@SuppressWarnings("unchecked")
	public boolean isDuplicateRecord(String clause, Object[] params) {
		String hsql = "Select t from " + persistentClass.getName()
				+ " t where " + clause;
		//log.debug(hsql);
		Query query = entityManager.createQuery(hsql);
		/** bind parameters */
		for (int i = 0; params != null && i < params.length; i++) {
			query.setParameter(i + 1, params[i]);
		}
		List<T> recordList = query.getResultList();
		return recordList.size() > 0;
	}
	
	public Integer deleteByClause(String clause, Object[] params) {
	    String hsql = "DELETE FROM " + persistentClass.getName()
	        + " t where " + clause;
	    //log.debug(hsql);
	    Query query = entityManager.createQuery(hsql);
	    /** bind parameters */
	    for (int i = 0; params != null && i < params.length; i++) {
	      query.setParameter(i + 1, params[i]);
	    }
	    //log.debug(query.toString());
	    return query.executeUpdate();
  }
	public int getRowCount(String clause, Object[] params) {
		String hsql = "Select count(t)  from " + persistentClass.getName()
		+ " t where " + clause;
		//log.debug(hsql);
		Query query = entityManager.createQuery(hsql);
		/** bind parameters */
		for (int i = 0; params != null && i < params.length; i++) {
			query.setParameter(i + 1, params[i]);
		}
		List<T> recordList = query.getResultList();
		int count = 0;
		if(recordList != null && recordList.size() > 0){
			count = Integer.parseInt(recordList.get(0).toString());
		}
		 
		return count;
	}
}
