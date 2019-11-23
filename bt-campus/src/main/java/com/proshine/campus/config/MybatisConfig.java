package com.proshine.campus.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName: MybatisConfig
 * Description: Mybatis配置类
 * Author: zyl
 * Date: 2019/10/16 11:03
 */
@Configuration
@MapperScan("com.proshine.campus.*.mapper")
public class MybatisConfig {

}
