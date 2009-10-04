package com.intrigueit.myc2i.common.autocomplete;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intrigueit.myc2i.common.ServiceConstants;
import com.intrigueit.myc2i.common.dao.GenericDaoImpl;
import com.intrigueit.myc2i.member.domain.Member;

@Transactional
@Repository
public class CommonSearchDaoImpl extends GenericDaoImpl<CommonSearchDataTmp,Long> implements CommonSearchDao{	
  /**
	 *
	 */
	private static final long serialVersionUID = -1291399822187627845L;
	private static final Logger log = Logger.getLogger(CommonSearchDaoImpl.class);
	private int totalResult = 100;
	
	public ArrayList<CommonSearchDataTmp> requestProcess(String tableName,String searchText, String extConds) {
		ArrayList<CommonSearchDataTmp> resultList = new ArrayList<CommonSearchDataTmp>();
		try {
			totalResult = 100;
			if(tableName!=null) {
				if (tableName.equals(ServiceConstants.MEMBER_TABLE)) {
					resultList = searchMember(searchText);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

  @SuppressWarnings("unchecked")
  private ArrayList<CommonSearchDataTmp> searchMember(String searchText) throws Exception {
  	ArrayList<CommonSearchDataTmp> resultList = new ArrayList<CommonSearchDataTmp>();
  	if (searchText == null || searchText.equals(""))
      return resultList;
  	List<Object> value = new ArrayList<Object>();
    StringBuffer clause = new StringBuffer();
    clause.append(" upper(t.firstName) like ?1");
    value.add("%"+searchText.toUpperCase()+"%");
    List recordList = this.loadByClause("MEMBER", clause.toString(), value.toArray()); 
    if (recordList!= null && !recordList.isEmpty()) {
      Iterator it = recordList.iterator();
      while (it.hasNext()) {
        Member obj = (Member) it.next();
        resultList.add(new CommonSearchDataTmp(""+obj.getMemberId(),obj.getFirstName(),obj.getFirstName()));
      }
    }
    return resultList;
  }
  
  @SuppressWarnings("unchecked")
  public List loadByClause(String searchObj,String clause,Object[] params){
    String hsql = "Select t from " + searchObj + " t where "+ clause;
    log.debug(hsql);
    Query query = entityManager.createQuery(hsql);
    /** bind parameters */
    for (int i=0; params!=null && i<params.length; i++ ) {
      query.setParameter(i+1, params[i] );
    }
    return query.getResultList();
  }
}
