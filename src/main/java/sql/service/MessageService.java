package sql.service;

import data.Message;
import sql.dao.impl.MessageDAO;

import java.util.List;

public class MessageService {

    private MessageDAO messageDAO = new MessageDAO();

    public void createMessage(Message message){
        messageDAO.create(message);
    }

    public Message getMessageById(String id){
        return messageDAO.getById(id);
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAll();
    }

    public List<Message> getMessageHistory(){
        return messageDAO.getHistory();
    }

    public void deleteMessageById(String id){
        messageDAO.deleteById(id);
    }

    public void updateMessage(Message message){
        messageDAO.update(message);
    }
}
