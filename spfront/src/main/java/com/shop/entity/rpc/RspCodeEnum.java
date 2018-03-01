package com.shop.entity.rpc;

import org.apache.commons.lang.StringUtils;

public enum RspCodeEnum {

	SUCCESS("SUCCESS", 0, ""),
	COMMON_ERROR_UNKNOWN_EXCEPTION("COMMON_ERROR_UNKNOWN_EXCEPTION",100001,"未知异常");

	private final String name;
	private final int value;
	private final String remark;

	RspCodeEnum(String name, int value, String remark) {
		this.name = name;
		this.value = value;
		this.remark = remark;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

	public String getRemark() {
		return remark;
	}

	public static final RspCodeEnum fromValue(int value) {
		for (RspCodeEnum e : values()) {
			if (value == e.getValue()) {
				return e;
			}
		}
		return null;
	}

	public static final RspCodeEnum fromName(String name) {
		for (RspCodeEnum e : values()) {
			if (StringUtils.equalsIgnoreCase(name, e.getName())) {
				return e;
			}
		}
		return null;
	}

}
