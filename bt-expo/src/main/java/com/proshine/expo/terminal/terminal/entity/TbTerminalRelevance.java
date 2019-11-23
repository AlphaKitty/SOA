package com.proshine.expo.terminal.terminal.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * tb_terminal和其余表的关联表
 *
 * @author zyl
 * @since 2019-10-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_terminal_relevance")
public class TbTerminalRelevance implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 客户域id冗余,加索引时使用
     */
    private String cstmId;

    /**
     * tb_terminal主键id
     */
    private String terminalId;

    /**
     * 其余表的主键id
     */
    private String relevanceId;

    /**
     * 0:tb_content_aphorism  1:tb_file  2:tb_content_class_schedule 3:tb_content_notification(后面可以自行添加,必须添加注释)
     */
    private Boolean tableType;


}
