package ija.projekt.uml;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomas Valent
 * Class for creeating objects in lifeline of quaestor in sequence diagram.
 */
public class LifelineObject {
    private final UMLQuaestor target;
    private boolean transmittion = false;

    public LifelineObject(UMLQuaestor target, boolean transmittion) {
        this.target = target;
        this.transmittion = transmittion;
    }

    public UMLQuaestor getTarget(){
        return target;
    }

    public void setTransmittion(boolean transmittion) {
        this.transmittion = transmittion;
    }

    public boolean isTransmittion() {
        return transmittion;
    }

}
