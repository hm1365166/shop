package com.shop.entity.rpc;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

public abstract class Content implements Serializable {

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
