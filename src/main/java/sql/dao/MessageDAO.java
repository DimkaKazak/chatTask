package sql.dao;

import data.Message;

import java.util.List;

public interface MessageDAO {
    void create(Message message);
    void update();
    void delete();
    
    Message getById(String id);
    List<Message> getAll();
    List<Message> getHistory();

}
