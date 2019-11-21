//package com.xian.common.exception;
//
//
//import com.google.gson.JsonSyntaxException;
//import com.google.gson.stream.MalformedJsonException;
//import com.xian.common.model.RestResult;
//import com.xian.common.model.RestResultBuilder;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.jdbc.BadSqlGrammarException;
//import org.springframework.validation.BindException;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//import org.springframework.validation.ObjectError;
//import org.springframework.web.HttpMediaTypeNotSupportedException;
//import org.springframework.web.HttpRequestMethodNotSupportedException;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.MissingServletRequestParameterException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.servlet.NoHandlerFoundException;
//
//import javax.validation.ConstraintViolation;
//import javax.validation.ConstraintViolationException;
//import javax.validation.UnexpectedTypeException;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.Set;
//
//
///**
// * Desc <p>Controller统一异常advice</p>
// *
// * @author xlr
// * @date 2017/7/3
// */
//@RestControllerAdvice
//@Slf4j
//public class DefaultExceptionAdvice {
//
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler({HttpMessageNotReadableException.class, })
//    public RestResult handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
//        log.error("参数解析失败", e);
//        return RestResultBuilder.builder().failure().message(e.getMessage()).build();
//    }
//    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
//    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
//    public RestResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
//        log.error("不支持当前请求方法", e);
//        return RestResultBuilder.builder().failure().message(e.getMessage()).build();
//    }
//
//    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
//    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
//    public RestResult handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
//        log.error("不支持当前媒体类型", e);
//        return RestResultBuilder.builder().failure().message(e.getMessage()).build();
//    }
//
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler({SQLException.class})
//    public RestResult handleSQLException(SQLException e) {
//        log.error("服务运行SQLException异常", e);
//        return RestResultBuilder.builder().failure().message(e.getMessage()).build();
//    }
//
//    /**
//     * 所有异常统一处理
//     *
//     * @return RestResult
//     */
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(Exception.class)
//    public RestResult handleException(Exception ex) {
//        log.error("未知异常", ex);
//        IError error;
//        String extMessage = null;
//        if (ex instanceof BindException) {
//            error = DefaultError.INVALID_PARAMETER;
//            List<ObjectError> errors = ((BindException) ex).getAllErrors();
//            if (errors.size() != 0) {
//                StringBuilder msg = new StringBuilder();
//                for (ObjectError objectError : errors) {
//                    msg.append("Field error in object '").append(objectError.getObjectName()).append(" ");
//                    if (objectError instanceof FieldError) {
//                        msg.append("on field ").append(((FieldError) objectError).getField()).append(" ");
//                    }
//                    msg.append(objectError.getDefaultMessage()).append(" ");
//                }
//                extMessage = msg.toString();
//            }
//        } else if (ex instanceof MissingServletRequestParameterException) {
//            error = DefaultError.INVALID_PARAMETER;
//            extMessage = ex.getMessage();
//        } else if (ex instanceof ConstraintViolationException) {
//            error = DefaultError.INVALID_PARAMETER;
//            Set<ConstraintViolation<?>> violations = ((ConstraintViolationException) ex).getConstraintViolations();
//            final StringBuilder msg = new StringBuilder();
//            for (ConstraintViolation<?> constraintViolation : violations) {
//                msg.append(constraintViolation.getPropertyPath()).append(":").append(constraintViolation.getMessage()).append("\n");
//            }
//            extMessage = msg.toString();
//        } else if (ex instanceof HttpMediaTypeNotSupportedException) {
//            error = DefaultError.CONTENT_TYPE_NOT_SUPPORT;
//            extMessage = ex.getMessage();
//        } else if (ex instanceof HttpMessageNotReadableException) {
//            error = DefaultError.INVALID_PARAMETER;
//            extMessage = ex.getMessage();
//        } else if (ex instanceof MethodArgumentNotValidException) {
//            error = DefaultError.INVALID_PARAMETER;
//            final BindingResult result = ((MethodArgumentNotValidException) ex).getBindingResult();
//            if (result.hasErrors()) {
//                extMessage = result.getAllErrors().get(0).getDefaultMessage();
//            } else {
//                extMessage = ex.getMessage();
//            }
//        } else if (ex instanceof HttpRequestMethodNotSupportedException) {
//            error = DefaultError.METHOD_NOT_SUPPORTED;
//            extMessage = ex.getMessage();
//        } else if (ex instanceof UnexpectedTypeException) {
//            error = DefaultError.INVALID_PARAMETER;
//            extMessage = ex.getMessage();
//        } else if (ex instanceof NoHandlerFoundException) {
//            error = DefaultError.SERVICE_NOT_FOUND;
//            extMessage = ex.getMessage();
//        } else {
//            error = DefaultError.SYSTEM_INTERNAL_ERROR;
//            extMessage = ex.getMessage();
//        }
//        return RestResultBuilder.builder().failure().message(extMessage).build();
//    }
//
//    /**
//     * BusinessException 业务异常处理
//     *
//     * @return RestResult
//     */
//    @ResponseStatus(HttpStatus.OK)
//    @ExceptionHandler(BusinessException.class)
//    public RestResult handleException(BusinessException e) {
//        log.error("业务异常", e);
//        return RestResultBuilder.builder().failure().message(e.getMessage()).build();
//    }
//
//    /**
//     * BusinessException 业务异常处理
//     *
//     * @return RestResult
//     */
//    @ResponseStatus(HttpStatus.OK)
//    @ExceptionHandler(ValidateCodeException.class)
//    public RestResult handleException(ValidateCodeException e) {
//        log.error("请求参数校验异常", e.getExtMessage());
//        return RestResultBuilder.builder().failure().message(e.getMessage()).build();
//    }
//
//    /**
//     * InvalidParamException 参数校验异常
//     *
//     * @return RestResult
//     */
//    @ResponseStatus(HttpStatus.OK)
//    @ExceptionHandler(InvalidParamException.class)
//    public RestResult handleException(InvalidParamException e) {
//        log.error("参数验证失败", e);
//        return RestResultBuilder.builder().failure().message(e.getMessage()).build();
//
//    }
//
//    /**
//     * ClientException 客户端异常 给调用者 app,移动端调用
//     *
//     * @return RestResult
//     */
//    @ResponseStatus(HttpStatus.OK)
//    @ExceptionHandler(ClientException.class)
//    public RestResult handleException(ClientException e) {
//        log.error("客户端异常", e);
//        return RestResultBuilder.builder().failure().message(e.getMessage()).build();
//    }
//
//    /**
//     * ServerException 服务端异常, 微服务服务端产生的异常
//     *
//     * @return RestResult
//     */
//    @ResponseStatus(HttpStatus.OK)
//    @ExceptionHandler(ServerException.class)
//    public RestResult handleException(ServerException e) {
//        log.error("ServerException 服务端异常{}",e.getMessage());
//        return RestResultBuilder.builder().failure().message(e.getMessage()).build();
//    }
//
//    /**
//     * gson 解析异常。
//     *
//     * @return RestResult
//     */
//    @ResponseStatus(HttpStatus.OK)
//    @ExceptionHandler(JsonSyntaxException.class)
//    public RestResult handleException(JsonSyntaxException e) {
//        log.error("gson 解析异常{}",e.getMessage());
//        return RestResultBuilder.builder().failure().message("gson解析异常").build();
//    }
//
//    /**
//     * sql 异常。
//     *
//     * @return RestResult
//     */
//    @ResponseStatus(HttpStatus.OK)
//    @ExceptionHandler(BadSqlGrammarException.class)
//    public RestResult handleException(BadSqlGrammarException e) {
//        log.error("sql 异常{}",e.getMessage());
//        return RestResultBuilder.builder().failure().message(e.getMessage()).build();
//    }
//
//
//    /**
//     * json解析 异常。
//     *
//     * @return RestResult
//     */
//    @ResponseStatus(HttpStatus.OK)
//    @ExceptionHandler(MalformedJsonException.class)
//    public RestResult handleException(MalformedJsonException e) {
//        log.error("json解析异常{}",e.getMessage());
//        return RestResultBuilder.builder().failure().message("json解析异常").build();
//    }
//
//}
