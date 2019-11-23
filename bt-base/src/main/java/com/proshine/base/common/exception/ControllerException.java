package com.proshine.base.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by wink on 17/8/31.
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ControllerException extends RuntimeException {
    private static final long serialVersionUID = 1300838057268629997L;
    private int statusCode;
    private String errMsg;

}
