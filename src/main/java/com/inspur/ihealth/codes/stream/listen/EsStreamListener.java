package com.inspur.ihealth.codes.stream.listen;

import com.inspur.ihealth.codes.stream.EsChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;

import java.text.SimpleDateFormat;
import java.util.Date;

@EnableBinding(value = EsChannel.class)
@Slf4j
public class EsStreamListener {

    /**
     * 从缺省通道接收消息
     *
     * @param message
     */
    @StreamListener(EsChannel.ES_DEFAULT_INPUT)
    public void receive(Message<String> message) {

        Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);

        if (acknowledgment != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            log.info(sdf.format(new Date()) + "------start：   " + message);
            try {
                Thread.sleep(1000 * 5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //log.info(sdf.format(new date())+"------end--------");

            acknowledgment.acknowledge(); //消费结束，告知kafka消息已收到，调整消息的偏移量。
        }
    }

    /**
     * 从告警通道接收消息
     *
     * @param message
     */
    //@StreamListener(EsChannel.ES_ALARM_INPUT)
    public void receiveAlarm(Message<String> message) {
        System.out.println("订阅告警消息：" + message);
    }
}
