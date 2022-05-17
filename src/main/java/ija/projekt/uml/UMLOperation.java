package ija.projekt.uml;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomas Valent
 * Class represents operation.
 */
public class UMLOperation extends UMLAttribute{
    private final List<UMLAttribute> arguments = new ArrayList<>();

    /**
     * Constructor of operation.
     * @param name name of operation.
     * @param classifier type of operation.
     */
    public UMLOperation(java.lang.String name, UMLClassifier classifier) {
        super(name,classifier,UMLAttributeModifier.PUBLIC);
    }

    /**
     * Create new operation.
     * @param name name of operation.
     * @param classifier type of operation.
     * @param args arguments of operation.
     * @return new operation.
     */
    public static UMLOperation create(java.lang.String name, UMLClassifier classifier, UMLAttribute... args) {
        UMLOperation oper = new UMLOperation(name, classifier);
        for (UMLAttribute attr : args
        ) {
            oper.addArgument(attr);
        }
        return oper;
    }

    /**
     * Getter of type.
     * @return type.
     */
    public UMLClassifier getType() {
        return classifier;
    }

    /**
     * Add new argument.
     * @param arg new argument.
     */
    public void addArgument(UMLAttribute arg) {
        arguments.add(arg);
    }

    /**
     * remove argument from list.
     * @param arg argument that will be removed.
     */
    public void rmArgument(UMLAttribute arg) {
        this.arguments.remove(arg);
    }

    /**
     * Getter for arguments.
     * @return list of arguments.
     */
    public List<UMLAttribute> getArguments() {
        return arguments;
    }

    /**
     * Remove operation
     * @param arg arguments to delete.
     */
    public void removeOperation(UMLAttribute arg) {
        arguments.remove(arg);
    }

    @Override
    public java.lang.String toString() {
        return name + ":" + classifier.name;
    }
}