/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Model.ListMessage;
import MongodbHelper.DataHelper;
import Model.Message;
import static java.lang.String.format;

import Model.MessageDecoder;
import Model.MessageEncoder;
import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import org.json.JSONObject;

/**
 *
 * @author mac
 */
@javax.websocket.ClientEndpoint(encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class ClientEndpoint {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

    @OnOpen
    public void onOpen(Session session) {
        System.out.println(format("Connection established. session id: " + session.getId()));
    }

    @OnMessage
    public void onMessage(Message message, Session session) {
        Gson gson = new Gson();
        Message message1 = gson.fromJson(message.getContent(), Message.class);
//        System.out.println("Day la content cua message nhan dc: " + message1);
//        System.out.println("Day la messRaw: " + message);
//        System.out.println("Check id: " + session.getId());
        //System.out.println(message1.getSender()+ ": " + message1.getContent() + " " + message1.getTimer());
        System.out.println(format("[%s:%s] %s", simpleDateFormat.format(message1.getTimer()), message1.getSender(), message1.getContent()));
        
        ListMessage listMessage = gson.fromJson(message.getContent(), ListMessage.class);
        listMessage.setSender(DataHelper.getIdByFields("username", listMessage.getSender()));
        listMessage.setReceived(DataHelper.getIdByFields("username", listMessage.getReceived()));
        
        JSONObject messageObj = new JSONObject(listMessage);
        messageObj.remove("type");
        messageObj.remove("ID");
        messageObj.remove("idOnServer");
        //System.out.println("Day la messObj: " + messageObj);
        //TransferDataHelper.putDataTo_PosteeDB(messageObj, session.getId());
        if(!messageObj.get("content").toString().trim().equals("Can't chat by yourself! Type \"quit\" to logout!")){
            DataHelper.putDataTo_PosteeDB(messageObj, "LM"+messageObj.getString("received"));
        }
    }
}
