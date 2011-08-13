package com.intrigueit.myc2i.trainingitem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intrigueit.myc2i.trainingitem.dao.TrainingItemDao;
import com.intrigueit.myc2i.trainingitem.domain.TrainingItem;

@Service
public class TrainingItemServiceImpl implements TrainingItemService {

	private TrainingItemDao trainingItemDao;

	@Autowired
	public TrainingItemServiceImpl(TrainingItemDao trainingItemDao) {
		this.trainingItemDao = trainingItemDao;
	}

	public List<TrainingItem> loadAll() {
		List<TrainingItem> categoryList = trainingItemDao.loadAll();
		return categoryList;
	}

	public TrainingItem loadById() {
		// return trainingItemDao.loadById(recordId);
		return null;
	}

	public TrainingItem loadById(Long recordId) {
		return trainingItemDao.loadById(recordId);
	}

	public void updateTrainingItem(TrainingItem trainingItem) {
		trainingItemDao.update(trainingItem);
	}

	public void addTrainingItem(TrainingItem trainingItem) {
		trainingItemDao.persist(trainingItem);
	}

	public void deleteTrainingItem(Long recordId) {
		TrainingItem trainingItem = trainingItemDao.loadById(recordId);
		trainingItemDao.delete(trainingItem);
	}

	public ArrayList<String> getCategories() {
		return trainingItemDao.getCategories();
	}

	public List<TrainingItem> findByProperty(String propertyName, Object value) {
		String clause = " t." + propertyName + " like ?1 ";
		return trainingItemDao.loadByClause(clause, new Object[] { value });
	}

	public List<TrainingItem> findByProperties(String vendorId, String itemEnd) {
		StringBuffer clause = new StringBuffer();
		clause
				.append(
						" SELECT NEW TrainingItem(t.itemId,t.itemDescription,t.itemEIndicator,")
				.append(
						" t.itemSubscriptionInd,t.itemEStorageLocati,t.itemPurchaseCost,")
				.append(" t.itemSalesPrice,t.itemVersion,t.itemLanguage,")
				.append(" t.itemAvailability)").append(" FROM TrainingItem t");
		boolean useWhere = true;
		if (vendorId != null && !vendorId.equals("")) {
			clause.append(" where t.vendorId =" + vendorId);
			useWhere = false;
		}
		if (itemEnd != null && !itemEnd.equals("")) {
			if (useWhere) {
				clause.append(" where ");
			} else {
				clause.append(" and ");
			}
			clause.append(" t.itemEIndicator ='" + itemEnd + "'");
		}
		return trainingItemDao.findByProperties(clause.toString());
	}

	public boolean isCategoryExist(Long recordId, String categoryName,
			String itemNAme) {
		List<Object> value = new ArrayList<Object>();
		StringBuffer clause = new StringBuffer();
		clause.append(" t.itemEIndicator = ?1").append(
				" and t.itemDescription = ?2");
		value.add(categoryName);
		value.add(itemNAme);
		if (recordId != null && recordId != 0) {
			clause.append(" and t.itemId != ?3");
			value.add(recordId);
		}
		return trainingItemDao.isDuplicateRecord(clause.toString(), value
				.toArray());
	}

	public List<TrainingItem> getMentorBooks() {
		String clause = " t.itemEIndicator =?1 and t.itemEStorageLocati=?2 ";
		return trainingItemDao.loadByClause(clause, new Object[] { "BOOK",
				"ST001" });
	}
}
