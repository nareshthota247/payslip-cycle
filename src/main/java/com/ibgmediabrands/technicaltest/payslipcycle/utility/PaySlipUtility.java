package com.ibgmediabrands.technicaltest.payslipcycle.utility;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PaySlipUtility {

	private static final Logger logger = LoggerFactory.getLogger(PaySlipUtility.class);

	
	private PaySlipUtility() {
	}
	
	public static BigDecimal calculateIncomeTax(BigDecimal annualIncome, BigDecimal startSlab, BigDecimal centPerDoller, BigDecimal fixAmt) {
		
		logger.debug("annualIncome {}",annualIncome);
		logger.debug("startSlab {}",startSlab);
		logger.debug("centPerDoller {}",centPerDoller);
		logger.debug("fixAmt {}",fixAmt);
		BigDecimal incomeTax = (((annualIncome.subtract(startSlab.subtract(BigDecimal.ONE)))
				.multiply(centPerDoller)).add(fixAmt))
				.divide(BigDecimal.valueOf(12),0, RoundingMode.HALF_UP);
		logger.debug("incomeTax {}",incomeTax);
		return incomeTax;
	}
	
	public static BigDecimal calculateGrossIncome(BigDecimal annualIncome) {
		return annualIncome.divide(PaySlipConstants.TOTAL_MONTHS_IN_A_YEAR, RoundingMode.HALF_UP);
	}
	public static BigDecimal calculateNetIncome(BigDecimal grossIncome, BigDecimal incomeTax) {
		return grossIncome.subtract(incomeTax);
	}
	public static BigDecimal calculateSuperAmt(BigDecimal grossIncome, BigDecimal superRate) {
		return grossIncome.multiply(superRate).divide(BigDecimal.valueOf(100),0, RoundingMode.HALF_UP);
	}
}
