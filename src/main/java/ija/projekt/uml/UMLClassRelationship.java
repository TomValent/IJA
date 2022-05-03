package ija.projekt.uml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Magdalena Bellayova
 * Class for creeating relationships.
 */

public class UMLClassRelationship extends UMLClassifier{

    private boolean isAbstract;


    public UMLClassRelationship(java.lang.String name)
    {
        super(name);
        this.isAbstract = false;
    }

    public boolean isAbstract()
    {
        return this.isAbstract;
    }

    public void setAbstract(boolean isAbstract)
    {
        this.isAbstract = isAbstract;
    }

}
