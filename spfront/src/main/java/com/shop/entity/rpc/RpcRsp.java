package com.shop.entity.rpc;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

public class RpcRsp<C extends Content> implements Serializable {

	protected int code;
	protected String message;
	protected C content;
	protected String display;

	/**
	 * 构造函数
	 */
	public RpcRsp() {
	}

	/**
	 * 构造函数
	 *
	 * @param code
	 * @param content
	 */
	public RpcRsp(int code, C content) {
		super();
		this.code = code;
		this.content = content;
	}

	/**
	 * 构造函数
	 *
	 * @param code
	 * @param message
	 * @param content
	 */
	public RpcRsp(int code, String message, C content) {
		super();
		this.code = code;
		this.message = message;
		this.content = content;
	}


	public RpcRsp(RspCodeEnum code) {
		this(code, (String) null);
	}

	public RpcRsp(RspCodeEnum code, String message) {
		super();
		this.code = code.getValue();
		this.message = message;
	}

	public RpcRsp(RspCodeEnum code, C content) {
		super();
		this.code = code.getValue();
		this.content = content;
	}

	public void setRsp(RspCodeEnum code) {
		this.code = code.getValue();
	}

	public void setRsp(RspCodeEnum code, String message) {
		this.code = code.getValue();
		this.message = message;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}



	public C getContent() {
		return content;
	}

	public void setContent(C content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
