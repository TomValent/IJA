package ija.projekt.js;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomas Valent
 * Class represents message in class diagram.
 * Equivalent for UMLRequest in class diagram.
 */
public class JSMessage {
    private final String name;
    private final String type;
    private final String sender;
    private final String receiver;
    private final boolean transmition;

    public JSMessage(String name, String type, String sender, String receiver, boolean transmition){
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

    public boolean isTransmition() {
        return transmition;
    }
}