package com.inspur.ihealth.codes.stream;

/**
 * 消息发送服务
 */
public interface SendMessageService {

    boolean sendToDefaultChannel(String msg);
}
