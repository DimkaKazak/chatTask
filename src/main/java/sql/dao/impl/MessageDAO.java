package sql.dao.impl;

import data.Message;
import org.apache.ibatis.session.SqlSession;
import sql.SessionFactory;

import java.util.List;

public class MessageDAO implements sql.dao.MessageDAO {
    
    final static String namespace = "message_mapper";

    @Override
    public void create() {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }

    @Override
    public Message getById(int id) {
        SqlSession sqlSession = SessionFactory.getSession();
        Message message = sqlSession.selectOne(namespace + ".getById", id);
        sqlSession.close();
        return message;
    }

    @Override
    public List<Message> getAll() {
        SqlSession sqlSession = SessionFactory.getSession();
        List<Message> messages = sqlSession.selectList(namespace + ".getAll");
        sqlSession.close();
        return messages;
    }
}
