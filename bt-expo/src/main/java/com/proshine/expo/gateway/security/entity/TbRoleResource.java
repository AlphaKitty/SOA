package com.proshine.expo.gateway.security.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author zyl
 * @since 2019-10-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbRoleResource implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String roleId;

    private String resourceId;


}
