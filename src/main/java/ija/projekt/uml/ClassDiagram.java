package ija.projekt.uml;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Magdalena Bellayova
 * Class for creeating class diagram.
 */
public class ClassDiagram extends Element {
    private final List<UMLClassifier> classifiers = new ArrayList<>();


    private ObservableList<UMLClass> classes = FXCollections.observableArrayList();
    private ObservableList<Association> associations = FXCollections.observableArrayList();
    private ObservableList<AggregationComposition> aggrcomps = FXCollections.observableArrayList();
    private ObservableList<GeneralizationSpecification> genspecs = FXCollections.observableArrayList();


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
    public void addAssociation(Association association) {
        associations.add(association);
    }

    public AggregationComposition createAggrComp(java.lang.String name, UMLClass parent, UMLClass child, String child_cardinality, int type) {
        var aggrcomptionInstance = new AggregationComposition(name, parent, child, child_cardinality, type);
        aggrcomps.add(aggrcomptionInstance);
        return aggrcomptionInstance;
    }
    public void addGeneralization(GeneralizationSpecification generalization) {
        genspecs.add(generalization);
    }

    public GeneralizationSpecification createGenSpec(java.lang.String name, UMLClass parent, int type) {
        var genspecInstance = new GeneralizationSpecification(name, parent, type);
        genspecs.add(genspecInstance);
        return genspecInstance;
    }
    public void addAggrcomp(AggregationComposition aggrcomp) {
        aggrcomps.add(aggrcomp);
    }

    public boolean deleteAssociation(java.lang.String name){
        if (associations.stream().anyMatch(x -> x.getName().equals(name))) {
            associations.remove(name);
            return true;
        }
        return false;
    }
    public void removeAssociation(Association association){
        associations.remove(association);
    }

    public boolean deleteAggrComp(java.lang.String name){
        if (aggrcomps.stream().anyMatch(x -> x.getName().equals(name))) {
            aggrcomps.remove(name);
            return true;
        }
        return false;
    }
    public void removeGeneralization(GeneralizationSpecification generalization){
        genspecs.remove(generalization);
    }

    public boolean deleteGenspec(java.lang.String name){
        if (genspecs.stream().anyMatch(x -> x.getName().equals(name))) {
            genspecs.remove(name);
            return true;
        }
        return false;
    }
    public void removeAggrcomp(AggregationComposition aggrcomp){
        aggrcomps.remove(aggrcomp);
    }

    public void removeRelationships(ArrayList<Association> remove_assoc, ArrayList<GeneralizationSpecification> remove_gen, ArrayList<AggregationComposition> remove_aggr){
        for(Association assoc: remove_assoc){
            associations.remove(assoc);
        }
        for(GeneralizationSpecification gen: remove_gen){
            genspecs.remove(gen);
        }
        for(AggregationComposition aggr: remove_aggr){
            aggrcomps.remove(aggr);
        }

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
        return FXCollections.unmodifiableObservableList(classes);
    }
    public List<Association> getAssociations() {
        return FXCollections.unmodifiableObservableList(associations);
    }
    public List<AggregationComposition> getAggrcomps() {
        return FXCollections.unmodifiableObservableList(aggrcomps);
    }
    public List<GeneralizationSpecification> getGenspecs() { return FXCollections.unmodifiableObservableList(genspecs); }
}
