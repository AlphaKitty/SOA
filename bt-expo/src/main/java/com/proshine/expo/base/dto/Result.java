package com.proshine.expo.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 行政班考勤安卓调用的api返回结果类
 *
 * @param <T>
 * @author 齐辛
 */
@Data
@ApiModel(value = "接口返回对象", description = "接口返回对象")
@NoArgsConstructor
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Integer SC_INTERNAL_SERVER_ERROR_500 = 500;

    private static final Integer SC_OK_200 = 200;
    /**
     * 访问权限认证未通过
     */
    private static final Integer SC_JEECG_NO_AUTHZ = 510;
    @ApiModelProperty(value = "成功标志")
    private boolean success = true;

    @ApiModelProperty(value = "返回处理消息")
    private String message = "操作成功！";

    @ApiModelProperty(value = "返回处理消息")
    private Integer code = 0;

    @ApiModelProperty(value = "返回代码")
    private T result;

    @ApiModelProperty(value = "当时函数执行的时间戳")
    private Long timestamp = System.currentTimeMillis();

    public static Result<Object> ok() {
        Result<Object> r = new Result<>();
        r.setSuccess(true);
        r.setCode(SC_OK_200);
        r.setMessage("成功");
        return r;
    }

    public static Result<Object> ok(String msg) {
        Result<Object> r = new Result<>();
        r.setSuccess(true);
        r.setCode(SC_OK_200);
        r.setMessage(msg);
        return r;
    }

    public static Result<Object> ok(Object data) {
        Result<Object> r = new Result<>();
        r.setSuccess(true);
        r.setCode(SC_OK_200);
        r.setResult(data);
        return r;
    }

    public static Result<Object> error(String msg) {
        return error(SC_INTERNAL_SERVER_ERROR_500, msg);
    }

    public static Result<Object> error(int code, String msg) {
        Result<Object> r = new Result<>();
        r.setCode(code);
        r.setMessage(msg);
        r.setSuccess(false);
        return r;
    }

    public static Result<Object> noauth(String msg) {
        return error(SC_JEECG_NO_AUTHZ, msg);
    }

    public Result<T> error500(String message) {
        this.message = message;
        this.code = SC_INTERNAL_SERVER_ERROR_500;
        this.success = false;
        return this;
    }

    public Result<T> success(String message) {
        this.message = message;
        this.code = SC_OK_200;
        this.success = true;
        return this;
    }
}
