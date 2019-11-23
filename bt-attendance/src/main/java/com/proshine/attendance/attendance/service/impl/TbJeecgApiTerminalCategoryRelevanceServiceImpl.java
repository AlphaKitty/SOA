package com.proshine.attendance.attendance.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.proshine.attendance.attendance.mapper.TbJeecgApiTerminalCategoryRelevanceMapper;
import com.proshine.attendance.attendance.service.ITbJeecgApiTerminalCategoryRelevanceService;
import com.proshine.expo.attendance.attendance.entity.TbJeecgApiTerminalCategoryRelevance;
import org.springframework.stereotype.Service;

/**
 * 班级所属分组与终端的绑定关系，通过终端ID可以获取到其对应的班级分组ID，从而可以获取班级课表和上课的学生 服务实现类
 *
 * @author zyl
 * @since 2019-10-22
 */
@Service
public class TbJeecgApiTerminalCategoryRelevanceServiceImpl extends ServiceImpl<TbJeecgApiTerminalCategoryRelevanceMapper, TbJeecgApiTerminalCategoryRelevance> implements ITbJeecgApiTerminalCategoryRelevanceService {

}
