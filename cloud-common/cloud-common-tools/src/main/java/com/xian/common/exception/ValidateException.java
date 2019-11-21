package com.xian.common.exception;

/**
 * 校验异常
 *
 * @author xlr
 * @date 2017年12月21日20:45:38
 */
public class ValidateException extends BusinessException {

    private static final long serialVersionUID = -7285211528095468156L;

    public ValidateException(String msg) {
        super(msg);
    }

}
