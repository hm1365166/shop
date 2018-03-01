package com.file.entity;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

public class AbstractRpcEntity implements Serializable {

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
