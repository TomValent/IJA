package ija.projekt.uml;

import java.util.ArrayList;
import java.util.List;

public class ClassDiagram extends UMLClassifier {
    private final List<UMLClassifier> classifiers = new ArrayList<>();

    public ClassDiagram(java.lang.String name) {
        super(name);
    }

    public UMLClass createClass(java.lang.String name) {
        if (classifiers.stream().anyMatch(x -> x.getName().equals(name))) {
            return null;
        }
        var classInstance = new UMLClass(name);
        classifiers.add(classInstance);
        return classInstance;
    }

    public UMLClassifier findClassifier(java.lang.String name) {
        var found = classifiers.stream().filter(x -> x.getName().equals(name)).findFirst();
        return found.orElse(null);
    }

    public UMLClassifier classifierForName(java.lang.String name) {
        var found = findClassifier(name);
        if (found == null) {
            UMLClassifier newClassifier = new UMLClassifier(false, name);
            classifiers.add(newClassifier);
            return newClassifier;
        }
        return found;
    }

    public void removeClass(UMLClass obj) {
        classifiers.remove(obj);
    }
}
