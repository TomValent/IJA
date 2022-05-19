package ija.projekt.uml;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomas Valent
 * Class represents quaestor in sequence diagram.
 */
public class UMLQuaestor extends UMLClassifier {
    private final List<LifelineObject> objs = new ArrayList<>();
    private int x;

    /**
     * Constructor of quaestor.
     * @param name name of quaestor.
     */
    public UMLQuaestor(String name) {
        super(name);
    }

    public String getName(){
        return this.name;
    }

    public void addLink(UMLQuaestor target){
        objs.add(new LifelineObject(target, false));
    }

    public UMLQuaestor addLink(UMLQuaestor target, boolean trans){
        objs.add(new LifelineObject(target, trans));
        return this;
    }

    public List<LifelineObject> getObjects() {
        return this.objs;
    }

    /**
     * Getter for list of life-line object on index.
     * @param index index of object.
     * @return object
     */
    public LifelineObject getObject(int index) {
        return objs.get(index);
    }

    /**
     * Cancel linking between 2 objects.
     * @param target message what will be removed.
     */
    public void unlink(LifelineObject target) {
        objs.remove(target);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

}