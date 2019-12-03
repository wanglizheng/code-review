package com.inspur.ihealth.codes.websocket;

import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@CommonsLog
@Component
//@ServerEndpoint("/websocket/{docid}")
public class WebSocketServer {

    /**
     * websocket server
     * 回调函数：OnOpen/OnMessage/OnError/OnClose
     */

    private static int onlineCount = 0;
    /**
     * 以医生Id为key， 将WebSocketServer连接对象作为对象保存起来
     */
    private static Map<String, WebSocketServer> clients = new ConcurrentHashMap<String, WebSocketServer>();
    private Session session;
    private String docid;

    @OnOpen
    public void onOpen(@PathParam("username") String username, Session session) {

        this.docid = docid;
        this.session = session;

        addOnlineCount();
        clients.put(docid, this);
        log.info(docid + "已上线，Session Id 为： " + session.getId() );

    }

    @OnClose
    public void onClose() throws IOException {
        clients.remove(docid);
        subOnlineCount();
        log.info(docid + "已下线，Session Id 为： " + session.getId() );
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }


    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount ++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount --;
    }

}
