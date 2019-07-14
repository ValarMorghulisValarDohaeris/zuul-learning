package com.lh.zuul.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 2019/7/13.
 *
 * @author hao
 */
@RestController
public class ConsumerController
{

    @RequestMapping("/hi/{name}")
    public String index(@PathVariable String name, String token)
    {
        return name + ",hi!";
    }
}
