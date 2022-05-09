package ija.projekt.uml;

/**
 * @author Tomas Valent
 * Class represents attribute in diagram. Every attribute has its name and type.
 */
public class UMLAttribute extends Element {
    public final UMLClassifier classifier;

    /**
     * Contructor for attributes.
     * @param name name of attribute.
     * @param classifier type of attribute.
     */
    public UMLAttribute(String name, UMLClassifier classifier) {
        super(name);
        this.classifier = classifier;
    }

    /**
     * Getter for type od attirubte.
     * @return type.
     */
    public UMLClassifier getType() {
        return classifier;
    }

    /**
     * Print function of attribute.
     * @return string wiwth name and type of attribute.
     */
    @Override
    public java.lang.String toString() {
        return name + ":" + classifier.name;
    }
}