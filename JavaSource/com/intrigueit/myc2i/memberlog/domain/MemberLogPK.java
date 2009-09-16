package com.intrigueit.myc2i.memberlog.domain;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Embeddable
public class MemberLogPK implements java.io.Serializable {

	private static final long serialVersionUID = -6723439947191274890L;
	private Timestamp memberLogDateTime;
	private Long memberId;
	private Long memberActivityType;

	// Constructors

	/** default constructor */
	public MemberLogPK() {
	}

	/** full constructor */
	public MemberLogPK(Timestamp memberLogDateTime, Long memberId, Long memberActivityType) {
		this.memberLogDateTime = memberLogDateTime;
		this.memberId = memberId;
		this.memberActivityType = memberActivityType;
	}


	@Column(name = "MEMBER_LOG_DATE_TIME", nullable = false)
	public Date getMemberLogDateTime() {
		return this.memberLogDateTime;
	}

	public void setMemberLogDateTime(Timestamp memberLogDateTime) {
		this.memberLogDateTime = memberLogDateTime;
	}

	@Column(name = "MEMBER_ID", nullable = false, precision = 22, scale = 0)
	public Long getMemberId() {
		return this.memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	@Column(name = "MEMBER_LOG_TYPE_ID", nullable = false, precision = 22, scale = 0)
	public Long getMemberActivityType() {
		return memberActivityType;
	}

	public void setMemberActivityType(Long memberActivityType) {
		this.memberActivityType = memberActivityType;
	}


	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof MemberLogPK))
			return false;
		MemberLogPK castOther = (MemberLogPK) other;

		return ((this.getMemberLogDateTime() == castOther
				.getMemberLogDateTime()) || (this.getMemberLogDateTime() != null
				&& castOther.getMemberLogDateTime() != null && this
				.getMemberLogDateTime()
				.equals(castOther.getMemberLogDateTime())))
				&& ((this.getMemberId() == castOther.getMemberId()) || (this
						.getMemberId() != null
						&& castOther.getMemberId() != null && this
						.getMemberId().equals(castOther.getMemberId())))
				&& ((this.getMemberActivityType() == castOther.getMemberActivityType()) || (this
						.getMemberActivityType() != null
						&& castOther.getMemberActivityType() != null && this
						.getMemberActivityType().equals(castOther.getMemberActivityType())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getMemberLogDateTime() == null ? 0 : this
						.getMemberLogDateTime().hashCode());
		result = 37 * result
				+ (getMemberId() == null ? 0 : this.getMemberId().hashCode());
		result = 37
				* result
				+ (getMemberActivityType() == null ? 0 : this.getMemberActivityType()
						.hashCode());
		return result;
	}

}