package com.ibgmediabrands.technicaltest.payslipcycle.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibgmediabrands.technicaltest.payslipcycle.exceptionhandeling.DateRangeException;
import com.ibgmediabrands.technicaltest.payslipcycle.exceptionhandeling.TaxTableException;
import com.ibgmediabrands.technicaltest.payslipcycle.model.EmployeeInfo;
import com.ibgmediabrands.technicaltest.payslipcycle.model.MasterTax;
import com.ibgmediabrands.technicaltest.payslipcycle.model.PaySlip;
import com.ibgmediabrands.technicaltest.payslipcycle.repository.MasterTaxRepository;
import com.ibgmediabrands.technicaltest.payslipcycle.utility.PaySlipConstants;
import com.ibgmediabrands.technicaltest.payslipcycle.utility.PaySlipUtility;

@Service
public class PaySlipGeneratorServiceImpl implements PaySlipGeneratorService {

	private static final Logger logger = LoggerFactory.getLogger(PaySlipGeneratorServiceImpl.class);

	@Autowired
	private MasterTaxRepository masterTaxRepository;

	@Override
	public BigDecimal calculateGrossIncome(BigDecimal annualIncome) {
			return PaySlipUtility.calculateGrossIncome(annualIncome);
	}

	@Override
	public PaySlip generatePaySlip(EmployeeInfo employeeInfo) {
		String payPeriod = validateDateRange(employeeInfo.getPaymentStartDate());
		String name = new StringBuffer(employeeInfo.getFirstName()).append(" ").append(employeeInfo.getLastName()).toString();
		BigDecimal grossIncome = calculateGrossIncome(employeeInfo.getAnnualSalary());
		BigDecimal incomeTax = calculateIncomeTax(employeeInfo.getAnnualSalary());
		BigDecimal netIncome = PaySlipUtility.calculateNetIncome(grossIncome,incomeTax);
		BigDecimal superAmount = PaySlipUtility.calculateSuperAmt(grossIncome, employeeInfo.getSuperRate());
				
		return new PaySlip(name, payPeriod, grossIncome, incomeTax, netIncome, superAmount);
	}


	@Override
	public BigDecimal calculateIncomeTax(BigDecimal annualIncome) {

		List<MasterTax> masterTaxList= getMasterTaxTabel();
		for (MasterTax masterTax : masterTaxList) {
			BigDecimal startSlab = masterTax.getSlabStart();
			BigDecimal endSlab = (masterTax.getSlabEnd()!=null)? masterTax.getSlabEnd():BigDecimal.valueOf(Integer.MAX_VALUE);
			if (annualIncome.compareTo(startSlab) >= 0 && annualIncome.compareTo(endSlab) <= 0) {
				if(startSlab != null && startSlab.compareTo(BigDecimal.ZERO)==0)
					return BigDecimal.ZERO;
				logger.debug("masterTax.getSlabStart() :: {}",masterTax.getSlabStart());
				logger.debug("masterTax.getSlabEnd() :: {}",masterTax.getSlabEnd());
				
				return PaySlipUtility.calculateIncomeTax(annualIncome,startSlab,masterTax.getCentPerDoller(),masterTax.getFix());
			}
		}
		throw new TaxTableException("Master Tax table not configured.");
	}
	
	@Override
	public List<MasterTax> getMasterTaxTabel() {
		return masterTaxRepository.findAll();
	}
	
	private String validateDateRange(String paymentDateRange) {

		Calendar cal = Calendar.getInstance();
		DateFormat dateFormatter = new SimpleDateFormat(PaySlipConstants.DATE_FORMATTER);
		DateFormat monthFormatter = new SimpleDateFormat(PaySlipConstants.MONTH_FORMATTER);
		Date calculatedStartDate = null;
		Date calculatedEndDate = null;
		Date actualStartDate = null;
		Date actualEndDate = null;

		if (!paymentDateRange.contains(PaySlipConstants.DATE_SEPARATER))
			throw new DateRangeException(PaySlipConstants.ERR_PYMT_START_DATE_RANGE);

		String[] paymentDates = paymentDateRange.split(PaySlipConstants.DATE_SEPARATER);

		if (paymentDates.length != 2)
			throw new DateRangeException(PaySlipConstants.ERR_PYMT_START_DATE_RANGE);

		String paymentStartDate = paymentDates[0].trim();
		String paymentEndDate = paymentDates[1].trim();

		String[] paymentStartMonths = paymentStartDate.split(PaySlipConstants.MONTH_SEPARATER);
		String[] paymentEndMonths = paymentEndDate.split(PaySlipConstants.MONTH_SEPARATER);
		
		if (paymentStartMonths.length < 2 || paymentEndMonths.length < 2)
			throw new DateRangeException(PaySlipConstants.ERR_PYMT_START_DATE_RANGE);
		
		try {
			Date month = monthFormatter.parse(paymentStartMonths[1]);
			cal.setTime(month);
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
			calculatedStartDate = cal.getTime();
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			calculatedEndDate = cal.getTime();
			actualStartDate = dateFormatter.parse(paymentStartDate);
			actualEndDate = dateFormatter.parse(paymentEndDate);
		} catch (ParseException e) {
			throw new DateRangeException("Date Parsing Exception");
		}
		if (!DateUtils.isSameDay(calculatedStartDate, actualStartDate)
				|| !DateUtils.isSameDay(calculatedEndDate, actualEndDate))
			throw new DateRangeException(PaySlipConstants.ERR_START_END_DATE);
		
		return dateFormatter.format(calculatedStartDate)+ PaySlipConstants.DATE_SEPARATER +dateFormatter.format(calculatedEndDate);
	}

}
