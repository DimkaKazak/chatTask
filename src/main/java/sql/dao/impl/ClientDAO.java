package sql.dao.impl;

import data.ClientInfo;
import org.apache.ibatis.session.SqlSession;
import sql.SessionFactory;

import java.util.List;

public class ClientDAO implements sql.dao.ClientDAO {

    final static String namespace = "client_mapper";

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
    public ClientInfo getById(int id) {
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
