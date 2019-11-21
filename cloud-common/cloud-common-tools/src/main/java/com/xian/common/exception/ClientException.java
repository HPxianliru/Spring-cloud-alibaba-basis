package com.xian.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户端异常.给调用者
 *
 * @author xlr
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ClientException extends BaseException {

    private static final long serialVersionUID = 3076984003548588117L;

    private IError error = DefaultError.CLIENT_EXCEPTION;

    private String extMessage;

    public ClientException(String message) {
        super(message);
        this.error.setErrorMessage(message);
    }

    public ClientException(String message, String extMessage) {
        super(message + ":" + extMessage);
        this.error.setErrorMessage(message);
        this.extMessage = extMessage;
    }

}
