package com.inspur.jpush.common;

import com.inspur.jpush.config.ConfItem;
import com.inspur.jpush.config.JPushConf;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JPushFactory {
    private static Map<String,JClient> clients=new HashMap<String,JClient>();

    public JPushFactory(JPushConf conf) {
        for (ConfItem item :conf.getConf()) {
            clients.put(item.getName(),new JClient(item.getKey(),item.getSecret(),item.getProduction()));
        }
    }

    public static JClient getMe(String clientName) {
       return clients.get(clientName);
    }
}
