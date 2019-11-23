package com.proshine.expo.terminal.terminal.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zyl
 * @since 2019-10-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_terminal_category")
public class TbTerminalCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String cstmId;

    private String categoryName;

    private String categoryDesc;

    private String parentId;

    private LocalDateTime createTime;

    private String createUser;


}
