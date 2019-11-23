package com.proshine.expo.gateway.security.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author zyl
 * @since 2019-10-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String roleName;

    private String cstmId;

    private String parentId;

    private String descs;

    /**
     * 0不是默认 1 代表默认
     */
    private Integer isDefault;

    @TableField(exist = false)
    private TbCustomer customer;
    @TableField(exist = false)
    private List<TbResource> resources;

}
