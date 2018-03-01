package com.file.util;

import java.io.Serializable;

public class Result implements Serializable {
	private static final long serialVersionUID = 7828683340362103706L;

	/**
	 * -1：参数错误 200:成功 400：失败
	 *
	 */
	private int status;

	/**
	 * 异常信息
	 *
	 */
	private String errorMessage;

	/**
	 *
	 * 返回对象总数(分页用)
	 */
	private int totalCount;

	/**
	 *
	 * 返回每页条数(分页用)
	 */
	private int rows;

	/**
	 * 返回值String格式
	 *
	 */
	private String content;

	/**
	 * 返回值Object格式
	 *
	 */
	private Object data;

	public Result() {
		super();
	}

	public Result(int status) {
		super();
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
