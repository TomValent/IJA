package ija.projekt.js;

/**
 * @author Tomas Valent
 * Class for storing aggregations and compositions.
 */
public class JSAggrComp {
    private String parentClass;
    private String childClass;
    private String cardinality;
    private String type;

    /**
     * Constructor of aggregation and composition.
     * @param parentClass parent class
     * @param childClass child class.
     * @param cardinality cardinality.
     * @param type type -> aggregation or composition -> 0 or 1.
     */
    public JSAggrComp(String parentClass, String childClass, String cardinality, String type) {
        this.parentClass = parentClass;
        this.childClass = childClass;
        this.cardinality = cardinality;
        this.type = type;
    }

    /**
     * Getter of parent class.
     * @return parent class.
     */
    public String getParentClass() {
        return parentClass;
    }

    /**
     * Setter of parent class.
     * @param parentClass new parent.
     */
    public void setParentClass(String parentClass) {
        this.parentClass = parentClass;
    }

    /**
     * Getter of child class.
     * @return child class.
     */
    public String getChildClass() {
        return childClass;
    }

    /**
     * Setter of child class.
     * @param childClass new child.
     */
    public void setChildClass(String childClass) {
        this.childClass = childClass;
    }

    /**
     * Getter of cardinality.
     * @return cardinality.
     */
    public String getCardinality() {
        return cardinality;
    }

    /**
     * Setter of cardinality.
     * @param cardinality new cardinality.
     */
    public void setCardinality(String cardinality) {
        this.cardinality = cardinality;
    }

    /**
     * Getter of type.
     * @return type.
     */
    public String getType() {
        return type;
    }

    /**
     * Setter of type.
     * @param type new type.
     */
    public void setType(String type) {
        this.type = type;
    }
}
