package com.intrigueit.myc2i.masjid.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.masjid.dao.MasjidDao;
import com.intrigueit.myc2i.masjid.domain.Masjid;

@Component("masjidViewHandler")
@Scope("session")
public class MasjidViewHandler extends BasePage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MasjidDao masjidDao;
	
	private List<Masjid> masjids  = new ArrayList<Masjid>();
	
	private String masjidName;
	
	private void searchMasjid(){
		
	}

	public List<Masjid> getMasjids() {
		return masjids;
	}

	public void setMasjids(List<Masjid> masjids) {
		this.masjids = masjids;
	}

	public String getMasjidName() {
		return masjidName;
	}

	public void setMasjidName(String masjidName) {
		this.masjidName = masjidName;
	}

	public void setMasjidDao(MasjidDao masjidDao) {
		this.masjidDao = masjidDao;
	}
	
}
