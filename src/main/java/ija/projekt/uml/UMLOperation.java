package ija.projekt.uml;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomas Valent
 * Class represents operation.
 */
public class UMLOperation extends UMLAttribute{
    private final List<UMLAttribute> arguments = new ArrayList<>();

    public UMLOperation(java.lang.String name, UMLClassifier classifier) {
        super(name,classifier);
    }

    public static UMLOperation create(java.lang.String name, UMLClassifier classifier, UMLAttribute... args) {
        UMLOperation oper = new UMLOperation(name, classifier);
        for (UMLAttribute attr : args
        ) {
            oper.addArgument(attr);
        }
        return oper;
    }

    public UMLClassifier getType() {
        return classifier;
    }

    public boolean addArgument(UMLAttribute arg) {
        arguments.add(arg);
        return true;
    }

    public List<UMLAttribute> getArguments() {
        return arguments;
    }

    public void removeOperation(UMLAttribute arg) {
        arguments.remove(arg);
    }
}