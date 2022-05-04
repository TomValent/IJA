package ija.projekt.uml;

/**
 * @author Tomas Valent
 * Class represents attribute in diagram. Every attribute has its name and type.
 */
public class UMLAttribute extends Element {
    public final UMLClassifier classifier;

    public UMLAttribute(String name, UMLClassifier classifier) {
        super(name);
        this.classifier = classifier;
    }

    public UMLClassifier getType() {
        return classifier;
    }

    @Override
    public java.lang.String toString() {
        return name + ":" + classifier;
    }
}