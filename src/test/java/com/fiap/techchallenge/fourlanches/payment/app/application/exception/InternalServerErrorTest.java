package com.fiap.techchallenge.fourlanches.payment.app.application.exception;

import com.fiap.techchallenge.fourlanches.payment.app.application.execption.InternalServerError;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InternalServerErrorTest {

    @Test
    void testDefaultConstructor() {
        InternalServerError internalServerError = new InternalServerError();
        assertNull(internalServerError.getMessage());
        assertNull(internalServerError.getCause());
    }

    @Test
    void testMessageConstructor() {
        String errorMessage = "An internal server error occurred.";
        InternalServerError internalServerError = new InternalServerError(errorMessage);
        assertEquals(errorMessage, internalServerError.getMessage());
        assertNull(internalServerError.getCause());
    }

    @Test
    void testMessageAndCauseConstructor() {
        String errorMessage = "An internal server error occurred.";
        Throwable cause = new RuntimeException("Root cause");
        InternalServerError internalServerError = new InternalServerError(errorMessage, cause);
        assertEquals(errorMessage, internalServerError.getMessage());
        assertEquals(cause, internalServerError.getCause());
    }

    @Test
    void testCauseConstructor() {
        Throwable cause = new RuntimeException("Root cause");
        InternalServerError internalServerError = new InternalServerError(cause);
        assertEquals(cause, internalServerError.getCause());
    }

    @Test
    void testFullConstructor() {
        String errorMessage = "An internal server error occurred.";
        Throwable cause = new RuntimeException("Root cause");
        InternalServerError internalServerError = new InternalServerError(
                errorMessage, cause, true, false
        );

        assertEquals(errorMessage, internalServerError.getMessage());
        assertEquals(cause, internalServerError.getCause());
        assertFalse(internalServerError.getStackTrace().length > 0);
    }
}