package ija.projekt.uml;

/**
 * @author Tomas Valent
 * Class represents named elements in diagram.
 */
public class Element {
    String name;

    /**
     * Constructor of element.
     * @param name name of element.
     */
    public Element(java.lang.String name) {
        this.name = name;
    }

    /**
     * Getter for element name.
     * @return element name.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for element name.
     * @param name new name.
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }
}