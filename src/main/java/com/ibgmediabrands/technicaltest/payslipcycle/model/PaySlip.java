package com.ibgmediabrands.technicaltest.payslipcycle.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaySlip {

	@JsonProperty("name")
	private String name;
	@JsonProperty("pay-period")
	private String payPeriod;
	@JsonProperty("gross-income")
	private BigDecimal grossIncome;
	@JsonProperty("income-tax")
	private BigDecimal incomeTax;
	@JsonProperty("net-income")
	private BigDecimal netIncome;
	@JsonProperty("super-amount")
	private BigDecimal superAmount;
	
	public PaySlip() {
	}
	
	
	public PaySlip(String name, String payPeriod, BigDecimal grossIncome, BigDecimal incomeTax, BigDecimal netIncome,
			BigDecimal superAmount) {
		super();
		this.name = name;
		this.payPeriod = payPeriod;
		this.grossIncome = grossIncome;
		this.incomeTax = incomeTax;
		this.netIncome = netIncome;
		this.superAmount = superAmount;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPayPeriod() {
		return payPeriod;
	}
	public void setPayPeriod(String payPeriod) {
		this.payPeriod = payPeriod;
	}
	public BigDecimal getGrossIncome() {
		return grossIncome;
	}
	public void setGrossIncome(BigDecimal grossIncome) {
		this.grossIncome = grossIncome;
	}
	public BigDecimal getIncomeTax() {
		return incomeTax;
	}
	public void setIncomeTax(BigDecimal incomeTax) {
		this.incomeTax = incomeTax;
	}
	public BigDecimal getNetIncome() {
		return netIncome;
	}
	public void setNetIncome(BigDecimal netIncome) {
		this.netIncome = netIncome;
	}
	public BigDecimal getSuperAmount() {
		return superAmount;
	}
	public void setSuperAmount(BigDecimal superAmount) {
		this.superAmount = superAmount;
	}
}
