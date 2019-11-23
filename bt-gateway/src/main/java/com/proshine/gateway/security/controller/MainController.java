package com.proshine.gateway.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ClassName: MainController
 * Description: MainController
 * Author: zyl
 * Date: 2019/10/10 13:59
 */
@Controller
@RequestMapping(value = "/")
// TODO: 2019/10/21 zylTodo 后面删掉
public class MainController {

    public MainController() {
    }

    @ResponseBody
    @GetMapping("/login")
    public Object login() {
        return "Hello Spring Security";
    }

    @ResponseBody
    @GetMapping("/success")
    public Object success() {
        return "success";
    }

    @ResponseBody
    @GetMapping("/failure")
    public Object failure() {
        return "failure";
    }
}
