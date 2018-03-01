package com.shop.entity.rpc;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;


public class OpResponse<T> implements Serializable {

	private static final long serialVersionUID = 2331099959379685238L;

	/**
	 * 成功的code，最好是各个业务系统都保持统一
	 */
	public static final int SUCCESS_CODE = 0;

	/**
	 * 应用返回的错误码
	 */
	private int code;

	/**
	 * 应用返回的(错误)信息
	 */
	private String display;

	/**
	 * 应用返回的数据
	 */
	private T content;

	/**
	 * 某次请求返回的token，用于下一次表单提交
	 */
	private String token;

	/**
	 * 主要用于前端是H5的场景，用于告诉H5页面，收到请求后是跳下一个页面，还是停留在当前页面
	 */
	private String next;

	/**
	 * 返回1个表示成功的实例
	 * 
	 * @return
	 */
	public static OpResponse<?> suc() {
		OpResponse<?> resp = new OpResponse<>();
		resp.code = SUCCESS_CODE;
		return resp;
	}

	/**
	 * 返回1个表示成功的实例
	 * 
	 * @param display
	 *        要返回的消息
	 * @return
	 */
	public static OpResponse<?> suc(String display) {
		OpResponse<?> resp = OpResponse.suc();
		resp.display = display;
		return resp;
	}


	/**
	 * 返回1个表示失败的实例
	 * 
	 * @param code 错误码
	 * @param display 错误信息
	 * @param next 下一步，可以使用NextAction枚举类
	 * @return
	 */
	public static OpResponse<?> fail(int code, String display, String next) {
		OpResponse<?> resp = new OpResponse<>();
		resp.code = code;
		resp.display = display;
		resp.next = next;
		return resp;
	}

	/**
	 * 操作结果是否成功
	 * 
	 * @return
	 */
	public boolean isSuc() {
		return code == SUCCESS_CODE;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
