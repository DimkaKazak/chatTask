package sql.service;

import data.ClientInfo;
import sql.dao.impl.ClientDAO;

import java.util.List;

public class ClientService {

    private ClientDAO clientDAO = new ClientDAO();

    public void create(ClientInfo clientInfo){
        clientDAO.create(clientInfo);
    }

    public void updateClientInfo(ClientInfo clientInfo){
        clientDAO.update(clientInfo);
    }

    public void deleteClientById(String id){
        clientDAO.deleteById(id);
    }

    public ClientInfo getClientById(String id){
        return clientDAO.getById(id);
    }

    public List<ClientInfo> getAllClients(){
        return clientDAO.getAll();
    }

}
