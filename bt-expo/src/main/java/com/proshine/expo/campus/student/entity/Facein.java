package com.proshine.expo.campus.student.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class Facein implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("Stu_Id")
    private String stuId;

    @TableField("Image_Id")
    private String imageId;

    @TableField("Terminal_Ip")
    private String terminalIp;

    @TableField("Url")
    private String Url;

    @TableField("Sign_In")
    private String signIn;


}
