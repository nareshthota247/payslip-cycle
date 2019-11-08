package com.ibgmediabrands.technicaltest.payslipcycle.model;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeeInfo {
	
	@JsonProperty("first-name")
	@NotEmpty(message = "first-name cannt be null or empty")
	private String firstName;
	@JsonProperty("last-name")
	@NotEmpty(message = "last-name cannt be null or empty")
	private String lastName;
	@JsonProperty("annual-salary")
	@Positive(message = "annual-salary accepts only positive Natutal numbers")
	@Digits(fraction = 0, integer = 10, message ="Please do not include decimal part for annual-salary")
	private BigDecimal annualSalary;
	@JsonProperty("super-rate (%)")
	@DecimalMin(value = "0.0", inclusive = true, message = "Minimum value for super-rate is 0.0")
	@DecimalMax(value = "12.0", inclusive = true, message = "Maximum value for super-rate is 12.0")
	private BigDecimal superRate;
	@JsonProperty("payment-start-date")
	private String paymentStartDate;
	
	public EmployeeInfo() {
		super();
	}
	
	public EmployeeInfo(String firstName, String lastName, BigDecimal annualSalary, BigDecimal superRate, String paymentStartDate) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.annualSalary = annualSalary;
		this.superRate = superRate;
		this.paymentStartDate = paymentStartDate;
	}

	@NotNull(message = "first-name cannt be null or empty")
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	@NotNull(message = "last-name cannt be null or empty")
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@NotNull(message = "annual-salary cannot be null")
	public BigDecimal getAnnualSalary() {
		return annualSalary;
	}
	public void setAnnualSalary(BigDecimal annualSalary) {
		this.annualSalary = annualSalary;
	}
	@NotNull(message = "super-rate cannot be null")
	public BigDecimal getSuperRate() {
		return superRate;
	}
	public void setSuperRate(BigDecimal superRate) {
		this.superRate = superRate;
	}
	@NotNull(message = "payment-start-date cannot be null")
	public String getPaymentStartDate() {
		return paymentStartDate;
	}
	public void setPaymentStartDate(String paymentStartDate) {
		this.paymentStartDate = paymentStartDate;
	}
}
