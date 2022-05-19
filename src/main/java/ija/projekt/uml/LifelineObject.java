package ija.projekt.uml;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomas Valent
 * Class for creeating objects in lifeline of quaestor in sequence diagram.
 */
public class LifelineObject {
    private final UMLQuaestor target;
    private String desc;
    private boolean transmittion;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
