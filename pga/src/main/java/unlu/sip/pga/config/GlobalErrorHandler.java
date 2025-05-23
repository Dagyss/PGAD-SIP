package unlu.sip.pga.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import unlu.sip.pga.models.ErrorMessage;

import jakarta.servlet.http.HttpServletRequest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalErrorHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ErrorMessage handleNotFound(final HttpServletRequest request, final Exception error) {
        return ErrorMessage.from("Not Found");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ErrorMessage handleAccessDenied(final HttpServletRequest request, final Exception error) {
        return ErrorMessage.from("Permission denied");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Map<String,String>> handleAnyError(Throwable ex) {
        Throwable root = ex;
        while (root.getCause() != null && root != root.getCause()) {
            root = root.getCause();
        }
        StringWriter sw = new StringWriter();
        root.printStackTrace(new PrintWriter(sw));
        String[] lines = sw.toString().split("\\r?\\n");

        String traceExcerpt = Arrays.stream(lines)
                .limit(10)
                .collect(Collectors.joining("\n"));

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "exception", root.getClass().getName(),
                        "message", root.getMessage() != null ? root.getMessage() : "",
                        "trace", traceExcerpt
                ));
    }
}
