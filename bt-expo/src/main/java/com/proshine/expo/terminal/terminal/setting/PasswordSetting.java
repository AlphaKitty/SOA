package com.proshine.expo.terminal.terminal.setting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 终端退出密码实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PasswordSetting extends CommonSetting {
    /**
     * 密码
     * 位数 > 6位
     * 字母与数字的组合
     */
    private String password;
}
