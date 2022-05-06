package ija.projekt.js;

/**
 * @author Tomas Valent
 * Class for storing associations.
 */
public class JSAssociation {
    private String desc;
    private String class1;
    private String class2;
    private String cardinality1;
    private String cardinality2;

    /**
     * Constructor of association.
     * @param description description od association.
     * @param class1 first class of association.
     * @param class2 second class of association.
     * @param cardinality1 cardinality of first class.
     * @param cardinality2 cardinality of second class.
     */
    public JSAssociation(String description, String class1, String class2, String cardinality1, String cardinality2) {
        this.desc = description;
        this.class1 = class1;
        this.class2 = class2;
        this.cardinality1 = cardinality1;
        this.cardinality2 = cardinality2;
    }

    /**
     * Getter of description.
     * @return description od association.
     */
    public String getDescription() {
        return desc;
    }

    /**
     * Setter of descroption.
     * @param description new description.
     */
    public void setDescription(String description) {
        this.desc = description;
    }

    /**
     * Getter of class 1.
     * @return class 1 that is associated.
     */
    public String getClass1() {
        return class1;
    }

    /**
     * Setter of class 1.
     * @param class1 new class 1.
     */
    public void setClass1(String class1) {
        this.class1 = class1;
    }

    /**
     * Getter of class 2.
     * @return class 2 that is associated.
     */
    public String getClass2() {
        return class2;
    }

    /**
     * Setter of class 2.
     * @param class2 new class 2.
     */
    public void setClass2(String class2) {
        this.class2 = class2;
    }

    /**
     * Getter for first cardinality.
     * @return first cardinality.
     */
    public String getCardinality1() {
        return cardinality1;
    }

    /**
     * Setter for first cardinality.
     * @param cardinality1 new cardinality.
     */
    public void setCardinality1(String cardinality1) {
        this.cardinality1 = cardinality1;
    }

    /**
     * Getter for second cardinality.
     * @return second cardinality.
     */
    public String getCardinality2() {
        return cardinality2;
    }

    /**
     * Setter for second cardinality.
     * @param cardinality2 new cardinality.
     */
    public void setCardinality2(String cardinality2) {
        this.cardinality2 = cardinality2;
    }
}
