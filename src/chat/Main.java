package chat;

import chat.client.Client;
import chat.server.Server;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	    Scanner in = new Scanner(System.in);
	    System.out.println("Запустить сервер или клиент?");
	    System.out.println("S - Server");
        System.out.println("C - Client");

        while (true) {
            char answer = Character.toLowerCase(in.nextLine().charAt(0));

            if (answer == 's'){
                new Server();
                break;
            } else if (answer == 'c') {
                new Client();
                break;
            } else {

                System.out.println("Некорректный ввод. Повторите.\n");
                System.out.println("S - Server");
                System.out.println("C - Client");
            }
        }
    }
}
