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

    public void linkTo(String desc, LifelineObject target) {     //vytvori sipku z 1 objektu na druhy v seq diagrame medzi objektami na lifelines (obojstranne)
        this.to.put(desc, target);
        target.linkFrom(desc, this);
    }

    private void linkFrom(String desc, LifelineObject target) {
        this.from.put(desc, target);
    }

    public void unlinkAll() {
        to.forEach((key, target) -> target.from.remove(key));
        from.forEach((key, target) -> target.to.remove(key));
    }

    public void unlink(String desc) {
        to.get(desc).from.remove(desc);
        to.remove(desc);
    }
}
