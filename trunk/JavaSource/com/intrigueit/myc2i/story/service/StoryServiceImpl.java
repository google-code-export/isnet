package com.intrigueit.myc2i.story.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		String clause = " upper(t.member.memberId) = ?1 and lower(t.approvedForPublishInd) <> ?2 and t.weekWinnerIndicator IS NULL ";
		return stroyDao.loadByClause(clause, new Object[]{memberId,"yes"});
	}

	@Override
	public List<MemberStory> findTopTenStories(String type,int range) {
		//String clause = " where lower(t.approvedForPublishInd)=?1 and t.category=?2 and t.weekWinnerIndicator IS NULL ORDER BY t.numberOfVotesReceived DESC ";
		//return this.stroyDao.loadTopResultsByConditions(10, clause, new Object[]{"yes",type});
		Date date = this.getWeekFirstDay(range);
		String clause = " where lower(t.approvedForPublishInd)=?1 and t.category=?2 and t.approvalDate > ?3 ORDER BY t.numberOfVotesReceived DESC";
		List<MemberStory> stories  = this.stroyDao.loadTopResultsByConditions(10, clause, new Object[]{"yes",type,date});
		return stories;
	}

/*	@Override
	public MemberStory getMostVotedStory() {
		return this.stroyDao.loadById(7L);
	}*/

	@Override
	public MemberStory getWiningStory(String type) {
		String clause = " where t.weekWinnerIndicator IS NOT NULL and t.category=?1 ORDER BY t.weekWinnerIndicator DESC ";
		List<MemberStory> stories  = this.stroyDao.loadTopResultsByConditions(1, clause, new Object[]{type});
		return stories.get(0);
	}

	private Date getWeekFirstDay(int weekday){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
/*		if(weekday == 0){
			weekday = cal.get(Calendar.DAY_OF_WEEK) + 1 ;
		}*/

		cal.add(Calendar.DAY_OF_YEAR, -weekday);

		return cal.getTime();
	}
	@Override
	public List<MemberStory> findMostVotedAndLatestStories(String type,int range) {
		Date date = this.getWeekFirstDay(range);
		String clause = " where lower(t.approvedForPublishInd)=?1 and t.category=?2 and t.approvalDate > ?3 ORDER BY t.approvalDate DESC ";
		List<MemberStory> stories  = this.stroyDao.loadTopResultsByConditions(500, clause, new Object[]{"yes",type,date});
		return stories;
	}

	@Override
	public List<MemberStory> findUnpublishProtegeStories(Date date) {
		String clause = "  lower(t.memberPermissionToPublish)=?1 and t.memberStoryDate >= ?2 and lower(t.approvedForPublishInd) <> ?3 ORDER BY t.memberStoryDate DESC ";
		List<MemberStory> stories  = this.stroyDao.loadByClause(clause, new Object[]{"yes",date,"yes"});
		return stories;
	}

	@Override
	public MemberStory getWeeklyMentorWiningStory(int range) {
		Date date = this.getWeekFirstDay(range);
		String clause = " where t.numberOfVotesReceived IS NOT NULL and t.weekWinnerIndicator IS NULL and lower(t.approvedForPublishInd)=?1 and t.category=?2 and t.approvalDate > ?3  ORDER BY t.numberOfVotesReceived DESC t.approvalDate DESC ";
		List<MemberStory> stories  = this.stroyDao.loadTopResultsByConditions(50, clause, new Object[]{"yes","MENTOR",date});
		for(MemberStory story: stories){
			if(!isMemberWinThisYear(story.getMember().getMemberId())){
				return story;
			}
		}
		return null;
	}

	@Override
	public MemberStory getWeeklyProtegeWiningStory(int range) {
		Date date = this.getWeekFirstDay(range);
		String clause = " where t.numberOfVotesReceived IS NOT NULL and t.weekWinnerIndicator IS NULL and lower(t.approvedForPublishInd)=?1 and t.category=?2 and t.approvalDate > ?3 ORDER BY t.numberOfVotesReceived DESC t.approvalDate DESC ";
		List<MemberStory> stories  = this.stroyDao.loadTopResultsByConditions(50, clause, new Object[]{"yes","PROTEGE",date});
		for(MemberStory story: stories){
			if(!isMemberWinThisYear(story.getMember().getMemberId())){
				return story;
			}
		}
		return null;
	}
	
	private Boolean isMemberWinThisYear(Long memberId){
		String DATE_FORMAT = "yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		String dateStr = sdf.format(new Date());
		
		String clause = " where  to_char(t.weekWinnerIndicator,'yyyy')  =?1 and t.member.memberId=?2 ";
		List<MemberStory> stories  = this.stroyDao.loadTopResultsByConditions(1, clause, new Object[]{dateStr,memberId});
		if(stories == null){
			return false;
		}
		if(stories.size() == 0){
			return false;
		}
		return true;
	}

}
