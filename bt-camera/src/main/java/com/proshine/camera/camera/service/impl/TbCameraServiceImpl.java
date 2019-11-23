package com.proshine.camera.camera.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.proshine.camera.camera.mapper.TbCameraMapper;
import com.proshine.camera.camera.service.ITbCameraService;
import com.proshine.expo.camera.camera.entity.TbCamera;
import org.springframework.stereotype.Service;

/**
 * 摄像头信息表 服务实现类
 *
 * @author zyl
 * @since 2019-10-22
 */
@Service
public class TbCameraServiceImpl extends ServiceImpl<TbCameraMapper, TbCamera> implements ITbCameraService {

}
