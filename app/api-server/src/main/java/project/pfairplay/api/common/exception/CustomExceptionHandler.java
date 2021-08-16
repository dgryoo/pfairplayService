package project.pfairplay.api.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler{

    // ConstraintViolationException
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseEntity> handleConstraintViolationException(ConstraintViolationException e) {

        int index = e.getMessage().indexOf(":");

        String code = e.getMessage().substring(index + 2);

        ExceptionEnum exceptionEnum = ExceptionEnum.valueOf(code);

        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .errorCode(exceptionEnum.name())
                .errorField(exceptionEnum.getFieldName())
                .message(exceptionEnum.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);


    }

    // MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseEntity> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        ExceptionEnum exceptionEnum = ExceptionEnum.UNKNOWN;

        for(ExceptionEnum ee : ExceptionEnum.values()) {
            if (e.getMessage().contains(ee.name())) {
                exceptionEnum = ee;
            }
        }

        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .errorCode(exceptionEnum.name())
                .errorField(exceptionEnum.getFieldName())
                .message(exceptionEnum.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);


    }
}
