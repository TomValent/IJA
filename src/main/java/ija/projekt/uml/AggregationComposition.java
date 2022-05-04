package ija.projekt.uml;

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
}
