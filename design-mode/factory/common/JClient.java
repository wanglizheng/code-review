package com.inspur.jpush.common;

import com.inspur.jpush.domain.RequestParam;
import org.junit.Test;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.push.model.Options;

import java.util.Map;

/**
 * 本例说明极光推送开发大致流程和服务器端的API调用 ， 本例是极光3.0版本
 *
 */
public class JClient {

    private JPushClient jPushClient = null;

    private Boolean production=false;

    public JClient(String apiKey,String masterSecret,Boolean production){
        /**
         * 创建极光推送客户端对象， 有多个。这只是一种方式
         */
        this.jPushClient = new JPushClient(masterSecret,apiKey);
        this.production=production;
    }

    /**
     * 给使用该应用app的所有用户发送通知消息
     */
    public PushResult PushNoticeToAll(String notice) throws APIConnectionException, APIRequestException {

            PushResult result = jPushClient.sendNotificationAll(notice);
            return result;
    }


    /**
     * 对安卓和IOS设备， 通过别名来发送通知， 别名是一群用户的分组。
     */
    public PushResult PushNoticeByAlias(String title,String notice,Map<String, String> extras,String platForm,String... alias) throws APIConnectionException, APIRequestException {
        /**
         * test是别名， 即所有注册用户是test组的这一群用户进行推送消息。
         * title: 是发送通知时候， 消息的标题。
         * sendMessage: 是发送的消息体。
         */
        switch (platForm) {
            case "IOS":
                return sendIosNotificationWithAlias(notice, extras,alias);
            case "Android":
                return jPushClient.sendAndroidNotificationWithAlias(title,notice,extras,alias);
            default:
                return jPushClient.sendAndroidNotificationWithAlias(title,notice,extras,alias);
        }
    }

    /**
     * 对安卓设备，根据别名（组）来发送消息 ， 而不是通知。
     */
    public  PushResult PushMsgByAlias(String title,String msgContent,String platForm,String... alias) throws APIConnectionException, APIRequestException {


        switch (platForm) {
            case "IOS":
                return sendIosMessageWithAlias(title,msgContent,alias);
            case "Android":
                return jPushClient.sendAndroidMessageWithAlias(title,msgContent,alias);
            default:
                return jPushClient.sendAndroidMessageWithAlias(title,msgContent,alias);
        }

    }

    private PushResult sendIosNotificationWithAlias(String alert, Map<String, String> extras, String... alias) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.alias(alias)).setNotification(Notification.ios(alert, extras)).setOptions(Options.newBuilder().setApnsProduction(production).build()).build();
        return jPushClient.sendPush(payload);
    }

    private PushResult sendIosMessageWithAlias(String title, String msgContent, String... alias) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.alias(alias)).setMessage(Message.newBuilder().setTitle(title).setMsgContent(msgContent).build()).setOptions(Options.newBuilder().setApnsProduction(production).build()).build();
        return jPushClient.sendPush(payload);
    }

}


