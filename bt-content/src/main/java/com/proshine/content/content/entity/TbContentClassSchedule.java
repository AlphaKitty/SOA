package com.proshine.content.content.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 内容管理课程表模块
 *
 * @author zyl
 * @since 2019-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_content_class_schedule")
public class TbContentClassSchedule implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 客户域
     */
    private String cstmId;

    /**
     * 课表名称
     */
    private String classScheduleName;

    /**
     * 创建人id
     */
    private String createUserId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 上午课时
     */
    private Boolean morningSection;

    /**
     * 下午课时
     */
    private Boolean afternoonSection;

    /**
     * 晚上课时
     */
    private Boolean nightSection;

    /**
     * json格式存储课表信息
     */
    private String classScheduleContent;


}
