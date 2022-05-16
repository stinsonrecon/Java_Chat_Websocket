/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import JsonUtil.JsonUtil;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author mac
 */
public class MessageEncoder implements Encoder.Text<Message>{

    @Override
    public String encode(final Message message) throws EncodeException {
        return JsonUtil.formatMessage(message.getContent(), message.getSender());
    }

    @Override
    public void init(final EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }
    
}
