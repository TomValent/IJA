package ija.projekt.uml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Magdalena Bellayova
 * Class for creeating agregation relationship.
 */
public class Agregation extends UMLClassRelationship{

    private Map<UMLClass, String> from = new HashMap<>();
    private  Map<UMLClass, String> to  = new HashMap<>();
    public Agregation(String name) {
        super(name);
    }

    public boolean addAgregationto(UMLClass oneclass, String cardinality){
        if (this.to.size() == 0) {
            this.to.put(oneclass,cardinality);
            return true;
        }
        return false;
    }
    public boolean addAgregationfrom(UMLClass oneclass, String cardinality){
        if (this.from.size() == 0) {
            this.from.put(oneclass,cardinality);
            return true;
        }
        return false;
    }
    public boolean deleteAgregationto(UMLClass oneclass){
        if(this.to.get(oneclass)==null) {
            return false;
        }
        this.to.remove(oneclass);
        return true;
    }
    public boolean deleteAgregationfrom(UMLClass oneclass){
        if(this.from.get(oneclass)==null) {
            return false;
        }
        this.from.remove(oneclass);
        return true;
    }


}
