package com.proshine.expo.terminal.terminal.setting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

/**
 * 设置实体类父类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonSetting {

    /**
     * 配置唯一ID
     */
    private ObjectId id;

    /**
     * 配置版本
     */
    private String version = "";

    /**
     * 配置所属终端ID,现在为虚拟id(terminal主键)
     */
    private String terminalId = "";

    /**
     * 终端所属客户域ID
     */
    private String cstmId = "";

    /**
     * 判断是否属于终端自身的配置
     * true 代表终端自身的配置
     * false 代表是服务端的配置
     */
    private boolean individual;

}
