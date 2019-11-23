package com.proshine.expo.component.screen.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author zyl
 * @since 2019-10-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbArtScreen implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private byte[] id;

    /**
     * 艺屏名称
     */
    private String artScreenName;

    /**
     * 目录类型：
     * 11=>科学主题屏,12=>德育培育主题屏,13 =>德育践行主题屏,14=>家国情怀主题屏,15=>社会关爱主题屏,16=>人格修养主题屏,17=>美术主题屏,18=>影视文学主题屏,19=>思想建设屏,20=>红色中国,21=>美丽中国梦
     */
    private String libraryType;

    /**
     * 艺屏封面图
     */
    private String imgUrl;

    /**
     * 添加时间
     */
    private Long createTime;

    /**
     * 最后更新时间
     */
    private Long lastUpdateTime;


}
