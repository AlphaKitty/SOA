package com.proshine.expo.terminal.terminal.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TerminalInfo extends CommonSetting {

    /**
     * 终端扩展ID1
     */
    private String extraId1;

    /**
     * 终端扩展ID2
     */
    private String extraId2;

    /**
     * 终端名称
     */
    private String terminalName;


    /**
     * 终端model_divice_name
     */
    private String deviceModelName;
}
