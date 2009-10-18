package com.intrigueit.myc2i.story.service;

import java.util.Date;
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

	@Override
	public List<MemberStory> findTopTenStories() {
		String clause = " ORDER BY t.numberOfVotesReceived DESC ";
		return this.stroyDao.loadTopResultsByConditions(10, clause, new Object[]{});
	}

	@Override
	public MemberStory getMostVotedStory() {
		return this.stroyDao.loadById(7L);
	}

	@Override
	public MemberStory getWiningStory() {
		String clause = " where t.weekWinnerIndicator IS NOT NULL ORDER BY t.weekWinnerIndicator DESC ";
		List<MemberStory> stories  = this.stroyDao.loadTopResultsByConditions(1, clause, new Object[]{});
		return stories.get(0);
	}

	@Override
	public List<MemberStory> findTopTenMentorStories() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MemberStory> findTopTenProtegeStories() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MemberStory> findMostVotedAndLatestStories(String type) {
		String clause = " where lower(t.approvedForPublishInd)=?1 and t.category=?2 ORDER BY t.numberOfVotesReceived DESC t.memberStoryDate DESC ";
		List<MemberStory> stories  = this.stroyDao.loadTopResultsByConditions(1000, clause, new Object[]{"yes",type});
		return stories;
	}

	@Override
	public List<MemberStory> findUnpublishProtegeStories(Date date) {
		String clause = "  lower(t.memberPermissionToPublish)=?1 and t.memberStoryDate >= ?2 ORDER BY t.memberStoryDate DESC ";
		List<MemberStory> stories  = this.stroyDao.loadByClause(clause, new Object[]{"yes",date});
		return stories;
	}

}
