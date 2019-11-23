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
public class TbCustomerScrreen implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private String id;

    /**
     * 客户域ID
     */
    private String customerId;

    /**
     * 客户域名称
     */
    private String customerName;

    /**
     * 艺屏表主键ID
     */
    private String artScreenId;

    /**
     * 艺屏名称
     */
    private String artScreenName;

    /**
     * 艺屏目录类型
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
