package ija.projekt.uml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Magdalena Bellayova
 * Class for creeating class diagram.
 */
public class ClassDiagram extends Element {
    private final List<UMLClassifier> classifiers = new ArrayList<>();

    private final List<UMLClass> classes= new ArrayList<>();
    private final List<Association> associations = new ArrayList<>();
    private final List<AggregationComposition> aggrcomps = new ArrayList<>();
    private final List<GeneralizationSpecification> genspecs = new ArrayList<>();

    public ClassDiagram(java.lang.String name) {
        super(name);
    }

    public UMLClass createClass(java.lang.String name) {
        if (classifiers.stream().anyMatch(x -> x.getName().equals(name))) {
            return null;
        }
        var classInstance = new UMLClass(name);
        classifiers.add(classInstance);
        classes.add(classInstance);
        return classInstance;
    }

    public Association createAssociation(java.lang.String name) {
        var associationInstance = new Association(name);
        associations.add(associationInstance);
        return associationInstance;
    }
    public AggregationComposition createAggrComp(java.lang.String name, UMLClass parent, UMLClass child, String child_cardinality, int type) {
        var aggrcomptionInstance = new AggregationComposition(name, parent, child, child_cardinality, type);
        aggrcomps.add(aggrcomptionInstance);
        return aggrcomptionInstance;
    }

    public GeneralizationSpecification createGenSpec(java.lang.String name, UMLClass parent, int type) {
        var genspecInstance = new GeneralizationSpecification(name, parent, type);
        genspecs.add(genspecInstance);
        return genspecInstance;
    }

    public boolean deleteAssociation(java.lang.String name){
        if (associations.stream().anyMatch(x -> x.getName().equals(name))) {
            associations.remove(name);
            return true;
        }
        return false;
    }
    public boolean deleteAggrComp(java.lang.String name){
        if (aggrcomps.stream().anyMatch(x -> x.getName().equals(name))) {
            aggrcomps.remove(name);
            return true;
        }
        return false;
    }
    public boolean deleteGenspec(java.lang.String name){
        if (genspecs.stream().anyMatch(x -> x.getName().equals(name))) {
            genspecs.remove(name);
            return true;
        }
        return false;
    }

    public UMLClassifier findClassifier(java.lang.String name) {
        var found = classifiers.stream().filter(x -> x.getName().equals(name)).findFirst();
        return found.orElse(null);
    }
    public UMLClass findClass(java.lang.String name) {
        var found = classes.stream().filter(x -> x.getName().equals(name)).findFirst();
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
        classes.remove(obj);
    }

    public List<UMLClass> getClasses() {
        return Collections.unmodifiableList(classes);
    }
    public List<Association> getAssociations() {
        return Collections.unmodifiableList(associations);
    }
    public List<AggregationComposition> getAggrcomps() {
        return Collections.unmodifiableList(aggrcomps);
    }
    public List<GeneralizationSpecification> getGenspecs() {
        return Collections.unmodifiableList(genspecs);
    }
}
