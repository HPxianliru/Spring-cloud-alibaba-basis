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

    @ApiModelProperty("status状态码 0失败 1成功")
    protected int status;

    @ApiModelProperty("msg返回信息")
    protected String msg;

    @ApiModelProperty("返回json")
    protected T data;

    public static RestResultBuilder builder() {
        RestResultBuilder restResultBuilder = new RestResultBuilder();
        return restResultBuilder;
    }

    public RestResultBuilder code(int code) {
        this.status = code;
        return this;
    }

    public RestResultBuilder message(String message) {
        this.msg = message;
        return this;
    }

    public RestResultBuilder data(T data) {
        this.data = data;
        return this;
    }

    public RestResultBuilder errorCode(ErrorCode errorCode) {
        this.status = errorCode.getCode();
        this.msg = errorCode.getMessage();
        return this;
    }

    public RestResultBuilder success() {
        this.status = GlobalErrorCode.SUCCESS.getCode();
        this.msg = GlobalErrorCode.SUCCESS.getMessage();
        return this;
    }

    public RestResultBuilder success(T data) {
        this.status = GlobalErrorCode.SUCCESS.getCode();
        this.msg = GlobalErrorCode.SUCCESS.getMessage();
        this.data = data;
        return this;
    }

    public RestResultBuilder failure() {
        this.status = GlobalErrorCode.FAILURE.getCode();
        this.msg = GlobalErrorCode.FAILURE.getMessage();
        return this;
    }

    public RestResultBuilder failure(T data) {
        this.status = GlobalErrorCode.FAILURE.getCode();
        this.msg = GlobalErrorCode.FAILURE.getMessage();
        this.data = data;
        return this;
    }
    public RestResultBuilder objNull() {
        this.status = GlobalErrorCode.FAILURE.getCode();
        this.msg = GlobalErrorCode.FAILURE.getMessage();
        return this;
    }
    public RestResultBuilder objNull(T data) {
        this.status = GlobalErrorCode.OBJ_NULL.getCode();
        this.msg = GlobalErrorCode.OBJ_NULL.getMessage();
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
        return new RestResult<T>(this.status, this.msg, this.data);
    }

    public RestResult build(RestResult restResult) {
        return restResult;
    }
}
