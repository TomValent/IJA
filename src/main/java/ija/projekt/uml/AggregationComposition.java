package ija.projekt.uml;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author Magdalena Bellayova
 * Class for creeating agregation relationship.
 */
public class AggregationComposition extends UMLClassRelationship{

    private UMLClass parent;
    private UMLClass child;
    private String child_cardinality;
    private int type; //aggregation 0 composition 1

    public AggregationComposition(String name, UMLClass parent, UMLClass child, String child_cardinality, int type) {
        super(name);
        this.parent = parent;
        this.child = child;
        this.child_cardinality = child_cardinality;
        this.type = type;
    }
    public UMLClass getParent() {
        return this.parent;
    }
    public UMLClass getChild() {
        return this.child;
    }
    public String getChildCardinality() {
        return this.child_cardinality;
    }
    public int getType() {
        return this.type;
    }
    @Override
    public String toString(){
        UMLClass class1 = this.parent;
        UMLClass class2 = this.child;
        if(this.type == 0){
            return "aggr" + " : " + class1.getName() + "<>--" + class2.getName();
        }
        else{
            return "comp" + " : " + class1.getName() + "<>--" + class2.getName();
        }
    }

}
