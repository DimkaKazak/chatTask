package base.server;

import chat.client.Client;
import chat.server.Server;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ServerTest {

    protected final int POOL_SIZE = 30;
    protected final int INVOCATION_COUNT = 1;
    protected final int INVOCATION_TIME_OUT = 10000;

    protected ServerThread serverThread;
    protected ExecutorService clientPool;

    @BeforeClass
    public void setUpSpace(){

        serverThread = new ServerThread(new Server());
        serverThread.start();

        clientPool = Executors.newFixedThreadPool(POOL_SIZE);
        Runnable clientInitTask = () -> new Client().startClient();
        for (int i = 0; i < POOL_SIZE; i++){
            clientPool.execute(clientInitTask);
        }
    }

    @AfterClass
    public void setDownSpace(){
        clientPool.shutdown();
    }
}
