package ija.projekt.js;

import java.util.ArrayList;
import java.util.List;

public class JSSequence {
    private String name;
    private List<JSClass> classes = new ArrayList<>();     //pre konzistenciu necham rovnake nazvy aj tu
    private List<JSMessage> messages = new ArrayList<>();

    /**
     * Construcor
     * @param name name of diagram.
     * @param classses list of quaestors in diagram.
     * @param messages list od messages in diagram.
     */
    public JSSequence(String name, List<JSClass> classses, List<JSMessage> messages) {
        this.name = name;
        this.classes = classses;
        this.messages = messages;
    }

    /**
     * Constructor without lists.
     * @param name name of diagram.
     */
    public JSSequence(String name) {
        this.name = name;
    }

    /**
     * Getter of diagram name.
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter of diagram name.
     * @param name new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter of quaestors.
     * @return all quaestors.
     */
    public List<JSClass> getClasses() {
        return classes;
    }

    /**
     * Add new quaestor.
     * @param quaestor new quaestor.
     */
    public void addQ(JSClass quaestor){
        this.classes.add(quaestor);
    }

    /**
     * Remove quaestor.
     * @param queaestor quaestor that will be removed.
     */
    public void rmQ(JSClass queaestor){
        this.classes.remove(queaestor);
    }

    /**
     * Getter of messages.
     * @return messages.
     */
    public List<JSMessage> getMessages() {
        return messages;
    }

    /**
     * Add new message.
     * @param msg message.
     */
    public void addMsg(JSMessage msg){
        this.messages.add(msg);
    }

    /**
     * Remove message.
     * @param msg message that will be removed.
     */
    public void rmMsg(JSMessage msg){
        this.messages.remove(msg);
    }
}
