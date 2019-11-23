package com.proshine.expo.component.screen.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("tb_screen_play")
public class TbScreenPlay implements Serializable {

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
     * 艺屏ID
     */
    private String screenId;

    /**
     * 艺屏名称
     */
    private String screenName;

    /**
     * tb_file主键id
     */
    private String fileId;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 类型：0=文件夹|1=图片|2=文档|3=音频|4=视频|99=其他
     */
    private String fileType;

    /**
     * 视频时长
     */
    private String fileSize;

    /**
     * 视频封面截图
     */
    private String fileImageMd5;

    /**
     * 播放日期
     */
    private Long playDate;

    /**
     * 是否已删除，0未删除，1以删除
     */
    private Integer deleteStatus;

    /**
     * 播放顺序【升序】
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 最后更新时间
     */
    private Long lastUpdateTime;


}
