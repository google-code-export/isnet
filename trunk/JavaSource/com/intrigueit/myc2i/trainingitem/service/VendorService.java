package com.intrigueit.myc2i.trainingitem.service;

// default package

import java.util.ArrayList;
import java.util.List;

import com.intrigueit.myc2i.trainingitem.domain.ItemVendor;

public interface VendorService {
	public List<ItemVendor> loadAll();

	public ItemVendor loadById(Long recordId);

	public void addVendor(ItemVendor itemVendor);

	public void updateVendor(ItemVendor itemVendor);

	public void deleteVendor(Long recordId);

	public ArrayList<String> getCategories();

	public List<ItemVendor> findByProperty(String propertyName, Object value);

	public boolean isRecordExist(Long recordId, String vendorName);
}