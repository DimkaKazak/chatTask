package sql;

import data.ClientInfo;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import sql.service.ClientService;

import java.util.List;
import java.util.UUID;

public class ClientTest {

    private static final Logger LOGGER = Logger.getLogger(ClientTest.class);

    @Test(dataProvider = "setUpSpace")
    public void testClientGetById(String id, String expectedName, String expectedPassword) {

        ClientService clientService = new ClientService();
        clientService.create(new ClientInfo(id, expectedName, expectedPassword));
        ClientInfo clientInfo = clientService.getClientById(id);

        Assert.assertEquals(clientInfo.getName(), expectedName);
        Assert.assertEquals(clientInfo.getPassword(), expectedPassword);

        clientService.deleteClient(clientInfo);

    }

    @Test
    public void testClientGetAll() {

        ClientService clientService = new ClientService();
        ClientInfo client = new ClientInfo(UUID.randomUUID().toString());
        clientService.create(client);
        List<ClientInfo> clientInfos = clientService.getAllClients();
        clientInfos.forEach(clientInfo -> {
            LOGGER.info(clientInfo.getName() + " : " + clientInfo.getPassword());
        });
        Assert.assertFalse(clientInfos.isEmpty());

        clientService.deleteClient(client);
    }

    @Test
    public void testClientCreate() {

        ClientInfo clientInfo = new ClientInfo(UUID.randomUUID().toString(), "TestName", "");
        ClientService clientService = new ClientService();
        clientService.create(clientInfo);
        ClientInfo clientInfoFromDB = clientService.getClientById(clientInfo.getId());

        Assert.assertEquals(clientInfo.getName(), clientInfoFromDB.getName());
        Assert.assertEquals(clientInfo.getPassword(), clientInfoFromDB.getPassword());

        clientService.deleteClient(clientInfo);

    }

    @Test
    public void testClientUpdate() {

        ClientInfo prevClientInfo = new ClientInfo(UUID.randomUUID().toString(), "TestName", "");
        ClientService clientService = new ClientService();
        clientService.create(prevClientInfo);

        ClientInfo newClientInfo = new ClientInfo(prevClientInfo.getId(), "newTestName", "");
        clientService.updateClientInfo(newClientInfo);

        ClientInfo newClientInfoFromDB = clientService.getClientById(newClientInfo.getId());

        Assert.assertEquals(newClientInfo.getName(), newClientInfoFromDB.getName());
        Assert.assertEquals(newClientInfo.getPassword(), newClientInfoFromDB.getPassword());

        clientService.deleteClient(newClientInfo);
    }

    @Test
    public void testClientDelete() {

        ClientInfo clientInfo = new ClientInfo(UUID.randomUUID().toString(), "TestName", "");
        ClientService clientService = new ClientService();
        clientService.create(clientInfo);
        clientService.deleteClient(clientInfo);

        Assert.assertNull(clientService.getClientById(clientInfo.getId()));

    }

    @DataProvider
    public Object[][] setUpSpace(){

        Object[][] objects = new Object[10][3];
        for (int i = 0; i < 10; i++){
            objects[i][0] = (i + 1) + "";
            objects[i][1] = "testName" + (i + 1);
            objects[i][2] = "testPassword" + (i + 1);
        }
        return objects;
    }
}
