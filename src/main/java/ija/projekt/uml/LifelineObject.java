package ija.projekt.uml;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tomas Valent
 * Class for creeating objects in lifeline of quaestor in sequence diagram.
 */
public class LifelineObject {
    private final Map<String, LifelineObject> from = new HashMap<>();
    private final Map<String, LifelineObject> to = new HashMap<>();

    /**
     * Linking function between 2 life-line objects (between 2 quaestors).
     * @param desc description of relationship.
     * @param target target object.
     */
    public void linkTo(String desc, LifelineObject target) {     //vytvori sipku z 1 objektu na druhy v seq diagrame medzi objektami na lifelines (obojstranne)
        this.to.put(desc, target);
        target.linkFrom(desc, this);
    }

    /**
     * Link from oposite side. Object will have information what arrow is pointing on it.
     * @param desc description of relationship.
     * @param target target object.
     */
    private void linkFrom(String desc, LifelineObject target) {
        this.from.put(desc, target);
    }

    /**
     * Unlink all reuqests from this object. In both ways.
     */
    public void unlinkAll() {
        to.forEach((key, target) -> target.from.remove(key));
        from.forEach((key, target) -> target.to.remove(key));
    }

    /**
     * Unlink 1 request.
     * @param desc description of request.
     */
    public void unlink(String desc) {
        to.get(desc).from.remove(desc);
        to.remove(desc);
    }
}
