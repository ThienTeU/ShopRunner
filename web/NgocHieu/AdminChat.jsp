<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Chat</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script>
        let adminWs;
        let userWs;
        let currentUser;
        let chatStorageKey = "chat_history_";

        function connectAdminWebSocket() {
            adminWs = new WebSocket("ws://" + window.location.host + "/RunnerShop/chat/admin");

            adminWs.onmessage = function (event) {
                let message = event.data;

                if (message.startsWith("UPDATE_CUSTOMERS:")) {
                    let customers = message.replace("UPDATE_CUSTOMERS:", "").split(",");
                    updateCustomerList(customers);
                } else {
                    displayAdminMessage(message);
                }
            };
        }

        function updateCustomerList(customers) {
            let customerList = document.getElementById("customer-list");
            customerList.innerHTML = "";

            customers.forEach(email => {
                if (email.trim() !== "") {
                    let li = document.createElement("li");
                    let button = document.createElement("button");
                    button.className = "btn btn-outline-primary w-100 my-1";
                    button.textContent = email;
                    button.onclick = function() { connectToUser(email); };
                    li.appendChild(button);
                    customerList.appendChild(li);
                }
            });
        }

        function connectToUser(email) {
            if (userWs) {
                userWs.close();  // Đóng WebSocket cũ trước khi mở mới
            }
            currentUser = email;
            userWs = new WebSocket("ws://" + window.location.host + "/RunnerShop/chat/" + email);

            adminWs.send("SELECT_USER:" + email);

            userWs.onmessage = function (event) {
                displayAdminMessage(event.data);
                saveAdminMessage(email, event.data);
            };

            loadAdminChatHistory(email);
        }

        function displayAdminMessage(message) {
            let chatMessages = document.getElementById("admin-chat-messages");
            let messageElement = document.createElement("div");
            messageElement.className = "alert alert-secondary";
            messageElement.textContent = message;
            chatMessages.appendChild(messageElement);
            chatMessages.scrollTop = chatMessages.scrollHeight;
        }

        function sendAdminMessage() {
            let messageInput = document.getElementById("admin-message");
            let message = messageInput.value.trim();

            if (message !== "" && adminWs && currentUser) {
                adminWs.send(message);  // Gửi tin nhắn qua WebSocket
                saveAdminMessage(currentUser, "Admin: " + message);
                messageInput.value = "";
            }
        }

        function saveAdminMessage(email, message) {
            let key = chatStorageKey + email;
            let chatHistory = JSON.parse(localStorage.getItem(key)) || [];
            chatHistory.push(message);
            localStorage.setItem(key, JSON.stringify(chatHistory));
        }

        function loadAdminChatHistory(email) {
            let key = chatStorageKey + email;
            let chatMessages = document.getElementById("admin-chat-messages");
            chatMessages.innerHTML = "";
            let chatHistory = JSON.parse(localStorage.getItem(key)) || [];
            chatHistory.forEach(message => {
                displayAdminMessage(message);
            });
        }

        function clearAdminChatHistory() {
            if (!currentUser) return;
            let chatHistoryKey = chatStorageKey + currentUser;
            localStorage.removeItem(chatHistoryKey);
            document.getElementById("admin-chat-messages").innerHTML = "";
        }

        window.onload = function() {
            connectAdminWebSocket();
        };
    </script>
</head>
<body class="container mt-4">
    <h2 class="text-center text-primary">Admin Chat</h2>

    <div class="row">
        <!-- Danh sách khách hàng -->
        <div class="col-md-4">
            <h4>Danh sách khách hàng</h4>
            <ul id="customer-list" class="list-group"></ul>
        </div>

        <!-- Phần chat -->
        <div class="col-md-8">
            <h4>Cuộc trò chuyện</h4>
            <div id="admin-chat-messages" class="border rounded p-3 mb-3" style="height: 300px; overflow-y: auto; background: #f8f9fa;"></div>
            <div class="input-group">
                <input type="text" id="admin-message" class="form-control" placeholder="Nhập tin nhắn..." />
                <button class="btn btn-primary" onclick="sendAdminMessage()">Gửi</button>
                <button class="btn btn-danger" onclick="clearAdminChatHistory()">Xóa lịch sử</button>
            </div>
        </div>
    </div>
</body>
</html>
