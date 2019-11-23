package com.proshine.attendance.attendance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.proshine.expo.attendance.attendance.entity.TbJeecgApiTerminalCategoryRelevance;

/**
 * 班级所属分组与终端的绑定关系，通过终端ID可以获取到其对应的班级分组ID，从而可以获取班级课表和上课的学生 服务类
 *
 * @author zyl
 * @since 2019-10-22
 */
public interface ITbJeecgApiTerminalCategoryRelevanceService extends IService<TbJeecgApiTerminalCategoryRelevance> {

}
