package com.ishvatov.controller;

import com.ishvatov.exception.BusinessLogicException;
import com.ishvatov.exception.DataBaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller, which is called every time exception occurs in the program.
 * Depending on the exception, different response to the user is sent.
 *
 * @author Sergey Khvatov
 */
@RestControllerAdvice
public class ExceptionController {

    /**
     * Logback logger instance.
     */
    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    /**
     * Handles the {@link MethodArgumentNotValidException} exception,
     * which occurs after validation of the user's input.
     *
     * @param exception exception object.
     * @return new {@link ResponseEntity} object, which stores a map with
     * all incorrect input fields and corresponding messages.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        StringBuilder errorLogMessage = new StringBuilder("Incorrect user input:\n");
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
            errorLogMessage.append(fieldName);
            errorLogMessage.append(" - ");
            errorLogMessage.append(errorMessage);
            errorLogMessage.append("\n");
        });
        String resultErrorMessage = errorLogMessage.toString();
        logger.warn(resultErrorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    /**
     * Handles the {@link DataBaseException}, which may occur,
     * if something happens in the data base.
     *
     * @param exception exception object.
     * @return new {@link ResponseEntity} object, which stores response message to this
     * type of exceptions.
     */
    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<String> handleDataBaseException(DataBaseException exception) {
        String errorMessage = "Unfortunately, your transaction has failed. " +
            "Please, try to repeat your transaction later.";
        logger.error("Error occurred while working with DB", exception.getCause());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    /**
     * Handles the {@link BusinessLogicException}, which may occur,
     * if something happens in the business logic.
     *
     * @param exception exception object.
     * @return new {@link ResponseEntity} object, which stores response message to this
     * type of exceptions.
     */
    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<String> handleBusinessLogicException(BusinessLogicException exception) {
        logger.warn("Exception occurred in business logic", exception.getCause());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
    }

    /**
     * Handles the {@link RuntimeException}, which may occur,
     * if something happens in the data base.
     *
     * @param exception exception object.
     * @return new {@link ResponseEntity} object, which stores response message to this
     * type of exceptions.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleOtherExceptions(RuntimeException exception) {
        String errorMessage = "Unfortunately, internal server error has occurred. " +
            "Please contact your admin.";
        logger.error("Internal server error has occurred", exception.getCause());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }
}
