package com.inspur.ihealth.codes.stream.impl;

import com.inspur.ihealth.codes.stream.EsChannel;
import com.inspur.ihealth.codes.stream.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * 消息发送
 */
@Service
//@EnableBinding(EsChannel.class) 也可加载入库函数上。
public class SendMessageServiceImpl implements SendMessageService {

    @Autowired
    EsChannel esChannel;

    @Override
    public boolean sendToDefaultChannel(String msg) {
        Message<String> message = MessageBuilder.withPayload(msg).build();
        return esChannel.sendEsDefaultMessage().send(message);
    }
}
