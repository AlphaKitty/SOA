package com.proshine.partner.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName: MybatisConfig
 * Description: Mybatis配置类
 * Author: zyl
 * Date: 2019/10/16 11:03
 */
@Configuration
// TODO: 2019/10/12 zylTodo 新增模块这里需要改
@MapperScan("com.proshine.partner.*.mapper")
public class MybatisConfig {

}
