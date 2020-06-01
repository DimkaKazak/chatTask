package base.server;

import base.client.ClientThread;
import chat.client.Client;
import chat.server.Server;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.LinkedList;
import java.util.List;


public class ServerTest extends Assert {

    protected ServerThread serverThread;
    protected List<ClientThread> clientThreadList = new LinkedList();

    protected final int POOL_SIZE = 30;
    protected final int INVOCATION_COUNT = 1;
    protected final int INVOCATION_TIME_OUT = 10000;

    @BeforeClass
    public void setUpSpace(){
        serverThread = new ServerThread(new Server());

        for (int i = 0; i < POOL_SIZE; i++){
            clientThreadList.add(new ClientThread(new Client()));
        }

        serverThread.start();

        for (ClientThread clientThread : clientThreadList){
            clientThread.start();
        }
    }

    @AfterClass
    public void setDownSpace(){

        for (ClientThread clientThread : clientThreadList){
            if (!clientThread.isInterrupted()){
                clientThread.interrupt();
            }
            clientThread.setClient(null);
        }
        clientThreadList = null;

        if (!serverThread.isInterrupted()){
            serverThread.interrupt();
        }
        serverThread.setServer(null);
        serverThread = null;
    }
}
