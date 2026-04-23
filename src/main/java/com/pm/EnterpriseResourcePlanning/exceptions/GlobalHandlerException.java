package com.pm.EnterpriseResourcePlanning.exceptions;

import com.pm.EnterpriseResourcePlanning.enums.ApiProblem;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ProblemDetail> handleNotFoundException(NotFoundException notFoundException, HttpServletRequest request) {

        log.error("Not found = {}", notFoundException.getMessage());

        return ResponseEntity.status(404).body(
                ApiProblem.of(HttpStatus.NOT_FOUND, notFoundException.getMessage(), request, notFoundException, List.of(notFoundException.getEntityId()), null)
        );
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> handleAlreadyExistsException(AlreadyExistsException alreadyExistsException, HttpServletRequest httpServletRequest) {

        log.error("ALREADY EXISTS EXCEPTION = {}", alreadyExistsException.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ApiProblem.of(HttpStatus.CONFLICT, alreadyExistsException.getMessage(), httpServletRequest, alreadyExistsException,
                        alreadyExistsException.getEntityIds(), alreadyExistsException.getValue())
        );
    }


    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ProblemDetail> handleIllegalStateException(IllegalStateException illegalStateException, HttpServletRequest httpServletRequest) {

        log.error("illegalStateException = {}", illegalStateException.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ApiProblem.of(HttpStatus.CONFLICT, illegalStateException.getMessage(), httpServletRequest, illegalStateException, illegalStateException.getEntityIds(), null)
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleUnexpectedException(Exception exception, HttpServletRequest httpServletRequest) {

        log.error("UNEXPECTED ERROR", exception);

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String detail = exception.getMessage();

        if (exception instanceof ErrorResponse errorResponse) {

            status = HttpStatus.valueOf(errorResponse.getStatusCode().value());
            detail = errorResponse.getBody().getDetail();
        }

        return ResponseEntity.status(status).body(
                ApiProblem.of(status, detail, httpServletRequest, exception, null, null));


    }
}
