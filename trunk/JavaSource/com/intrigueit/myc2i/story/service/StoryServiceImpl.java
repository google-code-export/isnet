package com.intrigueit.myc2i.story.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intrigueit.myc2i.common.CommonConstants;
import com.intrigueit.myc2i.story.dao.StoryDao;
import com.intrigueit.myc2i.story.domain.MemberStory;

@Service
public class StoryServiceImpl implements StoryService{

	private StoryDao stroyDao;

	@Autowired
	public StoryServiceImpl(StoryDao stroyDao) {
		this.stroyDao = stroyDao;
	}

	@Override
	public void delete(MemberStory entity) {
		 stroyDao.delete(entity);
		
	}

	@Override
	public List<MemberStory> findAll() {
		return this.stroyDao.loadAll();
	}

	@Override
	public MemberStory findById(Long id) {
		return this.stroyDao.loadById(id);
	}

	@Override
	public void save(MemberStory entity) {
		this.stroyDao.persist(entity);
		
	}

	public void update(MemberStory entity) {
		 stroyDao.update(entity);
	}

	@Override
	public List<MemberStory> findMyAllStories(Long memberId) {
		String clause = " upper(t.member.memberId) = ?1 ";
		return stroyDao.loadByClause(clause, new Object[]{memberId});
	}

}
