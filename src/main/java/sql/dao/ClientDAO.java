package sql.dao;

import data.ClientInfo;

import java.util.List;

public interface ClientDAO {
    void create(ClientInfo clientInfo);
    void update();
    void delete();

    ClientInfo getById(String id);
    List<ClientInfo> getAll();
}
