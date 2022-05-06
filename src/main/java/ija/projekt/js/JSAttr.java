package ija.projekt.js;

/**
 * @author Tomas Valent
 * Class for storing class attributes.
 */
public class JSAttr {
    String attr;
    String type;

    /**
     * Constructor for class attribute.
     * @param attr name of attribute.
     * @param type data type od attribute.
     */
    public JSAttr(String attr, String type) {
        this.attr = attr;
        this.type = type;
    }

    /**
     * Getter for name of attribute.
     * @return attribute name.
     */
    public String getAttr() {
        return attr;
    }

    /**
     * Getter for attribute type.
     * @return attribute type.
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for attribute name.
     * @param attr new name.
     */
    public void setAttr(String attr) {
        this.attr = attr;
    }

    /**
     * Setter for attribute type.
     * @param type new type.
     */
    public void setType(String type) {
        this.type = type;
    }
}
