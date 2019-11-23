package com.proshine.base.tbClass.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.proshine.base.tbClass.entity.TbClass;
import com.proshine.base.tbClass.mapper.TbClassMapper;
import com.proshine.base.tbClass.service.ITbClassService;
import org.springframework.stereotype.Service;

/**
 * 班级信息表 服务实现类
 *
 * @author zyl
 * @since 2019-10-08
 */
@Service
public class TbClassServiceImpl extends ServiceImpl<TbClassMapper, TbClass> implements ITbClassService {

}
