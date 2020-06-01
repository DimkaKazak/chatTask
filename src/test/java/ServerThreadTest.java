import base.client.ClientThread;
import base.server.ServerTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.PrintWriter;


public class ServerThreadTest extends ServerTest {

    @BeforeClass
    public void initClientsOnServer(){
        int j = 0;

        for (ClientThread clientThread : clientThreadList){

            PrintWriter out;
            int counter = 0;
            do {
                out = clientThread.getClient().getOut();
                counter++;
            } while (out == null || counter != 1000);

            out.println("RandomName" + j);
            j++;
        }
    }

    @Test(threadPoolSize = POOL_SIZE, invocationCount = INVOCATION_COUNT, invocationTimeOut = INVOCATION_TIME_OUT)
    public void testConnections() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            assertFalse(serverThread.getServer().getConnections().isEmpty());
            assertEquals(serverThread.getServer().getConnections().size(), POOL_SIZE);
        }
    }

}
