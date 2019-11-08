package com.ibgmediabrands.technicaltest.payslipcycle.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibgmediabrands.technicaltest.payslipcycle.model.EmployeeInfo;

@SpringBootTest
@AutoConfigureMockMvc
public class PaySlipControllerTests {
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;
	
	@Test
	public void test_lastname_constrant_null() throws Exception{
		mockMvc.perform(post("/api/payslip").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(new EmployeeInfo("Claire", null, BigDecimal.valueOf(60050), BigDecimal.valueOf(9), "01 March - 31 March"))))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.lastName").value("last-name cannt be null or empty")).andReturn();
	}
	
	@Test
	public void test_firstname_constrant_null() throws Exception{
		mockMvc.perform(post("/api/payslip").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(new EmployeeInfo(null, "Wong", BigDecimal.valueOf(60050), BigDecimal.valueOf(9), "01 March - 31 March"))))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.firstName").value("first-name cannt be null or empty")).andReturn();
	}
	
	@Test
	public void test_salary_constrant_naturalnumnber() throws Exception{
		mockMvc.perform(post("/api/payslip").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(new EmployeeInfo("Claire", "Wong", BigDecimal.valueOf(60050.00), BigDecimal.valueOf(9), "01 March - 31 March"))))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.annualSalary").value("Please do not include decimal part for annual-salary")).andReturn();
	}
	
	@Test
	public void test_salary_constrant_negitivenumnber() throws Exception{
		mockMvc.perform(post("/api/payslip").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(new EmployeeInfo("Claire", "Wong", BigDecimal.valueOf(-60050), BigDecimal.valueOf(9), "01 March - 31 March"))))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.annualSalary").value("annual-salary accepts only positive Natutal numbers")).andReturn();
	}
	
	@Test
	public void test_SuperRate_constrant_lessthan_0() throws Exception{
		mockMvc.perform(post("/api/payslip").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(new EmployeeInfo("Claire", "Wong", BigDecimal.valueOf(60050), BigDecimal.valueOf(-9), "01 March - 31 March"))))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.superRate").value("Minimum value for super-rate is 0.0")).andReturn();
	}

	@Test
	public void test_SuperRate_constrant_greterthan_12() throws Exception{
		mockMvc.perform(post("/api/payslip").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(new EmployeeInfo("Claire", "Wong", BigDecimal.valueOf(60050), BigDecimal.valueOf(12.3), "01 March - 31 March"))))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.superRate").value("Maximum value for super-rate is 12.0")).andReturn();
	}
	
	@Test
	public void test_paymentStartDate_constrant_empty() throws Exception{
		mockMvc.perform(post("/api/payslip").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(new EmployeeInfo("Claire", "Wong", BigDecimal.valueOf(60050), BigDecimal.valueOf(9), ""))))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errorCode").value("1002"))
				.andExpect(jsonPath("$.errorDesc").value("payment-date-range is not in correct format")).andReturn();
	}
	
	@Test
	public void test_paymentStartDate_constrant_startandenddates() throws Exception{
		mockMvc.perform(post("/api/payslip").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(new EmployeeInfo("Claire", "Wong", BigDecimal.valueOf(60050), BigDecimal.valueOf(9), "02 March - 31 March"))))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errorCode").value("1002"))
				.andExpect(jsonPath("$.errorDesc").value("Please check start and end Dates")).andReturn();
	}
	
	@Test
	public void test_paymentStartDate_constrant_parsingexceptions() throws Exception{
		mockMvc.perform(post("/api/payslip").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(new EmployeeInfo("Claire", "Wong", BigDecimal.valueOf(60050), BigDecimal.valueOf(9), "01 Mattrch - 31 March"))))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errorCode").value("1002"))
				.andExpect(jsonPath("$.errorDesc").value("Date Parsing Exception")).andReturn();
	}
	
	@Test
	public void test_paymentStartDate_constrant_wrongformate() throws Exception{
		mockMvc.perform(post("/api/payslip").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(new EmployeeInfo("Claire", "Wong", BigDecimal.valueOf(60050), BigDecimal.valueOf(9), "01 March - 11 March - 31 March"))))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errorCode").value("1002"))
				.andExpect(jsonPath("$.errorDesc").value("payment-date-range is not in correct format")).andReturn();
	}
	
	@Test
	public void test_paymentStartDate_constrant_datewithoutmonth() throws Exception{
		mockMvc.perform(post("/api/payslip").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(new EmployeeInfo("Claire", "Wong", BigDecimal.valueOf(60050), BigDecimal.valueOf(9), "01 March - 31 "))))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errorCode").value("1002"))
				.andExpect(jsonPath("$.errorDesc").value("payment-date-range is not in correct format")).andReturn();
	}
	
	@Test
	public void test_generate_payslip_senario1() throws Exception{
		mockMvc.perform(post("/api/payslip").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(new EmployeeInfo("Andrew", "Smith", BigDecimal.valueOf(60050), BigDecimal.valueOf(9), "01 March - 31 March"))))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Andrew Smith"))
				.andExpect(jsonPath("$.pay-period").value("1 March-31 March"))
				.andExpect(jsonPath("$.gross-income").value("5004"))
				.andExpect(jsonPath("$.income-tax").value("922"))
				.andExpect(jsonPath("$.net-income").value("4082"))
				.andExpect(jsonPath("$.super-amount").value("450")).andReturn();
	}
	
	@Test
	public void test_generate_payslip_senario2() throws Exception{
		mockMvc.perform(post("/api/payslip").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(new EmployeeInfo("Claire", "Wong", BigDecimal.valueOf(120000), BigDecimal.valueOf(10), "01 March - 31 March"))))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Claire Wong"))
				.andExpect(jsonPath("$.pay-period").value("1 March-31 March"))
				.andExpect(jsonPath("$.gross-income").value("10000"))
				.andExpect(jsonPath("$.income-tax").value("2669"))
				.andExpect(jsonPath("$.net-income").value("7331"))
				.andExpect(jsonPath("$.super-amount").value("1000")).andReturn();
	}
	
	@Test
	public void test_generate_payslip_senario3() throws Exception{
		mockMvc.perform(post("/api/payslip").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(new EmployeeInfo("Claire", "Wong", BigDecimal.valueOf(18000), BigDecimal.valueOf(10), "01 March - 31 March"))))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Claire Wong"))
				.andExpect(jsonPath("$.pay-period").value("1 March-31 March"))
				.andExpect(jsonPath("$.gross-income").value("1500"))
				.andExpect(jsonPath("$.income-tax").value("0"))
				.andExpect(jsonPath("$.net-income").value("1500"))
				.andExpect(jsonPath("$.super-amount").value("150")).andReturn();
	}
}
