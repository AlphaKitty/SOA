package com.proshine.expo.terminal.terminal.entity;

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
public class TbDeviceModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String cstmId;

    private String modelName;

    private String descs;

    private Integer modelVersion;

    private LocalDateTime createTime;

    private String createUserName;

    private String createUserId;


}
