package com.proshine.gateway.temp;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 后台给前台返回JSON的时候统一用的消息数据格式
 *
 * @author 杨雪
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message<T> implements Serializable {
    private static final long serialVersionUID = 7086146786142625072L;

    //返回状态码
    private int status;

    //附带的消息
    private String msg;

    //附带的数据
    private Object data;

    /**
     * 将当前对象转化为json
     *
     * @return 当前对象的json格式，字符串类型
     */
    public String toJson() {
        return JSON.toJSONString(this);
    }
}
