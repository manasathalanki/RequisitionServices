package com.wissen.recruit.requisition.constants;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ErrorCodesTest {

	@BeforeEach
	void init() {
	}

	@Test
	void test_invalidRequisitionID() {
		assertEquals("101", ErrorCodes.INVALID_REQUISITION_ID);
	}

	@Test
	void test_requisitionDetailsNotFoud() {
		assertEquals("102", ErrorCodes.REQUISTION_DETAILS_NOT_FOUND);
	}
	
	@Test
	void test_constraintValidationError() {
		assertEquals("103", ErrorCodes.CONSTRAINT_VALIDATION_ERROR);
	}
	
	@Test
	void test_workflowNotFound() {
		assertEquals("104", ErrorCodes.WORKFLOW_NOT_FOUND);
	}
}
