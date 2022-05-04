package ija.projekt.uml;

/**
 * @author Tomas Valent
 * Class represents request between 2 quaestors in sequence diagram.
 */
public class UMLRequest extends UMLClassifier {
    public UMLRequest(java.lang.String name) {
        super(name);
    }

    /**
     * Getter for name of request.
     * @return name of request.
     */
    public java.lang.String getName() {
        return this.name;
    }

    /**
     * Setter for name of request.
     * @param name new name.
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }
}
