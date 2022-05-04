package ija.projekt.uml;

/**
 * @author Tomas Valent
 * Class for creeating classifiers.
 */
public class UMLClassifier extends Element {
    private boolean userDefined = true;

    /**
     * Constructor for classifier.
     * @param name name of classifier.
     */
    public UMLClassifier(java.lang.String name) {
        super(name);
    }

    /**
     * Constructor for classifier.
     * @param userDefined userDefined flag.
     * @param name name of classifier.
     */
    public UMLClassifier(boolean userDefined, java.lang.String name) {
        super(name);
        this.userDefined = userDefined;
    }

    /**
     * Create new classifier.
     * @param name name of classifier.
     * @return new classifier.
     */
    public static UMLClassifier forName(java.lang.String name) {
        return new UMLClassifier(false, name);
    }

    /**
     * Getter for userDefined flag.
     * @return userDefined flag.
     */
    public boolean isUserDefined() {
        return userDefined;
    }

    /**
     * Print function of classifier.
     * @return string with name and userDefined flag of classifier.
     */
    @Override
    public java.lang.String toString() {
        return name + "(" + userDefined + ")";
    }
}