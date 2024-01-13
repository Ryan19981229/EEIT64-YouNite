package tw.com.younite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import tw.com.younite.service.impl.UserServiceImpl;
import tw.com.younite.service.inter.IUserService;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.sql.*;
import java.util.*;

@Controller
@ServerEndpoint(value = "/websocket/{userID}/{room}")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
//@Component
public class SocketController {

    private static Set<Session> sessions;
    private static Map<String, Set<Session>> roomMap;
//    private CountDownLatch dataMessageLatch = new CountDownLatch(1);

    public SocketController() {

        if (sessions == null) {
            sessions = new HashSet<>();
            roomMap = new HashMap<>();
        }
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("room") String room) {
        System.out.println("onOpen()" + session.toString());
//        session.setMaxIdleTimeout(10 * 1000);
        session.setMaxIdleTimeout(0);
        if (sessions.add(session)) {
            addUserToRoom(room, session); // 加入指定的房間
        }

    }

    @OnClose
    public void onClose(Session session, @PathParam("userID") Integer id) {
//        System.out.println("onClose()");
        update(id);
        sessions.remove(session);
        removeUserFromAllRooms(session);
    }

    @OnError
    public void onError(Session session, Throwable t) {
        System.out.println("onError()" + t.toString());
        sessions.remove(session);
        removeUserFromAllRooms(session);
    }


    @OnMessage
    public void onMessage(String msg, Session session) {
        try {
            // 等待 onDataMessage 處理完畢
//            dataMessageLatch.await();
            System.out.println(msg);
            System.out.println("data:string");

            String room = getRoomForUser(session);
            if (room != null) {
                Set<Session> usersInRoom = getUsersInRoom(room);
                for (Session user : usersInRoom) {
                    try {
                        user.getBasicRemote().sendText(msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println();
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @OnMessage
    public void onDataMessage(InputStream inputStream, Session session) {
        String room = getRoomForUser(session);
        System.out.println("data:data");
        if (room != null) {
            Set<Session> usersInRoom = getUsersInRoom(room);
            byte[] buffer = new byte[8192];
            int bytesRead;
            try {
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    byte[] data = Arrays.copyOf(buffer, bytesRead);
                    ByteBuffer byteBuffer = ByteBuffer.wrap(data);
                    for (Session user : usersInRoom) {
                        try {
                            RemoteEndpoint.Basic remote = user.getBasicRemote();
                            remote.sendBinary(byteBuffer);

                        } catch (IOException e) {
//                            e.printStackTrace();
                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }





    private void addUserToRoom(String room, Session session) {
        Set<Session> users = roomMap.getOrDefault(room, new HashSet<>());
        users.add(session);
        roomMap.put(room, users);
    }

    private void removeUserFromRoom(String room, Session session) {
        Set<Session> users = roomMap.get(room);
        if (users != null) {
            users.remove(session);
            if (users.isEmpty()) {
                roomMap.remove(room);
            }
        }
    }

    private void removeUserFromAllRooms(Session session) {
        for (Set<Session> users : roomMap.values()) {
            users.remove(session);
        }
    }

    private Set<Session> getUsersInRoom(String room) {
        return roomMap.getOrDefault(room, new HashSet<>());
    }

    private String getRoomForUser(Session session) {
        for (Map.Entry<String, Set<Session>> entry : roomMap.entrySet()) {
            if (entry.getValue().contains(session)) {
                return entry.getKey();
            }
        }
        return null;
    }
    private void update(Integer id){
        String url = "jdbc:mysql://younite-database.c7ta6ybbk9nu.ap-northeast-1.rds.amazonaws.com:3306/younite";
        String username = "admin";
        String password = "younite666";

        // 建立資料庫連接
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            // 建立 PreparedStatement 物件
            String sql = "UPDATE users SET log_time = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // 設定參數值
            Timestamp newLogTime = new Timestamp(System.currentTimeMillis());
            pstmt.setTimestamp(1, newLogTime);
            pstmt.setInt(2, id);

            // 執行更新操作
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("updateok: " + rowsAffected);
            } else {
                System.out.println("not found");
            }

     pstmt.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }}
}
