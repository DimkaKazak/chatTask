package sql.dao;

import data.ClientInfo;

import java.util.List;

public interface ClientDAO {
    void create();
    void update();
    void delete();

    ClientInfo getById(int id);
    List<ClientInfo> getAll();
}
