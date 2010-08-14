package com.intrigueit.myc2i.message.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "MESSAGE_ATTACHMENT")
public class MessageAttachment implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/** Attachment ID */
    @Id
	@Column(name = "ATTACHMENT_ID")
	@GeneratedValue(generator = "AttachmentIdSeq")
	@SequenceGenerator(name = "AttachmentIdSeq", sequenceName = "ATTACHMENT_ID_SEQ", allocationSize = 1, initialValue = 1)
	private Long attachmentId;


    /** Reference Message */
	@ManyToOne ( targetEntity=Message.class,optional=true)
	@JoinColumn(name = "MESSAGE_ID",referencedColumnName="MESSAGE_ID")	
	private Message message;
	
	/** Attachment file path */
	private String filePath;
	
	/** Attachment file name */
	private String fileName;
	
/*	@Column(name="FILE_CONTENT")
	@Lob
	private byte[] fileContent;*/

	public Long getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(Long attachmentId) {
		this.attachmentId = attachmentId;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public MessageAttachment copy(){
		MessageAttachment attachment = new MessageAttachment();
		attachment.attachmentId = this.attachmentId;
		attachment.fileName = this.fileName;
		attachment.filePath = this.filePath;
		attachment.message = this.message;
		return attachment;
	}


	
}

