package sql.dao;

import data.Message;

import java.util.List;

public interface MessageDAO {
    void create();
    void update();
    void delete();
    
    Message getById(int id);
    List<Message> getAll();

}
