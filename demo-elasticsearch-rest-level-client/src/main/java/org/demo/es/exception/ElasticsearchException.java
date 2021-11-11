package org.demo.es.exception;

import lombok.Getter;
import org.demo.es.common.ResultCode;


public class ElasticsearchException extends RuntimeException {

    @Getter
    private int errcode;

    @Getter
    private String errmsg;

    public ElasticsearchException(ResultCode resultCode) {
        this(resultCode.getCode(), resultCode.getMsg());
    }

    public ElasticsearchException(String message) {
        super(message);
    }

    public ElasticsearchException(Integer errcode, String errmsg) {
        super(errmsg);
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    public ElasticsearchException(String message, Throwable cause) {
        super(message, cause);
    }

    public ElasticsearchException(Throwable cause) {
        super(cause);
    }

    public ElasticsearchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
