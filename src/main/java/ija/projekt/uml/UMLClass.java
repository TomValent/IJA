package ija.projekt.uml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UMLClass extends UMLClassifier {
    private final List<UMLAttribute> attributes = new ArrayList<>();
    private boolean abs;

    public UMLClass(String name) {
        super(name);
        abs = false;
    }

    public boolean isAbstract() {
        return abs;
    }

    public void setAbstract(boolean abs) {
        this.abs = abs;
    }

    public void addAttribute(UMLAttribute attribute) {
        this.attributes.add(attribute);
    }

    public List<UMLAttribute> getAttributes() {
        return Collections.unmodifiableList(attributes);
    }

    public int getAttrPosition(UMLAttribute attribute) {
        return attributes.indexOf(attribute);
    }

    public int moveAttrAtPosition(UMLAttribute attr4, int i) {
        if (attributes.contains(attr4)) {
            attributes.remove(attr4);
            attributes.add(i, attr4);
            return 0;
        }
        return -1;
    }

    public void removeAttr(UMLAttribute attr) {
        attributes.remove(attr);
    }
}