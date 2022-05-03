package ija.projekt.uml;

import java.security.KeyException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @author Tomas Valent
 * Class for creeating sequence diagram.
 */
public class SequenceDiagram extends UMLClassifier {
    private final List<UMLQuaestor> quaestors = new ArrayList<>();

    public SequenceDiagram(java.lang.String name) {
        super(name);
    }

    public UMLQuaestor addQuaestor(java.lang.String name) {
        if (quaestors.stream().anyMatch(x -> x.getName().equals(name))) {
            return null;
        }
        var someone = new UMLQuaestor(name);
        quaestors.add(someone);
        return someone;
    }
    public void makeObject(UMLQuaestor target){
        target.addObject();
    }
    public void addRequest(UMLQuaestor from, int indexFrom, UMLQuaestor to, int indexTo, java.lang.String desc) {
        from.addLink(indexFrom, desc, to.getObject(indexTo));
    }

    public void removeRequest(String desc, int index, UMLQuaestor target) {
        target.unlink(desc, index);
    }

    public void removeQuaestor(UMLQuaestor someone) {
        someone.destroyObjs();
        quaestors.remove(someone);
    }
    public UMLQuaestor getQ(String name) throws KeyException {
        var tmp = quaestors.stream().filter(x -> Objects.equals(x.getName(), name));
        if(tmp.findAny().isEmpty()){
            throw new KeyException("Given name not in list.");
        }
        return (UMLQuaestor) tmp.toArray()[0];
    }
}
