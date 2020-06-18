package sql.dao.impl;

import data.ClientInfo;
import org.apache.ibatis.session.SqlSession;
import sql.SessionFactory;

import java.util.List;

public class ClientDAO implements sql.dao.ClientDAO {

    final static String namespace = "client_mapper";

    @Override
    public void create(ClientInfo clientInfo) {
        SqlSession sqlSession = SessionFactory.getSession();
        sqlSession.insert(namespace + ".create", clientInfo);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void update(ClientInfo clientInfo) {

    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public ClientInfo getById(String id) {
        SqlSession sqlSession = SessionFactory.getSession();
        ClientInfo clientInfo = sqlSession.selectOne(namespace + ".getById", id);
        sqlSession.close();
        return clientInfo;
    }

    @Override
    public List<ClientInfo> getAll() {
        SqlSession sqlSession = SessionFactory.getSession();
        List<ClientInfo> clientInfos = sqlSession.selectList(namespace + ".getAll");
        sqlSession.close();
        return clientInfos;
    }
}
