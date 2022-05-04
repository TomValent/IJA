package ija.projekt.uml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Tomas Valent
 * Class represents class in class diagram.
 */
public class UMLClass extends UMLClassifier {
    private final List<UMLAttribute> attributes = new ArrayList<>();
    private boolean abs;

    /**
     * Constructor of class.
     * @param name name of the class.
     */
    public UMLClass(String name) {
        super(name);
        abs = false;
    }

    /**
     * Getter for abstract flag.
     * @return abstract flag.
     */
    public boolean isAbstract() {
        return abs;
    }

    /**
     * Setter for abstract flag.
     * @param abs new abstract.
     */
    public void setAbstract(boolean abs) {
        this.abs = abs;
    }

    /**
     * Add new attribute to list.
     * @param attribute new attribute.
     */
    public void addAttribute(UMLAttribute attribute) {
        this.attributes.add(attribute);
    }

    /**
     * Getter for list of attributes.
     * @return list of attributes.
     */
    public List<UMLAttribute> getAttributes() {
        return Collections.unmodifiableList(attributes);
    }

    /**
     * Getter for posiition of attribute
     * @param attribute searched attribute.
     * @return position of attribute.
     */
    public int getAttrPosition(UMLAttribute attribute) {
        return attributes.indexOf(attribute);
    }

    /**
     * Move attribute position.
     * @param attr4 new attribute.
     * @param i position of new attribute.
     * @return 0 if successfull, -1 if not.
     */
    public int moveAttrAtPosition(UMLAttribute attr4, int i) {
        if (attributes.contains(attr4)) {
            attributes.remove(attr4);
            attributes.add(i, attr4);
            return 0;
        }
        return -1;
    }

    /**
     * Remove attribute of class.
     * @param attr attribute that will be removed.
     */
    public void removeAttr(UMLAttribute attr) {
        attributes.remove(attr);
    }
}