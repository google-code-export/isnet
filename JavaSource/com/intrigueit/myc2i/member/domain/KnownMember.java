package com.intrigueit.myc2i.member.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.intrigueit.myc2i.member.domain.Member;


@Entity(name = "KNOWN_MEMBER")
public class KnownMember implements java.io.Serializable {

	/**
	 * Auto generated serialize version no
	 */
	private static final long serialVersionUID = -5400316555595910634L;

	/** Known Member Id */
	@Id
	@Column(name = "KNOWN_ID")
	@GeneratedValue(generator = "KnownMemberSeq")
	@SequenceGenerator(name = "KnownMemberSeq", sequenceName = "KNOWN_MEMBER_ID_SEQ", allocationSize = 1, initialValue = 1)
	private Long knownId;
	
	
    /** Reference Message */
	@ManyToOne ( targetEntity=Member.class,optional=true)
	@JoinColumn(name = "MEMBER_ID",referencedColumnName="MEMBER_ID")	
	private Member srcMember;	
	
	
	/** Message sender  */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "KNOWN_MEMBER_ID", nullable = false, insertable = false, updatable = false)
	private Member friend;
	
	/** Sender Id */
	@Column(name = "KNOWN_MEMBER_ID", nullable = false)
	private Long friendId;

	public Long getKnownId() {
		return knownId;
	}

	public void setKnownId(Long knownId) {
		this.knownId = knownId;
	}

	public Member getSrcMember() {
		return srcMember;
	}

	public void setSrcMember(Member srcMember) {
		this.srcMember = srcMember;
	}

	public Member getFriend() {
		return friend;
	}

	public void setFriend(Member friend) {
		this.friend = friend;
	}

	public Long getFriendId() {
		return friendId;
	}

	public void setFriendId(Long friendId) {
		this.friendId = friendId;
	}
	
	
	
}
