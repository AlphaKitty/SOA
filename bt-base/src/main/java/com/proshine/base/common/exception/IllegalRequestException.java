package com.proshine.base.common.exception;

/**
 * Created by wink on 17/9/19.
 */
public class IllegalRequestException extends ControllerException {
    private static final long serialVersionUID = -2504093591957374869L;

    public IllegalRequestException(String errMsg) {
        super(504, errMsg);
    }
}
