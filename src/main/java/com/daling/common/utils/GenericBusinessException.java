package com.daling.common.utils;

/**
 * @author liuyi
 * @since 2020/1/14
 *
 * 自定义业务异常基类
 */
public class GenericBusinessException extends RuntimeException {

    private int errorCode = 0;
    private String errorDescription = "";
    private String errorDetail = "";

    public GenericBusinessException(){
    }

    /**
     * 不要轻易使用
     * @param cause
     */
    public GenericBusinessException(Throwable cause){
        super(cause);
    }
    public GenericBusinessException(int errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public GenericBusinessException(String errorDescription) {
        super(errorDescription);
        this.errorDescription = errorDescription;
    }
    public GenericBusinessException(String errorDescription, Throwable cause) {
        super(cause);
        this.errorDescription = errorDescription;
    }

    public GenericBusinessException(int errorCode, String errorDescription) {
        super(errorDescription);
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }
    public GenericBusinessException(int errorCode, String errorDescription, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public GenericBusinessException(String errorDescription , String errorDetail) {
        super(errorDescription);
        this.errorDescription = errorDescription;
        this.errorDetail = errorDetail;
    }
    public GenericBusinessException(int errorCode, String errorDescription , String errorDetail) {
        super(errorDescription);
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.errorDetail = errorDetail;
    }

    public GenericBusinessException(String errorDescription , String errorDetail, Throwable cause) {
        super(cause);
        this.errorDescription = errorDescription;
        this.errorDetail = errorDetail;
    }

    public GenericBusinessException(int errorCode, String errorDescription , String errorDetail, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.errorDetail = errorDetail;
    }




    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }
}
