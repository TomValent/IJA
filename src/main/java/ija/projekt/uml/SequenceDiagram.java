package ija.projekt.uml;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.security.KeyException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Tomas Valent
 * Class for creeating sequence diagram.
 */
public class SequenceDiagram extends Element {
    private ObservableList<UMLQuaestor> quaestors = FXCollections.observableArrayList();

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
     * Remove request from diagram.
     * @param index index of request.
     * @param target target quaestor.
     */
    public void removeRequest(int index, UMLQuaestor target) {
        target.unlink(target.getObject(index));
    }

    /**
     * Remove quaestor with all its requests.
     * @param someone quaestor that will be removed.
     */
    public void removeQuaestor(UMLQuaestor someone) {
        for (var q: quaestors
        ) {
            q.getObjects().removeIf(x -> x.getTarget().equals(someone));
        }
        quaestors.remove(someone);
    }
    public void removeLinks(UMLQuaestor someone) {
        for (var q: quaestors
        ) {
            q.getObjects().removeIf(x -> x.getTarget().equals(someone));
        }
    }

    public void removeQ(UMLQuaestor someone) {
        quaestors.remove(someone);
    }


    /**
     * Getter for quaestor.
     * @param name name of quaestor.
     * @return quaestor.
     * @throws KeyException quaestor does not exist.
     */
    public UMLQuaestor getQ(String name) {
        for(UMLQuaestor tmp : quaestors){
            if(tmp.getName().equals(name)){
                return tmp;
            }
        }
        return null;
    }

    public List<UMLQuaestor> getAllQuaestors(){
        return (quaestors);
    }

    @Override
    public String toString(){
        return this.name;
    }
}
