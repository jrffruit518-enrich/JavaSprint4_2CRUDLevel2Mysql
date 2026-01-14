package cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneric(Exception exception) {
        return new ErrorResponse(500, exception.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(ResourceExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleResourceExists(ResourceExistsException e) {
        return new ErrorResponse(
                HttpStatus.CONFLICT.value(), // 409
                e.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceNotFound(ResourceNotFoundException e) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(), // 404
                e.getMessage(),
                LocalDateTime.now()
        );
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(MethodArgumentNotValidException e) {
        String detailedMessage = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return new ErrorResponse(
                400,
                "Validation failed: "+ detailedMessage,
                LocalDateTime.now()
        );
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleReadableException(HttpMessageNotReadableException e) {
        return new ErrorResponse(
                400,
                "Malformed JSON request or data type mismatch",
                LocalDateTime.now()
        );
    }



}
