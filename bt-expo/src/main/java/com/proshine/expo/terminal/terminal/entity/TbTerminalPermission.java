package com.proshine.expo.terminal.terminal.entity;

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
@TableName("tb_terminal_permission")
public class TbTerminalPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 终端表字段的ID
     */
    private String terId;

    /**
     * 用户ID
     */
    private String userId;


}
