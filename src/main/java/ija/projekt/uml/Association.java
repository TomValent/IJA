package ija.projekt.uml;

import javafx.scene.layout.VBox;

import java.util.*;

/**
 * @author Magdalena Bellayova
 * Class for creeating association relationship.
 */
public class Association extends UMLClassRelationship{

    private ArrayList<UMLAttribute> attrs = new ArrayList<UMLAttribute>();
    private  Map<UMLClass, String> class_cardinality = new HashMap<>();

    public Association(String name) {
        super(name);
    }

    public boolean addAttribute(UMLAttribute attr)
    {
        if(this.attrs.indexOf(attr)==-1)
        {
            this.attrs.add(attr);
            return true;
        }
        return false;

    }

    public int getAttrPosition(UMLAttribute attr)
    {
        return this.attrs.indexOf(attr);
    }

    public boolean removeAttr(UMLAttribute attr){
        if(this.attrs.indexOf(attr)==-1)
        {
            return false;
        }
        this.attrs.remove(attr);
        return true;
    }

    public int moveAttrAtPosition(UMLAttribute attr, int pos)
    {
        if(this.attrs.indexOf(attr)==-1)
        {
            return -1;
        }
        this.attrs.remove(attr);
        this.attrs.add(pos, attr);
        return 0;
    }

    public java.util.List<UMLAttribute> getAttributes()
    {
        List<UMLAttribute> unmodifiable_attrs = Collections.unmodifiableList(this.attrs);
        return unmodifiable_attrs;
    }

    public boolean addClasswithCardinality(UMLClass oneclass, String cardinality){
        if (this.class_cardinality.size()<2){
            this.class_cardinality.put(oneclass, cardinality);
            return true;
        }
        return false;
    }

    public boolean removeClass(UMLClass oneclass){
        if(this.class_cardinality.get(oneclass) == null){
            this.class_cardinality.remove(oneclass);
            return true;
        }
        return false;
    }
    public Map<UMLClass, String> getClassCardinality() {
        return Collections.unmodifiableMap(class_cardinality);
    }

    @Override
    public String toString(){
        ArrayList<UMLClass> classes = new ArrayList<>();
        for (Map.Entry<UMLClass, String> class_cardinality : this.getClassCardinality().entrySet()) {
           classes.add(class_cardinality.getKey());
        }
        UMLClass class1 = classes.get(0);
        UMLClass class2 = classes.get(1);
        return this.getName() + " : " + class1.getName() + "<-->" + class2.getName();
    }

}
