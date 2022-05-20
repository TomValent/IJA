package ija.projekt.js;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomas Valent
 * Class for storing json.
 */
public class InOut{
    private String nameSeq;
    private String nameClass;
    private List<JSClass> classes = new ArrayList<>();
    private List<JSMessage> messages = new ArrayList<>();
    private List<JSAssociation> association = new ArrayList<>();
    private List<JSAggrComp> agg_com = new ArrayList<>();
    private List<JSInheritance> gen_spe = new ArrayList<>();
    private List<JSSequence> seq_dia = new ArrayList<>();

    /**
     * Konstructor of InOut class.
     * @param nameSeq title of sequence diagram.
     * @param nameClass title of class diagram.
     * @param classes list of classes.
     * @param messages list of messages.
     */
    public InOut(String nameSeq, String nameClass, List<JSClass> classes, List<JSMessage>  messages, List<JSAssociation> associations, List<JSAggrComp> aggr_comp, List<JSInheritance> inherit, List<JSSequence> seq_dia){
        this.nameSeq = nameSeq;
        this.nameClass = nameClass;
        this.classes = classes;
        this.messages = messages;
        this.association = associations;
        this.agg_com = aggr_comp;
        this.gen_spe = inherit;
        this.seq_dia = seq_dia;
    }

    /**
     * Getter of the title.
     * @return title of diagrams.
     */
    public String getNameSeq() {
        return nameSeq;
    }

    /**
     * Setter of the title.
     * @param name new title.
     */
    public void setNameSeq(String name) {
        this.nameSeq = name;
    }

    /**
     * Getter of the title.
     * @return title of diagrams.
     */
    public String getNameClass() {
        return nameClass;
    }

    /**
     * Setter of the title.
     * @param name new title.
     */
    public void setNameClass(String name) {
        this.nameClass = name;
    }

    /**
     * Getter of list of classes.
     * @return list of classes.
     */
    public List<JSClass> getClasses() {
        return classes;
    }

    /**
     * Adding new class to list of classes.
     * @param newClass new class.
     */
    public void addClass(JSClass newClass) {
        this.classes.add(newClass);
    }

    /**
     * Remove class from list.
     * @param oldClass class that will be removed.
     */
    public void rmClass(JSClass oldClass) {
        this.classes.remove(oldClass);
    }

    /**
     * Getter of messages.
     * @return all messages.
     */
    public List<JSMessage> getMessages() {
        return messages;
    }

    /**
     * Add new message.
     * @param message new message.
     */
    public void addMsg(JSMessage message) {
        this.messages.add(message);
    }

    /**
     * Remove message from list.
     * @param message message that will be removed.
     */
    public void rmMsg(JSMessage message) {
        this.messages.remove(message);
    }

    /**
     * Getter of associations.
     * @return all associations.
     */
    public List<JSAssociation> getAssociations() {
        return association;
    }

    /**
     * Add new association.
     * @param association new association.
     */
    public void addAssociation(JSAssociation association) {
        this.association.add(association);
    }

    /**
     * Remove association from list.
     * @param association association that will be removed.
     */
    public void rmAssociation(JSAssociation association) {
        this.association.remove(association);
    }

    /**
     * Getter for aggregations and compositions.
     * @return list of aggregations and compositions.
     */
    public List<JSAggrComp> getAggr_comp() {
        return agg_com;
    }

    /**
     * Add new aggregation or composition.
     * @param aggrComp new aggregation or composition.
     */
    public void addAggrComp(JSAggrComp aggrComp) {
        this.agg_com.add(aggrComp);
    }

    /**
     * Remove aggregation or composition from list.
     * @param aggrComp aggregation or composition that will be removed.
     */
    public void rmAggrComp(JSAggrComp aggrComp) {
        this.agg_com.remove(aggrComp);
    }

    /**
     * Getter of inheritances.
     * @return all inheritances.
     */
    public List<JSInheritance> getInherit() {
        return gen_spe;
    }

    /**
     * Add new inheritance.
     * @param inherit new inheritance.
     */
    public void addInherit(JSInheritance inherit) {
        this.gen_spe.add(inherit);
    }

    /**
     * Remove inheritance from list.
     * @param inherit inheritance that will be removed.
     */
    public void rmInherit(JSInheritance inherit) {
        this.gen_spe.remove(inherit);
    }

    /**
     * Getter of sequence diagrams.
     * @return all sequence diagrams.
     */
    public List<JSSequence> getSeq() {
        return seq_dia;
    }

    /**
     * Add new sequence diagram.
     * @param seq new sequence diagram.
     */
    public void addSeq(JSSequence seq) {
        this.seq_dia.add(seq);
    }

    /**
     * Remove sequence diagram from list.
     * @param seq sequence diagram that will be removed.
     */
    public void rmSeq(JSSequence seq) {
        this.seq_dia.remove(seq);
    }

    public void removeEverything(){
        this.nameSeq = nameSeq.replace(nameSeq, "");
        this.classes.clear();
        this.messages.clear();
        this.association.clear();
        this.agg_com.clear();
        this.gen_spe.clear();
        this.seq_dia.clear();
    }
    public void removeClassDiagram(){
        this.classes.clear();
        this.association.clear();
        this.agg_com.clear();
        this.gen_spe.clear();
    }
}