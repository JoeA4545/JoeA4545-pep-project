package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.*;

public class MessageService {
    private final MessageDAO messageDAO;

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public MessageService() {
        this.messageDAO = new MessageDAO();
    }


    public Message createMessage(Message message) {
        if (message.getMessage_text() == null || message.getMessage_text().isBlank() || message.getMessage_text().length() > 255)
            return null;
        
        if (message.getPosted_by() <= 0)
            return null;

        return messageDAO.createMessage(message);
    }

    public Message deleteMessageById(int messageId) {
        if (messageId <= 0)
            return null;
        
        return messageDAO.deleteMessageById(messageId);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int messageId) {
        return messageDAO.getMessageById(messageId);
    }

    public Message updateMessageById(int messageId, String newText) {
        if (newText == null || newText.isBlank() || newText.length() > 255)
            return null;

        return messageDAO.updateMessageById(messageId, newText);
    }

    public List<Message> getMessagesByUser(int accountId) {
        return messageDAO.getMessagesByUser(accountId);
    }
    
}