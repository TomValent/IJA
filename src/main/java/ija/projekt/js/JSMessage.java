package ija.projekt.js;


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

    /**
     * Constructor for message.
     * @param name description of message.
     * @param type type of message.
     * @param sender sender of message.
     * @param receiver receiver of messaga.
     * @param transmition transmition of message.
     */
    public JSMessage(String name, String type, String sender, String receiver, String transmition){
        this.name = name;
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
        this.transmition = transmition;
    }

    /**
     * Getter of description.
     * @return decription of message.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter of type.
     * @return type of message.
     */
    public String getType() {
        return type;
    }

    /**
     * Getter of sender.
     * @return sender of message.
     */
    public String getSender() {
        return sender;
    }

    /**
     * Getter of receiver.
     * @return receiver of message.
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Getter of transmition.
     * @return transmition of message.
     */
    public String getTransmition() {
        return transmition;
    }

    /**
     * Setter of description.
     * @param name new description.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter of type.
     * @param type new type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Setter of sender.
     * @param sender new sender.
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * Setter of receiver.
     * @param receiver new receiver.
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * Setter of transmition.
     * @param transmition new transmition.
     */
    public void setTransmition(String transmition) {
        this.transmition = transmition;
    }
}