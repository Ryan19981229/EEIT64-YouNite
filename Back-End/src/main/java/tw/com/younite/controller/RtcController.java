package tw.com.younite.controller;


import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@ServerEndpoint("/msgServer2/{userId}/{room}")
@Component
@Scope("prototype")
public class RtcController {


    private static int onlineCount = 0;

    private static final Map<String, Set<Session>> webSocketMap =new HashMap<>();

    private final Set<Session> sessions=new HashSet<>();

    private String userId = "";
    private String roomid = "";


    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId,@PathParam("room") String room) throws IOException {

        this.userId = userId;
        this.roomid=room;

        System.out.println("onOpen()" + session.toString());
//        session.setMaxIdleTimeout(10 * 1000);
        if (sessions.add(session)) {
            addusertoRoom(room, session); // 加入指定的房間
        }
        Set<Session> sessionsget =getUserInRoom(room);
        System.out.println(sessionsget.size());
        if(sessionsget.size()>=2){
            JSONObject jsonObject =new JSONObject();
            System.out.println("ii");
            jsonObject.put("type","connectNow");
            String json =jsonObject.toString();
            for(Session ss:sessionsget){
                if(ss!=session){
                    ss.getBasicRemote().sendText(json);
                }
            }
        }

    }

    @OnMessage
    public void onMessage(String message, Session session) {
        try {
           sendMessage(message,session);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("error...");
        error.printStackTrace();
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
      removeUserFromAllRooms(session);
        System.out.println("close");
    }


    public void sendMessage(String message,Session Isession) throws IOException {
        if (message.equals("ii")) {
            Isession.getBasicRemote().sendText(message);
        }

        Set<Session> roomSessions = getUserInRoom(roomid);
        for (Session session : roomSessions) {
            if(session!=Isession) {
                if (session.isOpen()) {
                    System.out.println("send: " + session.getId() + " ,message: " + message);
                    session.getBasicRemote().sendText(message);
                } else {
                    System.err.println(session.getId() + ": not open");
                    session.close();
                    roomSessions.remove(session);
                }
            }

        }
    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        RtcController.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        RtcController.onlineCount--;
    }

    private void addusertoRoom(String room,Session session){
       Set<Session> users= webSocketMap.getOrDefault(room,new HashSet<>());
       users.add(session);
       webSocketMap.put(room,users);

}
    private Set<Session> getUserInRoom(String room){
       return webSocketMap.getOrDefault(room,new HashSet<>());
    }
    private void removeUserFromAllRooms(Session session) {
        for (Set<Session> users : webSocketMap.values()) {
            users.remove(session);
        }
    }
}
