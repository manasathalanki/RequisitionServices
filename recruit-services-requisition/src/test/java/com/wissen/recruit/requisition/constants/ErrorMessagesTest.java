package com.wissen.recruit.requisition.constants;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ErrorMessagesTest {

	@BeforeEach
	void init() {
	}

	@Test
	void test_invalidRequisitionID() {
		assertEquals("Invalid requisition Id ", ErrorMessages.INVALID_REQUISITION_ID);
	}

	@Test
	void test_requisitionDetailsNotFoud() {
		assertEquals("Requisition details not found for the id ", ErrorMessages.REQUISTION_DETAILS_NOT_FOUND_FOR_ID);
	}
	
	@Test
	void test_constraintValidation() {
		assertEquals("Input Validations Failed", ErrorMessages.CONSTRAINT_VALIDATION_FAILED);
	}
	
	@Test
	void test_invalidWorkflowID() {
		assertEquals("Invalid workflow Id ", ErrorMessages.INVALID_WORKFLOW_ID);
	}
	
	@Test
	void test_workflowNotFound() {
		assertEquals("Workflow details not found for the id ", ErrorMessages.WORKFLOW_NOT_FOUND_FOR_ID);
	}
}
