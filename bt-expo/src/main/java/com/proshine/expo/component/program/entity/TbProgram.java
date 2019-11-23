package com.proshine.expo.component.program.entity;

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
public class TbProgram implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String cstmId;

    private String name;

    private String descs;

    private Integer width;

    private Integer height;

    private String programType;

    private LocalDateTime createTime;

    private String createUser;

    private LocalDateTime lastUpdateTime;

    private String lastUpdateUser;

    private Integer version;

    private Integer examine;

    private String homeLayoutId;

    private String isTemplate;

    private String screenType;

    private String thumbnail;

    private String strategyId;

    private String classroomType;

    private String programStyleType;

    private String themeStyleType;


}
