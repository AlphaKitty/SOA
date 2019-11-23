package com.proshine.base.access.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.proshine.base.tbClass.service.impl.TbClassServiceImpl;
import com.proshine.expo.ExpoService;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class BaseController implements ExpoService {

    @Autowired
    private TbClassServiceImpl tbClassServiceImpl;

    @Override
    public String sayHello(String name) {
        return tbClassServiceImpl.getById(1001).toString();
    }
}
