package com.proshine.midware.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis配置类
 * ClassName: MybatisConfig
 * Author: zyl
 * Date: 2019/10/16 11:03
 */
@Configuration
@MapperScan("com.proshine.midware.*.mapper")
public class MybatisConfig {

}
