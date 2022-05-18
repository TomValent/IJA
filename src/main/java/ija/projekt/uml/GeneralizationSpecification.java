package ija.projekt.uml;

import java.util.*;

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
    public List<UMLClass> getChildren() {
        return Collections.unmodifiableList(children);
    }
    public UMLClass getParent() {
        return this.parent;
    }
    public int getType() {
        return this.gen_or_spec;
    }

    public String toString(){
        UMLClass class1 = this.parent;
        UMLClass class2 = this.children.get(0);
        return class1.getName() + "<--" + class2.getName();
    }

}
