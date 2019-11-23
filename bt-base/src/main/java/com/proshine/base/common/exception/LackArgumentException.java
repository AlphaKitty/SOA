package com.proshine.base.common.exception;

/**
 * Created by wink on 17/8/31.
 */
public class LackArgumentException extends ControllerException {
    private static final long serialVersionUID = 7783048334439919713L;

    public LackArgumentException(int statusCode, String errMsg) {
        super(statusCode, errMsg);
    }

    public LackArgumentException(String errMsg) {
        super(500, errMsg);
    }
}
