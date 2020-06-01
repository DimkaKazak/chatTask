package base.client;

import chat.client.Client;

public class ClientThread extends Thread{

    private Client client;

    public ClientThread(Client client){
        this.client = client;
    }

    @Override
    public void run() {
        client.startClient();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
