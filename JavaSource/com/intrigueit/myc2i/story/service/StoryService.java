package com.intrigueit.myc2i.story.service;

import java.util.Date;
import java.util.List;


import com.intrigueit.myc2i.story.domain.MemberStory;

public interface StoryService {
    public void save(MemberStory entity);

    public void delete(MemberStory entity);

	public void update(MemberStory entity);
	
	public MemberStory findById( Long id);

	public List<MemberStory> findAll();
	
	public List<MemberStory> findMyAllStories(Long memberId);
	
	public List<MemberStory> findTopTenStories(String type);
	
	public List<MemberStory> findTopTenMentorStories();
	
	public List<MemberStory> findTopTenProtegeStories();
	
	public MemberStory getMostVotedStory();
	
	public MemberStory getWiningStory(String type);
	
	public List<MemberStory> findMostVotedAndLatestStories(String type);
	
	public List<MemberStory> findUnpublishProtegeStories(Date date);
	
	public MemberStory getWeeklyMentorWiningStory();
	
	public MemberStory getWeeklyProtegeWiningStory();
	
}
