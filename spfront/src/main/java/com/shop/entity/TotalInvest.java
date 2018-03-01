package com.shop.entity;

import java.math.BigDecimal;

public class TotalInvest {

	/**
	 * 投资金额
	 */
	private BigDecimal investAmount;
	/**
	 * 季度
	 */
	private String dateRange;

	public BigDecimal getInvestAmount() {
		return investAmount;
	}

	public void setInvestAmount(BigDecimal investAmount) {
		this.investAmount = investAmount;
	}

	public String getDateRange() {
		return dateRange;
	}

	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
	}
}

