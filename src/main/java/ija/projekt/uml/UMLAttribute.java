package ija.projekt.uml;

public class UMLAttribute extends UMLClass{
    UMLClassifier classifier;

    public UMLAttribute(String name, UMLClassifier classifier) {
        super(name);
        this.classifier = classifier;
    }
    public UMLClassifier getType()
    {
        return classifier;
    }
    @Override
    public java.lang.String toString()
    {
        return name + ":" + classifier;
    }
}