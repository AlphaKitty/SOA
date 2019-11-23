package com.proshine.base.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by wink on 17/9/17.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FileExistException extends ControllerException {
    private static final long serialVersionUID = 2913818276283908980L;
    private String md5;

    public FileExistException(String errMsg, String md5) {
        super(501, errMsg);
        this.md5 = md5;
    }
}
