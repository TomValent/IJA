package ija.projekt.js;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomas Valent
 * Class for storing inheritance of classes.
 */
public class JSInheritance {
    private String parentClass;
    private String type;
    private List<JSChild> childClasses = new ArrayList<>();

    /**
     * Constructor of inheritance.
     * @param parentClass parent class.
     * @param type type -> generalisation/specialisation -> 0/1.
     * @param childClasses list of child classes.
     */
    public JSInheritance(String parentClass, String type, List<JSChild> childClasses) {
        this.parentClass = parentClass;
        this.type = type;
        this.childClasses = childClasses;
    }

    /**
     * Getter for parent class.
     * @return parent class.
     */
    public String getParentClass() {
        return parentClass;
    }

    /**
     * Setter for parent class.
     * @param parentClass new parent class.
     */
    public void setParentClass(String parentClass) {
        this.parentClass = parentClass;
    }

    /**
     * Getter for type.
     * @return type of inheritance
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for type.
     * @param type new type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter for list of child classes.
     * @return list of child classes.
     */
    public List<JSChild> getChildClasses() {
        return childClasses;
    }

    /**
     * Add new child class.
     * @param child new child class.
     */
    public void addChild(JSChild child) {
        this.childClasses.add(child);
    }

    /**
     * Remove child class.
     * @param child child that will be removed.
     */
    public void rmChild(JSChild child) {
        this.childClasses.remove(child);
    }
}
