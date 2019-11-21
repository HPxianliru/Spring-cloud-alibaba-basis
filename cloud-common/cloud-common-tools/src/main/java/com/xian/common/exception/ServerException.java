package com.xian.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 服务端异常.
 *
 * @author xlr
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ServerException extends BaseException {

    private static final long serialVersionUID = 3076984003548588117L;

    private IError error = DefaultError.SERVER_EXCEPTION;

    private String extMessage;

    public ServerException(String message) {
        super(message);
        this.error.setErrorMessage(message);
    }

    public ServerException(String message, String extMessage) {
        super(message + ":" + extMessage);
        this.error.setErrorMessage(message);
        this.extMessage = extMessage;
    }
}
