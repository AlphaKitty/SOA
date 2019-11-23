package com.proshine.expo.terminal.terminal.vo;

import com.proshine.expo.campus.classroom.entity.TbClassroom;
import com.proshine.expo.terminal.terminal.entity.TbTerminal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TerminalVo extends TbTerminal {

    private TbClassroom classroom;

    private String modelName;
}
