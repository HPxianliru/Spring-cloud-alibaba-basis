package com.xian.common.exception;



/**
 * 说明：默认异常
 *
 * @author zhangwei
 * @date 2017年11月18日23:45:26
 */
public enum DefaultError implements IError {
    /**
     * 系统内部错误
     */
    SYSTEM_INTERNAL_ERROR("0000", "系统错误"),
    /**
     * 无效参数
     */
    INVALID_PARAMETER("0001", "参数验证失败"),
    /**
     * 服务不存在
     */
    SERVICE_NOT_FOUND("0002", "服务不存在"),
    /**
     * 参数不全
     */
    PARAMETER_REQUIRED("0003", "参数不全"),
    /**
     * 参数过长
     */
    PARAMETER_MAX_LENGTH("0004", "参数过长"),
    /**
     * 参数过短
     */
    PARAMETER_MIN_LENGTH("0005", "参数过短"),
    /**
     * 认证失败
     */
    AUTHENTICATION_ERROR("0006", "认证失败"),
    /**
     * 请求方法出错
     */
    METHOD_NOT_SUPPORTED("0008", "请求方法出错"),
    /**
     * 不支持的content类型
     */
    CONTENT_TYPE_NOT_SUPPORT("0009", "不支持的content类型"),
    /**
     * json格式化出错
     */
    JSON_FORMAT_ERROR("0010", "json格式化出错"),
    /**
     * 远程调用出错
     */
    CALL_REMOTE_ERROR("0011", "远程调用出错"),
    /**
     * 服务运行SQLException异常
     */
    SQL_EXCEPTION("0012", "服务运行SQL异常"),
    /**
     * 客户端异常 给调用者 app,移动端调用
     */
    CLIENT_EXCEPTION("0013", "客户端异常"),
    /**
     * 服务端异常, 微服务服务端产生的异常
     */
    SERVER_EXCEPTION("0014", "服务端异常"),
    /**
     * 授权失败 禁止访问
     */
    ACCESS_DENIED("0015", "没有访问权限"),
    /**
     * 演示环境 没有权限访问
     */
    SHOW_AUTH_CONTROL("0016", "演示环境,没有权限访问"),
    /**
     * 业务异常
     */
    BUSINESS_ERROR("0017", "业务异常")
    ;

    String errorCode;
    String errorMessage;
    private static final String NS = "SYS";

    DefaultError(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public String getNameSpace() {
        return NS;
    }

    @Override
    public String getErrorCode() {
        return NS + "." + this.errorCode;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
