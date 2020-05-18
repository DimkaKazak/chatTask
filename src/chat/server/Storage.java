package chat.server;

import java.util.*;

/**
 * Contains all users and info about them like messages that they have published on a server.
 */
public class Storage {

    private static Storage instance;

    private Map<String, ArrayList<String>> usersStorage = Collections.synchronizedMap(new HashMap<>());

    private Storage(){}

    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }

        return instance;
    }

    public Map<String, ArrayList<String>> getUsersStorage() {
        return usersStorage;
    }

    public void addUser(String user){
        usersStorage.put(user, new ArrayList<>());
    }

    public void addMessage(String user, String message){
        usersStorage.get(user).add(message);
    }
}
