package com.fiap.techchallenge.fourlanches.payment.app.application.exception;

import com.fiap.techchallenge.fourlanches.payment.app.application.execption.NotFound;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotFoundTest {

    @Test
    void testDefaultConstructor() {
        NotFound notFound = new NotFound();
        assertNull(notFound.getMessage());
        assertNull(notFound.getCause());
    }

    @Test
    void testMessageConstructor() {
        String errorMessage = "Resource not found.";
        NotFound notFound = new NotFound(errorMessage);
        assertEquals(errorMessage, notFound.getMessage());
        assertNull(notFound.getCause());
    }

    @Test
    void testMessageAndCauseConstructor() {
        String errorMessage = "Resource not found.";
        Throwable cause = new RuntimeException("Root cause");
        NotFound notFound = new NotFound(errorMessage, cause);
        assertEquals(errorMessage, notFound.getMessage());
        assertEquals(cause, notFound.getCause());
    }

    @Test
    void testCauseConstructor() {
        Throwable cause = new RuntimeException("Root cause");
        NotFound notFound = new NotFound(cause);
        assertEquals(cause, notFound.getCause());
    }

    @Test
    void testFullConstructor() {
        String errorMessage = "Resource not found.";
        Throwable cause = new RuntimeException("Root cause");
        NotFound notFound = new NotFound(
                errorMessage, cause, true, false
        );

        assertEquals(errorMessage, notFound.getMessage());
        assertEquals(cause, notFound.getCause());
        assertFalse(notFound.getStackTrace().length > 0);
    }
}