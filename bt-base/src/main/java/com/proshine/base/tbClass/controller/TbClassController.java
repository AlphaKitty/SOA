package com.proshine.base.tbClass.controller;

import com.proshine.base.tbClass.entity.TbClass;
import com.proshine.base.tbClass.service.impl.TbClassServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 班级信息表 前端控制器
 *
 * @author zyl
 * @since 2019-10-08
 */
@RestController
@RequestMapping("/tbClass/tb-class")
public class TbClassController {
    @Autowired
    private TbClassServiceImpl tbClassServiceImpl;

    @RequestMapping("/getById")
    @ResponseBody
    public Object getById(@Valid TbClass tbClass, BindingResult errors) {
        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(error -> {
                FieldError fieldError = ((FieldError) error);
                String message = fieldError.getDefaultMessage();
                System.out.println(message);
            });
        }
        return tbClassServiceImpl.getById(tbClass.getId());
    }
}
