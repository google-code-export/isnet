/**
 * @(#)ProtegeEvaluationOfMentor.java
 *
 * Copyright (c) Intrigue (USA) Inc. 2009
 * 
 */
package com.intrigueit.myc2i.protegeevalmentor.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.intrigueit.myc2i.member.domain.Member;

/**
 * This class represent the MyC2i ProtegeEvaluationOfMentor entity
 * 
 * @version 1.00 August 11,2009
 * @author Shahinur Islam (Mithun)
 * 
 */
@Entity(name = "PROTEGE_EVALUATION_OF_MENTOR")
public class ProtegeEvaluationOfMentor implements java.io.Serializable {

	/**
   * 
   */
	private static final long serialVersionUID = 3966412828942198414L;

	/** Member Id */
	@Id
	@Column(name = "PROTEGE_EVAL_OF_MENTOR_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long protegeEvalOfMentorId;

	@Column(name = "MENTOR_MEMBER_ID")
	private Long mentorMemberId;

	@Column(name = "PROTEGE_MEMBER_ID")
	private Long protegeMemberId;

	@Column(name = "EVAL_DATE")
	private Date evalDate;

	@Column(name = "EVAL_FACTOR_ONE_TEXT")
	private String evalFactorOneText;

	@Column(name = "EVAL_FACTOR_TWO_TEXT")
	private String evalFactorTwoText;

	@Column(name = "EVAL_FACTOR_THREE_TEXT")
	private String evalFactorThreeText;

	@Column(name = "EVAL_FACTOR_FOUR_TEXT")
	private String evalFactorFourText;

	@Column(name = "EVAL_FACTOR_FIVE_TEXT")
	private String evalFactorFiveText;

	@Column(name = "EVAL_FACTOR_SIX_TEXT")
	private String evalFactorSixText;

	@Column(name = "EVAL_FACTOR_ONE_RATING_NUM")
	private Long evalFactorOneRatingNum = new Long(-1);

	@Column(name = "EVAL_FACTOR_TWO_RATING_NUM")
	private Long evalFactorTwoRatingNum = new Long(-1);

	@Column(name = "EVAL_FACTOR_THREE_RATING_NUM")
	private Long evalFactorThreeRatingNum = new Long(-1);

	@Column(name = "EVAL_FACTOR_FOUR_RATING_NUM")
	private Long evalFactorFourRatingNum = new Long(-1);

	@Column(name = "EVAL_FACTOR_FIVE_RATING_NUM")
	private Long evalFactorFiveRatingNum = new Long(-1);

	@Column(name = "EVAL_FACTOR_SIX_RATING_NUM")
	private Long evalFactorSixRatingNum;

	@Column(name = "EVAL_FACTOR_TOTAL_RATING_NUM")
	private Long evalFactorTotalRatingNum;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MENTOR_MEMBER_ID", nullable = false, insertable = false, updatable = false)
	private Member mentor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROTEGE_MEMBER_ID", nullable = false, insertable = false, updatable = false)
	private Member protege;

	/**
	 * @return the protegeEvalOfMentorId
	 */
	public Long getProtegeEvalOfMentorId() {
		return protegeEvalOfMentorId;
	}

	/**
	 * @param protegeEvalOfMentorId
	 *            the protegeEvalOfMentorId to set
	 */
	public void setProtegeEvalOfMentorId(Long protegeEvalOfMentorId) {
		this.protegeEvalOfMentorId = protegeEvalOfMentorId;
	}

	/**
	 * @return the mentorMemberId
	 */
	public Long getMentorMemberId() {
		return mentorMemberId;
	}

	/**
	 * @param mentorMemberId
	 *            the mentorMemberId to set
	 */
	public void setMentorMemberId(Long mentorMemberId) {
		this.mentorMemberId = mentorMemberId;
	}

	/**
	 * @return the protegeMemberId
	 */
	public Long getProtegeMemberId() {
		return protegeMemberId;
	}

	/**
	 * @param protegeMemberId
	 *            the protegeMemberId to set
	 */
	public void setProtegeMemberId(Long protegeMemberId) {
		this.protegeMemberId = protegeMemberId;
	}

	/**
	 * @return the evalDate
	 */
	public Date getEvalDate() {
		return evalDate;
	}

	/**
	 * @param evalDate
	 *            the evalDate to set
	 */
	public void setEvalDate(Date evalDate) {
		this.evalDate = evalDate;
	}

	/**
	 * @return the evalFactorOneText
	 */
	public String getEvalFactorOneText() {
		return evalFactorOneText;
	}

	/**
	 * @param evalFactorOneText
	 *            the evalFactorOneText to set
	 */
	public void setEvalFactorOneText(String evalFactorOneText) {
		this.evalFactorOneText = evalFactorOneText;
	}

	/**
	 * @return the evalFactorTwoText
	 */
	public String getEvalFactorTwoText() {
		return evalFactorTwoText;
	}

	/**
	 * @param evalFactorTwoText
	 *            the evalFactorTwoText to set
	 */
	public void setEvalFactorTwoText(String evalFactorTwoText) {
		this.evalFactorTwoText = evalFactorTwoText;
	}

	/**
	 * @return the evalFactorThreeText
	 */
	public String getEvalFactorThreeText() {
		return evalFactorThreeText;
	}

	/**
	 * @param evalFactorThreeText
	 *            the evalFactorThreeText to set
	 */
	public void setEvalFactorThreeText(String evalFactorThreeText) {
		this.evalFactorThreeText = evalFactorThreeText;
	}

	/**
	 * @return the evalFactorFourText
	 */
	public String getEvalFactorFourText() {
		return evalFactorFourText;
	}

	/**
	 * @param evalFactorFourText
	 *            the evalFactorFourText to set
	 */
	public void setEvalFactorFourText(String evalFactorFourText) {
		this.evalFactorFourText = evalFactorFourText;
	}

	/**
	 * @return the evalFactorFiveText
	 */
	public String getEvalFactorFiveText() {
		return evalFactorFiveText;
	}

	/**
	 * @param evalFactorFiveText
	 *            the evalFactorFiveText to set
	 */
	public void setEvalFactorFiveText(String evalFactorFiveText) {
		this.evalFactorFiveText = evalFactorFiveText;
	}

	/**
	 * @return the evalFactorSixText
	 */
	public String getEvalFactorSixText() {
		return evalFactorSixText;
	}

	/**
	 * @param evalFactorSixText
	 *            the evalFactorSixText to set
	 */
	public void setEvalFactorSixText(String evalFactorSixText) {
		this.evalFactorSixText = evalFactorSixText;
	}

	/**
	 * @return the evalFactorOneRatingNum
	 */
	public Long getEvalFactorOneRatingNum() {
		return evalFactorOneRatingNum;
	}

	/**
	 * @param evalFactorOneRatingNum
	 *            the evalFactorOneRatingNum to set
	 */
	public void setEvalFactorOneRatingNum(Long evalFactorOneRatingNum) {
		this.evalFactorOneRatingNum = evalFactorOneRatingNum;
	}

	/**
	 * @return the evalFactorTwoRatingNum
	 */
	public Long getEvalFactorTwoRatingNum() {
		return evalFactorTwoRatingNum;
	}

	/**
	 * @param evalFactorTwoRatingNum
	 *            the evalFactorTwoRatingNum to set
	 */
	public void setEvalFactorTwoRatingNum(Long evalFactorTwoRatingNum) {
		this.evalFactorTwoRatingNum = evalFactorTwoRatingNum;
	}

	/**
	 * @return the evalFactorThreeRatingNum
	 */
	public Long getEvalFactorThreeRatingNum() {
		return evalFactorThreeRatingNum;
	}

	/**
	 * @param evalFactorThreeRatingNum
	 *            the evalFactorThreeRatingNum to set
	 */
	public void setEvalFactorThreeRatingNum(Long evalFactorThreeRatingNum) {
		this.evalFactorThreeRatingNum = evalFactorThreeRatingNum;
	}

	/**
	 * @return the evalFactorFourRatingNum
	 */
	public Long getEvalFactorFourRatingNum() {
		return evalFactorFourRatingNum;
	}

	/**
	 * @param evalFactorFourRatingNum
	 *            the evalFactorFourRatingNum to set
	 */
	public void setEvalFactorFourRatingNum(Long evalFactorFourRatingNum) {
		this.evalFactorFourRatingNum = evalFactorFourRatingNum;
	}

	/**
	 * @return the evalFactorFiveRatingNum
	 */
	public Long getEvalFactorFiveRatingNum() {
		return evalFactorFiveRatingNum;
	}

	/**
	 * @param evalFactorFiveRatingNum
	 *            the evalFactorFiveRatingNum to set
	 */
	public void setEvalFactorFiveRatingNum(Long evalFactorFiveRatingNum) {
		this.evalFactorFiveRatingNum = evalFactorFiveRatingNum;
	}

	/**
	 * @return the evalFactorSixRatingNum
	 */
	public Long getEvalFactorSixRatingNum() {
		return evalFactorSixRatingNum;
	}

	/**
	 * @param evalFactorSixRatingNum
	 *            the evalFactorSixRatingNum to set
	 */
	public void setEvalFactorSixRatingNum(Long evalFactorSixRatingNum) {
		this.evalFactorSixRatingNum = evalFactorSixRatingNum;
	}

	/**
	 * @return the evalFactorTotalRatingNum
	 */
	public Long getEvalFactorTotalRatingNum() {
		return evalFactorTotalRatingNum;
	}

	/**
	 * @param evalFactorTotalRatingNum
	 *            the evalFactorTotalRatingNum to set
	 */
	public void setEvalFactorTotalRatingNum(Long evalFactorTotalRatingNum) {
		this.evalFactorTotalRatingNum = evalFactorTotalRatingNum;
	}

	public Member getMentor() {
		return mentor;
	}

	public void setMentor(Member mentor) {
		this.mentor = mentor;
	}

	public Member getProtege() {
		return protege;
	}

	public void setProtege(Member protege) {
		this.protege = protege;
	}

}
