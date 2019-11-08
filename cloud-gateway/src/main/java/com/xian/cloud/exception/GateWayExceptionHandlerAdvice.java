package com.xian.cloud.exception;


import com.xian.cloud.model.RestResult;
import com.xian.cloud.model.RestResultBuilder;
import io.netty.channel.ConnectTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Component
public class GateWayExceptionHandlerAdvice {


    @ExceptionHandler(value = {ResponseStatusException.class})
    public RestResult handle(ResponseStatusException ex) {
        log.error("response status exception:{}", ex.getMessage());
        return RestResultBuilder.builder().failure().code(ex.getStatus().value()).message(ex.getMessage()).build();
    }

    @ExceptionHandler(value = {ConnectTimeoutException.class})
    public RestResult handle(ConnectTimeoutException ex) {
        log.error("connect timeout exception:{}", ex.getMessage());
        return RestResultBuilder.builder().failure().code(500).message("网关超时").build();
    }

    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RestResult handle(NotFoundException ex) {
        log.error("not found exception:{}", ex.getMessage());
        return RestResultBuilder.builder().failure().code(404).message("服务未找到").build();

    }

    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestResult handle(RuntimeException ex) {
        log.error("runtime exception:{}", ex.getMessage());
        return RestResultBuilder.builder().failure().build();
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestResult handle(Exception ex) {
        log.error("exception:{}", ex.getMessage());
        return RestResultBuilder.builder().failure().build();
    }

    @ExceptionHandler(value = {Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestResult handle(Throwable throwable) {
        RestResult RestResult = RestResultBuilder.builder().failure().build();
        if (throwable instanceof ResponseStatusException) {
            RestResult = handle((ResponseStatusException) throwable);
        } else if (throwable instanceof ConnectTimeoutException) {
            RestResult = handle((ConnectTimeoutException) throwable);
        } else if (throwable instanceof NotFoundException) {
            RestResult = handle((NotFoundException) throwable);
        } else if (throwable instanceof RuntimeException) {
            RestResult = handle((RuntimeException) throwable);
        } else if (throwable instanceof Exception) {
            RestResult = handle((Exception) throwable);
        }
        return RestResult;
    }
}
