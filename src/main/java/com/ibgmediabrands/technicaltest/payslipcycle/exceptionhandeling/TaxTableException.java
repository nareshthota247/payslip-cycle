package com.ibgmediabrands.technicaltest.payslipcycle.exceptionhandeling;

public class TaxTableException extends RuntimeException {

	private static final long serialVersionUID = -108583392048799401L;
	
	public TaxTableException() {
		super();
	}
	
	public TaxTableException(String exceptionDescription) {
		super(exceptionDescription);
	}
	    

}
