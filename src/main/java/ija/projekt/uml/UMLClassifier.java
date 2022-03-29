package ija.projekt.uml;

public class UMLClassifier extends Element {
    private boolean userDefined = true;

    public UMLClassifier(java.lang.String name) {
        super(name);
    }

    public UMLClassifier(boolean userDefined, java.lang.String name) {
        super(name);
        this.userDefined = userDefined;
    }

    public static UMLClassifier forName(java.lang.String name) {
        return new UMLClassifier(false, name);
    }

    public boolean isUserDefined() {
        return userDefined;
    }

    @Override
    public java.lang.String toString() {
        return name + "(" + userDefined + ")";
    }
}