package com.proshine.base.common.exception;

/**
 * 树形结构执行插入的时候，父节点不存在抛出该异常
 *
 * @author 杨雪
 */
public class NoParentNodeException extends Exception {
    private static final long serialVersionUID = 8906177507450804174L;
    private String errMsg;

    public NoParentNodeException(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}