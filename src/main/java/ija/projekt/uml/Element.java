package ija.projekt.uml;

/**
 * @author Tomas Valent
 * Class represents named elements in diagram.
 */
public class Element {
    String name;

    public Element(java.lang.String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }
}