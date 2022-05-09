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

    private ArrayList<UMLClass> children = new ArrayList<UMLClass>();
    private int gen_or_spec; // 0 - generalization, 1 - specification
    private UMLClass parent;
    public GeneralizationSpecification(String name, UMLClass parent, int type) {
        super(name);
        this.parent = parent;
        this.gen_or_spec = type;
    }

    public boolean addChild(UMLClass oneclass){
        if (this.children.size() == 0) {
            this.children.add(oneclass);
            return true;
        }
        return false;
    }

    public boolean deleteChild(UMLClass oneclass){
        if(this.children.indexOf(oneclass)==-1) {
            return false;
        }
        this.children.remove(oneclass);
        return true;
    }


}
