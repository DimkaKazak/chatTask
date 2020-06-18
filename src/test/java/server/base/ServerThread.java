package server.base;

import chat.server.MultiThreadedSocketServer;

public class ServerThread extends Thread{

    private MultiThreadedSocketServer multiThreadedSocketServer;

    public ServerThread(MultiThreadedSocketServer multiThreadedSocketServer){
        this.multiThreadedSocketServer = multiThreadedSocketServer;
    }

    @Override
    public void run() {
        this.multiThreadedSocketServer.startServer();
    }

    public MultiThreadedSocketServer getMultiThreadedSocketServer() {
        return multiThreadedSocketServer;
    }

    public void setMultiThreadedSocketServer(MultiThreadedSocketServer multiThreadedSocketServer) {
        this.multiThreadedSocketServer = multiThreadedSocketServer;
    }
}
