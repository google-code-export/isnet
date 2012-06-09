package com.intrigueit.myc2i.masjid.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intrigueit.myc2i.common.dao.GenericDaoImpl;
import com.intrigueit.myc2i.masjid.domain.Masjid;

@Repository
@Transactional
public class MasjidDaoImpl extends GenericDaoImpl<Masjid, Long> implements MasjidDao {

}
