package ija.projekt.uml;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomas Valent
 * Class represents quaestor in sequence diagram.
 */
public class UMLQuaestor extends UMLClassifier {
    private final List<LifelineObject> objs = new ArrayList<>();

    public UMLQuaestor(String name) {
        super(name);
    }

    public void addObject() {
        objs.add(new LifelineObject());
    }

    public void addObject(String desc, LifelineObject target) {
        LifelineObject newObj = new LifelineObject();
        newObj.linkTo(desc, target);
        objs.add(newObj);
    }

    public LifelineObject getObject(int index) {
        return objs.get(index);
    }

    public void addLink(int index, String desc, LifelineObject target) {
        objs.get(index).linkTo(desc, target);
    }

    public void destroyObjs() {
        for (var obj : objs) {
            obj.unlinkAll();
        }
        objs.clear();
    }

    public void unlink(String desc, int index) {
        objs.get(index).unlink(desc);
    }
}
