package ija.projekt.js;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomas Valent
 * Class represents message in class diagram.
 * Equivalent for UMLRequest in class diagram.
 */
public class JSMessage {
    private String name;
    private String type;
    private String sender;
    private String receiver;
    private String transmition;

    public JSMessage(String name, String type, String sender, String receiver, String transmition){
        this.name = name;
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
        this.transmition = transmition;
    }
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getTransmition() {
        return transmition;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setTransmition(String transmition) {
        this.transmition = transmition;
    }
}