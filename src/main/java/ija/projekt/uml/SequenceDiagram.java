package ija.projekt.uml;

import java.security.KeyException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @author Tomas Valent
 * Class for creeating sequence diagram.
 */
public class SequenceDiagram extends Element {
    private final List<UMLQuaestor> quaestors = new ArrayList<>();

    /**
     * Constructor of sequence diagram.
     * @param name name of diagram.
     */
    public SequenceDiagram(java.lang.String name) {
        super(name);
    }

    /**
     * Add new quaestor to diagram.
     * @param name name of quaestor.
     * @return new quaestor.
     */
    public UMLQuaestor addQuaestor(java.lang.String name) {
        if (quaestors.stream().anyMatch(x -> x.getName().equals(name))) {
            return null;
        }
        var someone = new UMLQuaestor(name);
        quaestors.add(someone);
        return someone;
    }

    /**
     * Add new object to a quaestor.
     * @param target target quaestor.
     */
    public void makeObject(UMLQuaestor target){
        target.addObject();
    }

    /**
     * Add new request to diagram and link 2 quaestors.
     * @param from where is the arrow (request) comming from.
     * @param indexFrom index of object of "from" quaestor.
     * @param to where is the arrow (request) going to.
     * @param indexTo index of object "to" quaestor.
     * @param desc description of request.
     */
    public void addRequest(UMLQuaestor from, int indexFrom, UMLQuaestor to, int indexTo, java.lang.String desc) {
        from.addLink(indexFrom, desc, to.getObject(indexTo));
    }

    /**
     * Remove request from diagram.
     * @param desc description of request.
     * @param index index of request.
     * @param target target quaestor.
     */
    public void removeRequest(String desc, int index, UMLQuaestor target) {
        target.unlink(desc, index);
    }

    /**
     * Remove quaestor with all its requests.
     * @param someone quaestor that will be removed.
     */
    public void removeQuaestor(UMLQuaestor someone) {
        someone.destroyObjs();
        quaestors.remove(someone);
    }

    /**
     * Getter for quaestor.
     * @param name name of quaestor.
     * @return quaestor.
     * @throws KeyException quaestor does not exist.
     */
    public UMLQuaestor getQ(String name) throws KeyException {
        var tmp = quaestors.stream().filter(x -> Objects.equals(x.getName(), name));
        if(tmp.findAny().isEmpty()){
            throw new KeyException("Given name not in list.");
        }
        return (UMLQuaestor) tmp.toArray()[0];
    }
}
