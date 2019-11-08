package com.ibgmediabrands.technicaltest.payslipcycle.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ibgmediabrands.technicaltest.payslipcycle.model.EmployeeInfo;
import com.ibgmediabrands.technicaltest.payslipcycle.model.MasterTax;
import com.ibgmediabrands.technicaltest.payslipcycle.model.PaySlip;

@Service
public interface PaySlipGeneratorService {
	
	BigDecimal calculateGrossIncome(BigDecimal annualIncome);
	BigDecimal calculateIncomeTax(BigDecimal annualIncome);
	PaySlip generatePaySlip(EmployeeInfo employeeInfo);
	List<MasterTax> getMasterTaxTabel();

}
