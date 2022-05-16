/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author mac
 */
public class MessageDecoder implements Decoder.Text<Message>{

    @Override
    public Message decode(final String textMessage) throws DecodeException {
        Message message = new Message();
        JsonObject jsonObject = Json.createReader(new StringReader(textMessage)).readObject();
        message.setContent(jsonObject.getString("message"));
        message.setSender(jsonObject.getString("sender"));
        message.setReceived(jsonObject.getString("received"));
        message.setTimer(new Date());
        return message;
    }

    @Override
    public boolean willDecode(String arg0) {
        return true;
    }

    @Override
    public void init(final EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }
    
    
}
