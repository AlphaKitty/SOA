package com.proshine.expo.terminal.terminal.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TerminalRelevanceVO implements Comparable<TerminalRelevanceVO> {

    private String id;

    private String cstmId;

    private String terminalName;

    private int beUsing;

    private String catId;

    private String modelName;

    private String classroomName;

    @Override
    public int compareTo(TerminalRelevanceVO o) {
        return Integer.compare(this.getBeUsing(), o.getBeUsing());
    }
}
