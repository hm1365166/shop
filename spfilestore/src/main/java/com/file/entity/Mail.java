package com.file.entity;

import java.sql.Timestamp;

/**
 * 邮件
 * @author:HM
 * @date: 2017/9/30 09:15:39
 */
public class Mail {

	private Integer id;
	/**
	 * 发件人
	 */
	private String senderName;
	/**
	 * 发件人邮箱
	 */
	private String senderMail;
	/**
	 * 发件人网址
	 */
	private String senderWebSite;
	/**
	 * 收件人
	 */
	private String receiverName;
	/**
	 * 收件人邮箱
	 */
	private String receiverMail;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 日期
	 */
	private Timestamp date;

	public Integer getId() {

		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderMail() {
		return senderMail;
	}

	public void setSenderMail(String senderMail) {
		this.senderMail = senderMail;
	}

	public String getSenderWebSite() {
		return senderWebSite;
	}

	public void setSenderWebSite(String senderWebSite) {
		this.senderWebSite = senderWebSite;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverMail() {
		return receiverMail;
	}

	public void setReceiverMail(String receiverMail) {
		this.receiverMail = receiverMail;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}
}