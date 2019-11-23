package com.proshine.attendance.attendance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.proshine.expo.attendance.attendance.entity.TbJeecgApiTerminalAttendanceRuleRelevance;

/**
 * 班级所属分组与终端的绑定关系，通过终端ID可以获取到其对应的班级分组ID，从而可以获取班级课表和上课的学生 Mapper 接口
 *
 * @author zyl
 * @since 2019-10-22
 */
public interface TbJeecgApiTerminalAttendanceRuleRelevanceMapper extends BaseMapper<TbJeecgApiTerminalAttendanceRuleRelevance> {

}
