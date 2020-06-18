package sql.dao.impl;

import data.Message;
import org.apache.ibatis.session.SqlSession;
import sql.SessionFactory;

import java.util.List;

public class MessageDAO implements sql.dao.MessageDAO {

    final static String namespace = "message_mapper";

    @Override
    public void create(Message message) {
        SqlSession sqlSession = SessionFactory.getSession();
        sqlSession.insert(namespace + ".create", message);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void update(Message message) {

    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public Message getById(String id) {
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

    @Override
    public List<Message> getHistory() {
        SqlSession sqlSession = SessionFactory.getSession();
        List<Message> history = sqlSession.selectList(namespace + ".getHistory");
        sqlSession.close();
        return history;
    }
}
