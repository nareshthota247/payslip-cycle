package com.ibgmediabrands.technicaltest.payslipcycle.exceptionhandeling;

public class DateRangeException extends RuntimeException {

	private static final long serialVersionUID = -6147570337778771413L;
	
	public DateRangeException() {
		super();
	}

	public DateRangeException(String exceptionDescription) {
		super(exceptionDescription);
	}
}
