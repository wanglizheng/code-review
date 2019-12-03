package com.inspur.ihealth.codes.controller;

import com.inspur.ihealth.codes.stream.SendMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(tags = "Kafka测试服务接口")
@RestController
public class KafkaController {

    @Autowired
    SendMessageService sender;

    @ApiOperation(value = "test1", httpMethod = "POST")
    @PostMapping(value = "/test1", produces = "application/json;charset=UTF-8")
    public void test1(@RequestParam String message, HttpServletRequest request,
                      HttpServletResponse response) {
        sender.sendToDefaultChannel(message);
        sender.sendToDefaultChannel(message);
        sender.sendToDefaultChannel(message);
        sender.sendToDefaultChannel(message);
    }

    @ApiOperation(value = "test", httpMethod = "POST")
    @PostMapping(value = "/test2", produces = "application/json;charset=UTF-8")
    public void test2(@RequestParam String message, HttpServletRequest request,
                      HttpServletResponse response) {
        //sender.sendToAlarmChannel(message);
    }
}
