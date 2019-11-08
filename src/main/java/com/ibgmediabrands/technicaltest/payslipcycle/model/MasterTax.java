package com.ibgmediabrands.technicaltest.payslipcycle.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MasterTax {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer taxId;
	private BigDecimal slabStart;
	private BigDecimal slabEnd;
	@Column(precision=10, scale=3)
	private BigDecimal centPerDoller;
	private BigDecimal fix;
	
	public MasterTax() {
		super();
	}

	public BigDecimal getSlabStart() {
		return slabStart;
	}

	public void setSlabStart(BigDecimal slabStart) {
		this.slabStart = slabStart;
	}

	public BigDecimal getSlabEnd() {
		return slabEnd;
	}

	public void setSlabEnd(BigDecimal slabEnd) {
		this.slabEnd = slabEnd;
	}

	public BigDecimal getCentPerDoller() {
		return centPerDoller;
	}

	public void setCentPerDoller(BigDecimal centPerDoller) {
		this.centPerDoller = centPerDoller;
	}

	public BigDecimal getFix() {
		return fix;
	}

	public void setFix(BigDecimal fix) {
		this.fix = fix;
	}
	
	
	
	

}
