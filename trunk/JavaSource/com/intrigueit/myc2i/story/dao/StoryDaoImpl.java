package com.intrigueit.myc2i.story.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intrigueit.myc2i.common.dao.GenericDaoImpl;
import com.intrigueit.myc2i.story.domain.MemberStory;

@Repository
@Transactional
public class StoryDaoImpl extends GenericDaoImpl<MemberStory,Long> implements StoryDao{

}
