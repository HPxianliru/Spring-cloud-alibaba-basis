package com.xian.common.model;


import com.xian.common.exception.ErrorCode;
import com.xian.common.exception.GlobalErrorCode;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: xlr
import com.xian.common.exception.ErrorCode;
import com.xian.common.exception.GlobalErrorCode;

/**
 * @Author: xlr
 * @Date: Created in 11:29 PM 2019/9/11
 */
public class RestResultBuilder<T> {

    @ApiModelProperty("code 0失败 1成功")
    protected int code;

    @ApiModelProperty("msg返回信息")
    protected String message;

    @ApiModelProperty("返回json")
    protected T data;

    public static RestResultBuilder builder() {
        RestResultBuilder restResultBuilder = new RestResultBuilder();
        return restResultBuilder;
    }

    public RestResultBuilder code(int code) {
        this.code = code;
        return this;
    }

    public RestResultBuilder message(String message) {
        this.message = message;
        return this;
    }

    public RestResultBuilder data(T data) {
        this.data = data;
        return this;
    }

    public RestResultBuilder errorCode(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        return this;
    }

    public RestResultBuilder success() {
        this.code = GlobalErrorCode.SUCCESS.getCode();
        this.message = GlobalErrorCode.SUCCESS.getMessage();
        return this;
    }

    public RestResultBuilder success(T data) {
        this.code = GlobalErrorCode.SUCCESS.getCode();
        this.message = GlobalErrorCode.SUCCESS.getMessage();
        this.data = data;
        return this;
    }

    public RestResultBuilder failure() {
        this.code = GlobalErrorCode.FAILURE.getCode();
        this.message = GlobalErrorCode.FAILURE.getMessage();
        return this;
    }

    public RestResultBuilder failure(T data) {
        this.code = GlobalErrorCode.FAILURE.getCode();
        this.message = GlobalErrorCode.FAILURE.getMessage();
        this.data = data;
        return this;
    }
    public RestResultBuilder objNull() {
        this.code = GlobalErrorCode.FAILURE.getCode();
        this.message = GlobalErrorCode.FAILURE.getMessage();
        return this;
    }
    public RestResultBuilder objNull(T data) {
        this.code = GlobalErrorCode.OBJ_NULL.getCode();
        this.message = GlobalErrorCode.OBJ_NULL.getMessage();
        this.data = data;
        return this;
    }


     public RestResultBuilder result(boolean successful) {
        if (successful) {
            return this.success();
        } else {
            return this.failure();
        }
    }

    public RestResultBuilder success(Boolean result) {
        if (result.equals(Boolean.TRUE)) {
            success();
        } else {
            failure();
        }
        return this;
    }

    public RestResult build() {
        return new RestResult<T>(this.code, this.message, this.data);
    }

    public RestResult build(RestResult restResult) {
        return restResult;
    }
}
