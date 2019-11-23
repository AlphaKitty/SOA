package com.proshine.expo.gateway.security.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.proshine.expo.campus.teacher.entity.TbTeacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zyl
 * @since 2019-10-10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TbUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String usname;

    @JsonIgnore
    private String psword;

    private LocalDateTime lastLoginTime;

    private String lastLoginIpaddr;

    private LocalDateTime creatTime;

    private String cstmId;

    private String descs;

    private String email;

    private String cellphone;

    private String nfcId;

    private String imageId;

    private Integer sex;

    private String extraId;

    private String nickname;

    /**
     * 0 失效 1有效
     */
    private Integer isAvailable;

    /**
     * 0不是 1是 默认的超级管理员
     */
    private Integer defaultSuper;

    private String openid;

    // 关联的客户域
    @TableField(exist = false)
    private TbCustomer customer;
    // 关联的角色
    @TableField(exist = false)
    private List<TbRole> roles;
    //关联的教师
    @TableField(exist = false)
    private TbTeacher teacher;
}
