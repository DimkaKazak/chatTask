package base.server;

import chat.server.Server;

public class ServerThread extends Thread{

    private Server server;

    public ServerThread(Server server){
        this.server = server;
    }

    @Override
    public void run() {
        this.server.startServer();
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }
}
