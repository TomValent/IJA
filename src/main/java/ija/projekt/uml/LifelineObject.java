package ija.projekt.uml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tomas Valent
 * Class for creeating objects in lifeline of quaestor in sequence diagram.
 */
public class LifelineObject {
    private final List<LifelineObject> from = new ArrayList<>();
    private final List<LifelineObject> to = new ArrayList<>();
    private int x1;
    private int x2;
    private boolean transmittion = false;

    /**
     * Linking function between 2 life-line objects (between 2 quaestors).
     * @param desc description of relationship.
     * @param target target object.
     */
    public void linkTo(String desc, LifelineObject target) {     //vytvori sipku z 1 objektu na druhy v seq diagrame medzi objektami na lifelines (obojstranne)
        this.to.add(target);
        target.linkFrom(desc, this);
    }

    public void setX1(UMLQuaestor q) {
        this.x1 = q.getX();
    }

    public int getX1(){
        return this.x1;
    }

    public void setX2(UMLQuaestor q) {
        this.x2 = q.getX();
    }

    public int getX2(){
        return this.x2;
    }

    /**
     * Link from oposite side. Object will have information what arrow is pointing on it.
     * @param desc description of relationship.
     * @param target target object.
     */
    private void linkFrom(String desc, LifelineObject target) {
        this.from.add(target);
    }

    /**
     * Unlink all requests from this object. In both ways.
     */
    public void unlinkAll() {
        to.forEach((target) -> target.from.remove(target));
        from.forEach((target) -> target.to.remove(target));
    }

    /**
     * Unlink 1 request.
     * @param target unlink request.
     */
    public void unlink(LifelineObject target) {
        target.from.remove(target);
        target.to.remove(target);
    }

    public void setTransmittion(boolean transmittion) {
        this.transmittion = transmittion;
    }

    public boolean isTransmittion() {
        return transmittion;
    }

    public List<LifelineObject> getFrom() {
        return from;
    }

    public List<LifelineObject> getTo() {
        return to;
    }
}
