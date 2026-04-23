package com.pm.EnterpriseResourcePlanning.enums;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public final class ApiProblem {

    public static ProblemDetail of(HttpStatus httpStatus, String detail, HttpServletRequest httpServlet, Throwable ex, List<UUID> id, String value) {

        String code = ex.getClass().getSimpleName()
                .replaceAll("([a-z])([A-Z])", "$1_$2")
                .toLowerCase();

        ProblemDetail pd = ProblemDetail.forStatusAndDetail(httpStatus, detail);
        pd.setTitle(httpStatus.getReasonPhrase());
        pd.setType(URI.create("about:blank:" + code));

        if (httpServlet != null) {
            pd.setInstance(URI.create(httpServlet.getRequestURI()));
        }

        if (httpServlet != null && httpServlet.getMethod() != null) {
            pd.setProperty("method", httpServlet.getMethod());
        }
        if (httpServlet != null && httpServlet.getRequestId() != null) {
            pd.setProperty("requestId", httpServlet.getRequestId());
        }
        if (id != null) {
            pd.setProperty("entityId", id);
        }
        if (value != null) {
            pd.setProperty("value", value);
        }

        pd.setProperty("code", code);
        pd.setProperty("timestamp", Instant.now().toString());
        pd.setProperty("exception", ex.getClass().getSimpleName());

        return pd;
    }
}
