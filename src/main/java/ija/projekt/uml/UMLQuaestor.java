package ija.projekt.uml;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomas Valent
 * Class represents quaestor in sequence diagram.
 */
public class UMLQuaestor extends UMLClassifier {
    private final List<LifelineObject> objs = new ArrayList<>();

    /**
     * Constructor of quaestor.
     * @param name name of quaestor.
     */
    public UMLQuaestor(String name) {
        super(name);
    }

    /**
     * add new life-line object to quaestors life-line.
     */
    public void addObject() {
        objs.add(new LifelineObject());
    }

    /**
     * Add new life-line object to quaestros life-line
     * with description and targetted quaestor.
     * @param desc description of relationship.
     * @param target target quaestor to link.
     */
    public void addObject(String desc, LifelineObject target) {
        LifelineObject newObj = new LifelineObject();
        newObj.linkTo(desc, target);
        objs.add(newObj);
    }

    /**
     * Getter for list of life-line object on index.
     * @param index index of object.
     * @return
     */
    public LifelineObject getObject(int index) {
        return objs.get(index);
    }

    /**
     * Add link between 2 Quaestors.
     * @param index index of object.
     * @param desc description of relationship.
     * @param target target quaestor.
     */
    public void addLink(int index, String desc, LifelineObject target) {
        objs.get(index).linkTo(desc, target);
    }

    /**
     * Destroy all objects in quaestors life-line.
     */
    public void destroyObjs() {
        for (var obj : objs) {
            obj.unlinkAll();
        }
        objs.clear();
    }

    /**
     * Cancel linking between 2 objects.
     * @param desc description of relationship.
     * @param index ondex of object.
     */
    public void unlink(String desc, int index) {
        objs.get(index).unlink(desc);
    }
}
