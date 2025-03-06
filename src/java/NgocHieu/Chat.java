package NgocHieu;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/chat/{email}")
public class Chat {
    private static final ConcurrentHashMap<String, CopyOnWriteArraySet<Session>> userSessions = new ConcurrentHashMap<>();
    private static final CopyOnWriteArraySet<Session> adminSessions = new CopyOnWriteArraySet<>();
    private static final ConcurrentHashMap<Session, String> adminCurrentUser = new ConcurrentHashMap<>();
    private String email;

    @OnOpen
    public void onOpen(Session session, @PathParam("email") String email) throws IOException {
        this.email = email;

        if (email.equals("admin")) {
            adminSessions.add(session);
            sendCustomerListToAdmin(session);
        } else {
            userSessions.putIfAbsent(email, new CopyOnWriteArraySet<>());
            userSessions.get(email).add(session);
            notifyAdmins();  // Cập nhật danh sách khách hàng ngay lập tức
        }
    }

    @OnMessage
    public void onMessage(String message, Session senderSession) throws IOException {
        boolean isAdmin = adminSessions.contains(senderSession);

        if (isAdmin) {
            // Khi Admin chọn User để chat
            if (message.startsWith("SELECT_USER:")) {
                String selectedUser = message.replace("SELECT_USER:", "").trim();
                adminCurrentUser.put(senderSession, selectedUser);
                return;
            }

            // Gửi tin nhắn từ Admin đến User đang chọn
            String currentUser = adminCurrentUser.get(senderSession);
            if (currentUser != null && userSessions.containsKey(currentUser)) {
                for (Session s : userSessions.get(currentUser)) {
                    if (s.isOpen()) {
                        s.getBasicRemote().sendText("Admin: " + message);
                    }
                }
            }
        } else {
            // Gửi tin nhắn từ User đến Admin
            for (Session s : adminSessions) {
                if (s.isOpen()) {
                    s.getBasicRemote().sendText(email + ": " + message);
                }
            }
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        if (email.equals("admin")) {
            adminSessions.remove(session);
            adminCurrentUser.remove(session);
        } else {
            userSessions.get(email).remove(session);
            if (userSessions.get(email).isEmpty()) {
                userSessions.remove(email);
            }
            notifyAdmins();  // Cập nhật danh sách khách hàng khi User rời đi
        }
    }

    private void notifyAdmins() throws IOException {
        String customerList = String.join(",", userSessions.keySet());
        for (Session s : adminSessions) {
            if (s.isOpen()) {
                s.getBasicRemote().sendText("UPDATE_CUSTOMERS:" + customerList);
            }
        }
    }

    private void sendCustomerListToAdmin(Session adminSession) throws IOException {
        notifyAdmins();
    }
}
