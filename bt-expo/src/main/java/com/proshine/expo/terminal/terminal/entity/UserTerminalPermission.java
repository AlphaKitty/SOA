package com.proshine.expo.terminal.terminal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTerminalPermission implements Serializable {
    private String id;
    private String terId;
    private String userId;
    private String terminalId;
    private String terminalName;
    private String ipaddr;
    private int onlineState;
}
