package com.xian.cloud.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Classname
 * @Description
 * @Author xlr
 * @Date 2019-11-07 23:06
 * @Version 1.0
 */
public class ValidateCodeException extends AuthenticationException {

    private static final long serialVersionUID = 5022575393500654459L;

    public ValidateCodeException(String message) {
        super(message);
    }
}
