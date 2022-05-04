package ija.projekt.uml;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Magdalena Bellayova
 * Class for creeating class diagram.
 */
public class ClassDiagram extends Element {
    private final List<UMLClassifier> classifiers = new ArrayList<>();
    private final List<UMLClassRelationship> relationships = new ArrayList<>();

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

    public Association createAssociation(java.lang.String name) {
        if (relationships.stream().anyMatch(x -> x.getName().equals(name))) {
            return null;
        }
        var associationInstance = new Association(name);
        relationships.add(associationInstance);
        return associationInstance;
    }
    public AggregationComposition createAggrComp(java.lang.String name, UMLClass parent, UMLClass child, String child_cardinality, int type) {
        if (relationships.stream().anyMatch(x -> x.getName().equals(name))) {
            return null;
        }
        var aggrcomptionInstance = new AggregationComposition(name, parent, child, child_cardinality, type);
        relationships.add(aggrcomptionInstance);
        return aggrcomptionInstance;
    }

    public GeneralizationSpecification createGenSpec(java.lang.String name, UMLClass parent, int type) {
        if (relationships.stream().anyMatch(x -> x.getName().equals(name))) {
            return null;
        }
        var genspecInstance = new GeneralizationSpecification(name, parent, type);
        relationships.add(genspecInstance);
        return genspecInstance;
    }

    public boolean deleteRelationship(java.lang.String name){
        if (relationships.stream().anyMatch(x -> x.getName().equals(name))) {
            relationships.remove(name);
            return true;
        }
        return false;
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
