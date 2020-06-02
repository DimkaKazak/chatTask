import org.testng.annotations.Test;
import provider.ServerClientProvider;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class ServerTest extends ServerClientProvider {

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
}
