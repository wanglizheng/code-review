package com.inspur.ihealth.codes;

import com.inspur.ihealth.codes.stream.SendMessageService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class KafkaSendMsg {

    @Autowired
    SendMessageService sendMessageService;

    @Test
    public void sendTest() {

        log.info(String.valueOf(sendMessageService.sendToDefaultChannel("hello world")));
        log.info("hello world");
    }
}
