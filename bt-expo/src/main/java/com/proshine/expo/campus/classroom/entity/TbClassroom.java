package com.proshine.expo.campus.classroom.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 教学楼教室信息表
 *
 * @author zyl
 * @since 2019-10-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbClassroom implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private String id;

    /**
     * 客户域ID
     */
    private String cstmId;

    /**
     * 教室编号, 第三方系统
     */
    private String roomId;

    /**
     * 教室校区ID, 外键关联
     */
    private String roomCompusId;

    /**
     * 终端Id
     */
    private String terminalId;

    /**
     * 最近更新时间
     */
    @TableField("lastUpdateTime")
    private LocalDateTime lastUpdateTime;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private LocalDateTime createTime;

    /**
     * 扩展信息1, 若有代表年段
     */
    private String extend1;

    /**
     * 扩展信息2, 若有代表班级名称
     */
    private String extend2;

    /**
     * 教室名字
     */
    private String roomName;


}
