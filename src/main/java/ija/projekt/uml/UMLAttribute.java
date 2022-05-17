package ija.projekt.uml;

/**
 * @author Tomas Valent
 * Class represents attribute in diagram. Every attribute has its name and type.
 */
public class UMLAttribute extends Element {
    public final UMLClassifier classifier;
    private UMLAttributeModifier modifier;
    /**
     * Contructor for attributes.
     * @param name name of attribute.
     * @param classifier type of attribute.
     */
    public UMLAttribute(String name, UMLClassifier classifier, UMLAttributeModifier modifier) {
        super(name);
        this.classifier = classifier;
        this.modifier = modifier;
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
        return modifier.getSymbol() + " " + name + ":" + classifier.name;
    }

    public UMLAttributeModifier getModifier() {
        return modifier;
    }
}