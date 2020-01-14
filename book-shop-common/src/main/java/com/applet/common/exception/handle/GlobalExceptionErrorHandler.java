package com.applet.common.exception.handle;

import cn.hutool.http.HttpStatus;
import com.applet.common.util.JSONResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author novLi
 * @date 2020年01月14日 17:37
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionErrorHandler {


    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ErrorBody> error(SecurityException e) {
        log.warn("发生SecurityException异常", e);
        return new ResponseEntity<>(
                ErrorBody.builder()
                        .body(e.getMessage())
                        .status(HttpStatus.HTTP_UNAUTHORIZED)
                        .build(),
                org.springframework.http.HttpStatus.UNAUTHORIZED
        );
    }
}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class ErrorBody {
    private String body;
    private int status;
}

