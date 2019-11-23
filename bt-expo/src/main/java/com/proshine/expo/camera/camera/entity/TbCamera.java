package com.proshine.expo.camera.camera.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 摄像头信息表
 *
 * @author zyl
 * @since 2019-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbCamera implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 摄像头id
     */
    private String id;

    /**
     * 摄像头名称
     */
    private String name;

    /**
     * 播放地址url
     */
    private String url;

    /**
     * 根据URL解析的ip
     */
    private String ip;

    /**
     * 备注
     */
    private String comment;

    /**
     * 等待时间 单位秒 最短10s 最长180s
     */
    private Integer waitSec;

    /**
     * 所属分组id
     */
    private String groupId;


}
