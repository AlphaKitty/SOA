package com.proshine.expo.midware.file.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文件表
 *
 * @author zyl
 * @since 2019-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbFile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件/目录唯一ID
     */
    private String id;

    /**
     * 客户ID
     */
    private String cstmId;

    /**
     * 所属目录ID
     */
    private String parentId;

    /**
     * 文件/目录名称
     */
    private String name;

    /**
     * 文件/目录描述
     */
    private String desc;

    /**
     * 类型：0=文件夹|1=图片|2=文档|3=音频|4=视频|99=其他
     */
    private String type;

    /**
     * 后缀名称：doc|jpg|png等
     */
    private String suffix;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 文件路径
     */
    private String path;

    /**
     * 文件md5
     */
    private String md5;

    /**
     * 使用计数
     */
    private Integer usageCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建者ID
     */
    private String creatorId;

    /**
     * 创建者姓名
     */
    private String creatorName;

    /**
     * 审核时间
     */
    private LocalDateTime checkTime;

    /**
     * 审核者ID
     */
    private String checkUserId;

    /**
     * 审核者姓名
     */
    private String checkUserName;

    /**
     * 审核状态：1表示通过，0表示不通过
     */
    private String checkStatus;

    /**
     * 是否删除：1表示删除，0表示不删除
     */
    private String isDelete;

    /**
     * 素材库类型：1表示共有库，0表示私有库,
     */
    private String libraryType;


}
