<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Chat</title>
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
                    button.textContent = email;
                    button.onclick = function() { connectToUser(email); };
                    li.appendChild(button);
                    customerList.appendChild(li);
                }
            });
        }

        function connectToUser(email) {
            if (userWs) {
                userWs.close();
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
            let messageElement = document.createElement("p");
            messageElement.textContent = message;
            chatMessages.appendChild(messageElement);
            chatMessages.scrollTop = chatMessages.scrollHeight;
        }

        function sendAdminMessage() {
            let messageInput = document.getElementById("admin-message");
            let message = messageInput.value.trim();
            if (message !== "" && adminWs && currentUser) {
                let formattedMessage = "Admin: " + message;
                adminWs.send(message);
                saveAdminMessage(currentUser, formattedMessage);
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
<body>
    <h2>Admin Chat</h2>

    <h3>Danh sách khách hàng</h3>
    <ul id="customer-list"></ul>

    <h3>Cuộc trò chuyện với khách hàng</h3>
    <div id="admin-chat-messages" style="border:1px solid #ccc; width:400px; height:300px; overflow-y:scroll; padding:10px;"></div>

    <input type="text" id="admin-message" placeholder="Nhập tin nhắn..." />
    <button onclick="sendAdminMessage()">Gửi</button>
    <button onclick="clearAdminChatHistory()">Xóa lịch sử</button>
</body>
</html>
