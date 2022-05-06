package ija.projekt.js;

/**
 * @author Tomas Valent
 * Class for storing child classes that inherits something.
 */
public class JSChild {
    private String child;

    /**
     * Constructor for child.
     * @param child child name.
     */
    public JSChild(String child) {
        this.child = child;
    }

    /**
     * Getter for child.
     * @return name of child.
     */
    public String getchild() {
        return child;
    }

    /**
     * Setter for child.
     * @param child new child name.
     */
    public void setchild(String child) {
        this.child = child;
    }
}
