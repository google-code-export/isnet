package com.intrigueit.myc2i.event.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "event_registration")
public class EventRegistration implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO) 
	private Long id;
	
	@Column(name = "event_id")
	private Long eventId;
	
	@Column(name = "member_id")
	private Long memberId;
	
	@Column(name = "reg_date")
	private Date registrationDate;
	
}
