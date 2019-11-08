package com.ibgmediabrands.technicaltest.payslipcycle.api;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ibgmediabrands.technicaltest.payslipcycle.exceptionhandeling.DateRangeException;
import com.ibgmediabrands.technicaltest.payslipcycle.exceptionhandeling.ErrorResponse;
import com.ibgmediabrands.technicaltest.payslipcycle.exceptionhandeling.TaxTableException;
import com.ibgmediabrands.technicaltest.payslipcycle.model.EmployeeInfo;
import com.ibgmediabrands.technicaltest.payslipcycle.model.PaySlip;
import com.ibgmediabrands.technicaltest.payslipcycle.service.PaySlipGeneratorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api")
@Api(value = "Employee payslip for a flexible pay cycle")
public class PaySlipController {

	private static final Logger logger = LoggerFactory.getLogger(PaySlipController.class);

	@Autowired
	private PaySlipGeneratorService paySlipGeneratorServiceImpl;

	@ApiOperation(value = "Payslip generator end point", response = PaySlip.class)
	@PostMapping(value = "/payslip", consumes = { "application/json" }, produces = { "application/json" })
	public ResponseEntity<PaySlip> planGenerator(@Valid @RequestBody EmployeeInfo employeeInfo) {
		return ResponseEntity.ok(paySlipGeneratorServiceImpl.generatePaySlip(employeeInfo));
	}

	@ExceptionHandler(TaxTableException.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(TaxTableException ex) {
		logger.error("Exception PaySlipException :: {} ", ex.getMessage());
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(Integer.valueOf(1001));
		error.setErrorDesc(ex.getMessage());
		error.setTimestamp(new Date());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DateRangeException.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(DateRangeException ex) {
		logger.error("Exception DateRangeException :: {} ", ex.getMessage());
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(Integer.valueOf(1002));
		error.setErrorDesc(ex.getMessage());
		error.setTimestamp(new Date());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		logger.error("Exception MethodArgumentNotValidException :: {} ", ex.getMessage());
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleValidationExceptions(HttpMessageNotReadableException ex) {
		logger.error("Exception HttpMessageNotReadableException :: {} ", ex.getMessage());
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(Integer.valueOf(1003));
		error.setErrorDesc(ex.getMessage());
		error.setTimestamp(new Date());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
}
