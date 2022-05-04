package ija.projekt.js;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Tomas Valent
 * Class for storing json.
 */
public class InOut{
    private String name;
    private List<JSClass> classes = new ArrayList<>();
    private List<JSMessage> messages = new ArrayList<>();
    private List<JSAssociation> associations = new ArrayList<>();
    private List<JSAggrComp> aggr_comp = new ArrayList<>();
    private List<JSInheritance> inherit = new ArrayList<>();

    /**
     * Konstructor of InOut class.
     * @param name title of diagrams.
     * @param classes list of classes.
     * @param messages list of messages.
     */
    public void InOut(String name, List<JSClass> classes, List<JSMessage>  messages, List<JSAssociation> associations, List<JSAggrComp> aggr_comp, List<JSInheritance> inherit){
        this.name = name;
        this.classes = classes;
        this.messages = messages;
        this.associations = associations;
        this.aggr_comp = aggr_comp;
        this.inherit = inherit;
    }

    /**
     * Getter of the title.
     * @return title of diagrams.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter of the title.
     * @param name new title.
     */
    public void setName(String name) {
        this.name = name;
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
        return associations;
    }

    /**
     * Add new association.
     * @param association new association.
     */
    public void addAssociation(JSAssociation association) {
        this.associations.add(association);
    }

    /**
     * Remove association from list.
     * @param association association that will be removed.
     */
    public void rmAssociation(JSAssociation association) {
        this.associations.remove(association);
    }

    /**
     * Getter for aggregations and compositions.
     * @return list of aggregations and compositions.
     */
    public List<JSAggrComp> getAggr_comp() {
        return aggr_comp;
    }

    /**
     * Add new aggregation or composition.
     * @param aggrComp new aggregation or composition.
     */
    public void addAggrComp(JSAggrComp aggrComp) {
        this.aggr_comp.add(aggrComp);
    }

    /**
     * Remove aggregation or composition from list.
     * @param aggrComp aggregation or composition that will be removed.
     */
    public void rmAggrComp(JSAggrComp aggrComp) {
        this.aggr_comp.remove(aggrComp);
    }

    /**
     * Getter of inheritances.
     * @return all inheritances.
     */
    public List<JSInheritance> getInherit() {
        return inherit;
    }

    /**
     * Add new inheritance.
     * @param inherit new inheritance.
     */
    public void addInherit(JSInheritance inherit) {
        this.inherit.add(inherit);
    }

    /**
     * Remove inheritance from list.
     * @param inherit inheritance that will be removed.
     */
    public void rmInherit(JSInheritance inherit) {
        this.inherit.remove(inherit);
    }
}