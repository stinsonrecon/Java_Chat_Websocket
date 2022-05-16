/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;
import lombok.Data;

/**
 *
 * @author mac
 */
@Data
public class Message {
    private int type;
    private String content;
    private String sender;
    private String received;
    private String ID;
    private Date timer;
    private String authoToken;
    private String idOnServer;
    
    public String getIdOnServer(){
        return idOnServer;
    }
    
    public void setIdOnServer(String idOnServer){
        this.idOnServer = idOnServer;
    }
    
    public String getAuthoToken(){
        return authoToken;
    }
    
    public void setAuthoToken(String authoToken){
        this.authoToken = authoToken;
    }
    
    public Date getTimer(){
        return timer;
    }
    
    public void setTimer(Date timer){
        this.timer = timer;
    }
    
    public String getID(){
        return ID;
    }
    
    public int getType(){
        return type;
    }
    
    public void setType(int type){
        this.type = type;
    }
    
    public void setID(String ID){
        this.ID = ID;
    }
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }
    
    
    
}
