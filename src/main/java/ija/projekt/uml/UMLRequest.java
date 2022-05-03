package ija.projekt.uml;

/**
 * @author Tomas Valent
 * Class represents request between 2 quaestors in sequence diagram.
 */
public class UMLRequest extends UMLClassifier {
    public UMLRequest(java.lang.String name) {
        super(name);
    }

    public java.lang.String getName() {
        return this.name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }
}
