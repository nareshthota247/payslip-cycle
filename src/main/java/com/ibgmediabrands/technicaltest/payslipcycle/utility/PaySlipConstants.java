package com.ibgmediabrands.technicaltest.payslipcycle.utility;

import java.math.BigDecimal;

public class PaySlipConstants {

	private PaySlipConstants() {
	}
	public static final BigDecimal TOTAL_MONTHS_IN_A_YEAR = BigDecimal.valueOf(12);
	public static final String DATE_FORMATTER = "d MMMM";
	public static final String MONTH_FORMATTER = "MMMM";
	public static final String DATE_SEPARATER = "-";
	public static final String MONTH_SEPARATER = " ";
	public static final String ERR_START_END_DATE = "Please check start and end Dates";
	public static final String ERR_PYMT_START_DATE_RANGE = "payment-date-range is not in correct format";
	
}
