/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

/**
 *
 * @author mac
 */
import Model.ListMessage;
import Model.User;
import MongodbHelper.DataHelper;
import static JsonUtil.JsonUtil.formatMessage;
import static java.lang.String.format;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import Model.Message;
import Model.MessageDecoder;
import Model.MessageEncoder;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import org.json.JSONObject;

@javax.websocket.server.ServerEndpoint(value = "/chat", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class ServerEndpoint {

    static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
    static HashMap<String, String> listUser = new HashMap();

    @OnOpen
    public void onOpen(Session session) throws IOException, EncodeException {
        System.out.println(format(session.getId() + " joined the chat room."));
        peers.add(session);
    }

    @OnMessage
    public void onMessage(Message message, Session session) throws IOException, EncodeException {
        Gson gson = new Gson();
        Message msg = gson.fromJson(message.getContent(), Message.class);
        System.out.println(message.getContent());

        if (msg.getType() == 0) {
            listUser.put(msg.getID(), session.getId());

            String token = msg.getAuthoToken();
            String idOnServer = msg.getIdOnServer();
            //String token = apiDemo.generateNewToken();
            User user = new User(session.getId(), idOnServer, token, msg.getID());
            if (!DataHelper.isInDB_Postee_UserData("idOnServer", idOnServer)) {
                DataHelper.putDataTo_PosteeDB_Register(user);
            }
//            TransferDataHelper.createCollections(msg.getContent()); // dung ra la no phai la ntn
            //TransferDataHelper.createCollections(user.getUsername()); // tao colletions moi bang sessionId thi khi dung lenh db.sessionID.find() loi cu phap do sessionID qua dai
            System.out.println("Add user " + session.getId());
        } else if (msg.getType() == 1) {
            if (listUser.containsKey(msg.getReceived())) {
                String idReceive = listUser.get(msg.getReceived()).toString();
                System.out.println("id nguoi nhan: " + idReceive);
                for (Session peer : peers) {
                    if (!session.getId().equals(idReceive)) {
                        if (peer.getId().equals(idReceive)) {
                            System.out.println("id nguoi gui " + session.getId());
                            System.out.println("Peers: " + peer.getId());

                            if (!msg.getContent().toLowerCase().trim().equals("quit")) {
                                peer.getBasicRemote().sendObject(message);
                                ListMessage listMessage = gson.fromJson(message.getContent(), ListMessage.class);
                                //listMessage.setSender(session.getId());
                                listMessage.setSender(listMessage.getIdOnServer());
                                for (Map.Entry<String, String> e : listUser.entrySet()) {
                                    Object key = e.getKey();
                                    Object value = e.getValue();
                                    if (value == idReceive) {
                                        //do something
                                        String receiverName = key.toString();
                                        String idNguoiNhan = DataHelper.getIdByFields("username", receiverName);
                                        listMessage.setReceived(idNguoiNhan);
                                        break;
                                    }
                                }
                                JSONObject messageObj = new JSONObject(listMessage);
                                messageObj.remove("type");
                                messageObj.remove("ID");
                                messageObj.remove("idOnServer");
//                            message.setID(idReceive);
                                DataHelper.putDataTo_PosteeDB(messageObj, "LM" + messageObj.getString("sender"));
                            }
                        }
                    } else {
                        msg.setContent("Can't chat by yourself! Type \"quit\" to logout!");
                        message.setContent(gson.toJson(msg));
                        peer.getBasicRemote().sendObject(message);
                        System.out.println("Can't chat by yourself!");
                        break;
                    }
                }
            }
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        System.out.println(session.getId() + " left the chat");
        DataHelper.remove_From_DB_Postee("sessionId", session.getId(), "userData");
        peers.remove(session);
//        for (Session peer : peers) {
//            Message chatMessage = new Message();
//            chatMessage.setSender("Server");
//            chatMessage.setContent(format("%s left the chat.", (String) session.getUserProperties().get("user")));
//            chatMessage.setReceived(peer.getId());
//            peer.getBasicRemote().sendObject(chatMessage);
//        }
    }
}
