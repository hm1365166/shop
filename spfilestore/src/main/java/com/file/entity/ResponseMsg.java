package com.file.entity;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * ResponseMsg<T>
 *
 * @author:HM
 * @date: 17-12-23 15:06:52
 * @since v1.0.0
 * @param <T>
 */
public class ResponseMsg<T> {
	/**
	 * 响应状态
	 */
	private String status;
	/**
	 * 响应信息
	 */
	private String msg;
	/**
	 * 响应code
	 */
	private int code;
	/**
	 * 响应内容
	 */
	private T content;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this, filter);
	}

	/**
	 * json序列化时，将null装换成空字符串
	 */
	private ValueFilter filter = new ValueFilter() {
		@Override
		public Object process(Object obj, String s, Object v) {
			if (v == null) {
				return "";
			}
			return v;
		}
	};
}
