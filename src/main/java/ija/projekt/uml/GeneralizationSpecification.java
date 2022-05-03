package ija.projekt.uml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Magdalena Bellayova
 * Class for creeating generalization or specification relationship.
 */

public class GeneralizationSpecification extends UMLClassRelationship{

    private ArrayList<UMLClass> generalization = new ArrayList<UMLClass>();
    private ArrayList<UMLClass> specifications = new ArrayList<UMLClass>();
    private int gen_or_spec; // 0 - generalization, 1 - specification

    public GeneralizationSpecification(String name) {
        super(name);
    }
    public GeneralizationSpecification(String name, int typ) {
        super(name);
        this.gen_or_spec = typ;
    }

    public boolean addGeneration(UMLClass oneclass){
        if (this.generalization.size() == 0) {
            this.generalization.add(oneclass);
            return true;
        }
        return false;
    }
    public boolean addSpecification(UMLClass oneclass){
        if (this.specifications.indexOf(oneclass)!=-1){
            this.specifications.add(oneclass);
            return true;
        }
        return false;
    }
    public boolean deleteGeneralization(UMLClass oneclass){
        if(this.generalization.indexOf(oneclass)==-1) {
            return false;
        }
        this.generalization.remove(oneclass);
        return true;
    }
    public boolean deleteSpecification(UMLClass oneclass){
        if(this.specifications.indexOf(oneclass)==-1) {
            return false;
        }
        this.specifications.remove(oneclass);
        return true;
    }

}
