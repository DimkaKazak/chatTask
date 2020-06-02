import base.server.ServerTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class ServerThreadTest extends ServerTest {

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
