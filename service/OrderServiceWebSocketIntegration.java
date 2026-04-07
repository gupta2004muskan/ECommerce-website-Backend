package service;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;

public class OrderServiceWebSocketIntegration {

    // Method to send a notification about a new order
    public void notifyNewOrder(WebSocketSession session, String orderId) {
        String message = "New order created with ID: " + orderId;
        sendMessage(session, message);
    }

    // Method to send a notification about order status update
    public void notifyOrderStatusUpdate(WebSocketSession session, String orderId, String status) {
        String message = "Order ID: " + orderId + " has been updated to status: " + status;
        sendMessage(session, message);
    }

    // Private method to handle message sending
    private void sendMessage(WebSocketSession session, String message) {
        try {
            session.sendMessage(new TextMessage(message));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}