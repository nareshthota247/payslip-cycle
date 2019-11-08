package com.ibgmediabrands.technicaltest.payslipcycle.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ibgmediabrands.technicaltest.payslipcycle.model.MasterTax;

@SpringBootTest
public class PaySlipGeneratorServiceTests {
	
	@Autowired
	private PaySlipGeneratorService paySlipGeneratorServiceImpl;
	
	@Test
	public void test_calculateGrossIncome_senario1(){
		BigDecimal grossIncome = paySlipGeneratorServiceImpl.calculateGrossIncome(BigDecimal.valueOf(60050));
		assertEquals(grossIncome,BigDecimal.valueOf(5004));
	}
	
	@Test
	public void test_calculateGrossIncome_senario2(){
		BigDecimal grossIncome = paySlipGeneratorServiceImpl.calculateGrossIncome(BigDecimal.valueOf(120000));
		assertEquals(grossIncome,BigDecimal.valueOf(10000));
	}
	
	@Test
	public void test_calculateIncomeTax_senario1(){
		BigDecimal incomeTax = paySlipGeneratorServiceImpl.calculateIncomeTax(BigDecimal.valueOf(60050));
		assertEquals(incomeTax,BigDecimal.valueOf(922));
	}
	
	@Test
	public void test_calculateIncomeTax_senario2(){
		BigDecimal incomeTax = paySlipGeneratorServiceImpl.calculateIncomeTax(BigDecimal.valueOf(120000));
		assertEquals(incomeTax,BigDecimal.valueOf(2669));
	}
	
	@Test
	public void test_database_configuration(){
		 List<MasterTax> taxlist = paySlipGeneratorServiceImpl.getMasterTaxTabel();
		assertEquals(Integer.valueOf(taxlist.size()),Integer.valueOf(5));
	}

}
