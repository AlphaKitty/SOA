package com.proshine.midware.file.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.proshine.expo.midware.file.entity.TbFile;
import com.proshine.midware.file.mapper.TbFileMapper;
import com.proshine.midware.file.service.ITbFileService;
import org.springframework.stereotype.Service;

/**
 * 文件表 服务实现类
 *
 * @author zyl
 * @since 2019-10-22
 */
@Service
public class TbFileServiceImpl extends ServiceImpl<TbFileMapper, TbFile> implements ITbFileService {

}
