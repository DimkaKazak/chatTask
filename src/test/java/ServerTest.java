import base.server.ServerThread;
import chat.client.MultiThreadedSocketClient;
import chat.server.MultiThreadedSocketServer;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class ServerTest {

    protected final int POOL_SIZE = 30;
    protected final int INVOCATION_COUNT = 1;
    protected final int INVOCATION_TIME_OUT = 10000;

    protected ServerThread serverThread;
    protected ExecutorService clientPool;

    @BeforeClass
    public void setUpSpace(){

        serverThread = new ServerThread(new MultiThreadedSocketServer());
        serverThread.start();

        clientPool = Executors.newFixedThreadPool(POOL_SIZE);
        Runnable clientInitTask = () -> new MultiThreadedSocketClient().startClient();
        for (int i = 0; i < POOL_SIZE; i++){
            clientPool.execute(clientInitTask);
        }
    }

    @Test(threadPoolSize = POOL_SIZE, invocationCount = INVOCATION_COUNT, invocationTimeOut = INVOCATION_TIME_OUT)
    public void testConnections() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            assertFalse(serverThread.getMultiThreadedSocketServer().getConnections().isEmpty());
            assertEquals(serverThread.getMultiThreadedSocketServer().getConnections().size(), POOL_SIZE);
        }
    }

    @AfterClass
    public void setDownSpace(){
        clientPool.shutdown();
    }

}
