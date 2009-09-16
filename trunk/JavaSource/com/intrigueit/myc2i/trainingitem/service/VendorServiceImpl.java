package com.intrigueit.myc2i.trainingitem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intrigueit.myc2i.trainingitem.dao.VendorDao;
import com.intrigueit.myc2i.trainingitem.domain.ItemVendor;

@Service
public class VendorServiceImpl implements VendorService {

	private VendorDao vendorDao;
	
	@Autowired
	public VendorServiceImpl(VendorDao vendorDao) {
		this.vendorDao = vendorDao;
	}

	public List<ItemVendor> loadAll() {
		List<ItemVendor> categoryList= vendorDao.loadAll();
		return categoryList;
	}
	
	public ItemVendor loadById(Long recordId) {
		return vendorDao.loadById(recordId);
	}
	public void updateVendor (ItemVendor vendor) {
		vendorDao.update(vendor);
	}
	public void addVendor (ItemVendor vendor) {
		vendorDao.persist(vendor);
	}

	public void deleteVendor(Long recordId) {
		ItemVendor trainingItem = vendorDao.loadById(recordId);
		vendorDao.delete(trainingItem);		
	}
	
	public ArrayList<String> getCategories() {   
    return vendorDao.getCategories();
  }
  public List<ItemVendor> findByProperty(String propertyName, Object value) {
    String clause = " t."+propertyName+" like ?1 ";
    return vendorDao.loadByClause(clause, new Object[]{value});
  }
}
