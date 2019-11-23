package com.proshine.campus.classes.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.proshine.campus.classes.mapper.TbClassMapper;
import com.proshine.campus.classes.service.ITbClassService;
import com.proshine.expo.campus.classes.entity.TbClass;
import org.springframework.stereotype.Service;

/**
 * 班级信息表 服务实现类
 *
 * @author zyl
 * @since 2019-10-21
 */
@Service
public class TbClassServiceImpl extends ServiceImpl<TbClassMapper, TbClass> implements ITbClassService {

}
