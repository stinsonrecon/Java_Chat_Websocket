/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

/**
 *
 * @author mac
 */
import MongodbHelper.DataHelper;
import Functions.PostData;
import static JsonUtil.JsonUtil.formatMessage;
import Model.Message;
import MongodbHelper.MongoHelper;
import com.google.gson.Gson;

import java.net.URI;
import java.util.Date;
import java.util.Scanner;
import javax.websocket.Session;
import org.glassfish.tyrus.client.ClientManager;

public class Client {

    public static final String SERVER = "ws://localhost:8080/ws/chat";

    public static void main(String[] args) throws Exception {
        ClientManager client = ClientManager.createClient();

        // connect to server
        Scanner scanner = new Scanner(System.in);
        PostData postData = new PostData();
        MongoHelper mongoHelper = new MongoHelper();
        
        System.out.println("Enter username: ");
        String username = scanner.nextLine();
        System.out.println("Enter password: ");
        String password = scanner.nextLine();
        
        String autho = postData.Login(username, password);
        String idOnServer = postData.GetIDByAuthorization(autho);
        //String autho = postData.Login("admin","1234");
        System.out.println("Welcome to Tiny Chat!");
        //String user = postData.GetUsername(autho);
        //String username = scanner.nextLine();

        Session session = client.connectToServer(ClientEndpoint.class, new URI(SERVER));
        Message message1 = new Message();
        message1.setID(username);
        message1.setType(0);
        message1.setContent(session.getId());
        message1.setAuthoToken(autho);
        message1.setIdOnServer(idOnServer);
        
        //System.out.println(formatMessage("You are logged in as: " + username, username));
        System.out.println("You are logged in as: " + username);
        session.getBasicRemote().sendText(formatMessage((new Gson()).toJson(message1), username));
        Message sendMessage = new Message();

        // repeatedly read a message and send it to the server (until quit)
        boolean check = true;
        while (check) {
            System.out.print("Receiver name: ");
            String receiverName = scanner.nextLine();
            if (DataHelper.isInDB_Postee_UserData("username", receiverName)) {
                check = false;
                do {
                    sendMessage.setID(session.getId());
                    sendMessage.setSender(username);
                    sendMessage.setType(1);
                    sendMessage.setReceived(receiverName);
                    sendMessage.setTimer(new Date());
                    sendMessage.setAuthoToken(autho);
                    sendMessage.setIdOnServer(idOnServer);
                    sendMessage.setContent(scanner.nextLine());
                    session.getBasicRemote().sendText(formatMessage((new Gson().toJson(sendMessage)), username));
                } while (!sendMessage.getContent().equalsIgnoreCase("quit"));
                session.close();
            } else {
                System.out.println("Can't find user to chat");
            }
        }
    }

}
