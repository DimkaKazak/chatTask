package sql.dao;

import data.ClientInfo;

import java.util.List;

public interface ClientDAO {
    void create(ClientInfo clientInfo);
    void update(ClientInfo clientInfo);
    void deleteClient(ClientInfo clientInfo);

    ClientInfo getById(String id);
    List<ClientInfo> getAll();
}
