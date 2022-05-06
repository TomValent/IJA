package ija.projekt.js;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomas Valent
 * Class for storing informations of a class.
 */
public class JSClass {
    private String abs;
    private String name;
    private final List<JSAttr> attributes = new ArrayList<>();

    /**
     * Constructor of class.
     * @param abs abstract flag.
     * @param name name of class.
     * @param attr attributes of class.
     */
    public JSClass(String abs, String name, List<JSAttr> attr) {
        this.abs = abs;
        this.name = name;
        this.attributes.addAll(attr);
    }

    /**
     * Getter for abstract flag.
     * @return abstract flag.
     */
    public String getAbs() {
        return abs;
    }

    /**
     * Getter for name of class.
     * @return name of class.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for list of attributes.
     * @return list of attrubutes.
     */
    public List<JSAttr> getAttr() {
        return attributes;
    }

    /**
     * Setter for abstract flag.
     * @param abs new abstract.
     */
    public void setAbs(String abs) {
        this.abs = abs;
    }

    /**
     * Setter for name of class.
     * @param name new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Add new attribute to list.
     * @param attr new attribute.
     */
    public void addAttr(JSAttr attr) {
        this.attributes.add(attr);
    }

    /**
     * Remove attribute from list.
     * @param attr attribute that will be removed.
     */
    public void rmAttr(JSAttr attr) {
        this.attributes.remove(attr);
    }
}
