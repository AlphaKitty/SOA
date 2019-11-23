package com.proshine.midware;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: SpringBootMidware
 * Description: 中间件服务入口
 * Author: zyl
 * Date: 2019/8/24 17:52
 * Version 1.0
 */
@Log4j2
@RestController
@SpringBootApplication
public class SpringBootMidware {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SpringBootMidware.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}

