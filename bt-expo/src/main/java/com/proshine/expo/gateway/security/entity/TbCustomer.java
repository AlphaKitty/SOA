package com.proshine.expo.gateway.security.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
public class TbCustomer implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String shortName;

    private String names;

    private String descs;

    private LocalDateTime creatTime;

    private String creatUser;

    @TableField("authenticationCode")
    private String authenticationCode;

    /**
     * 0关闭 1启用
     */
    private Integer isAvailable;

    private String logoImg;


}
