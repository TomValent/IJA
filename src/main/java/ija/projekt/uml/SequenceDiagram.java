package ija.projekt.uml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SequenceDiagram extends UMLClassifier{
    private List<UMLQuaestor> quaestors = new ArrayList<>();
    private List<UMLRequest> requests = new ArrayList<>();
    private HashMap<UMLQuaestor, UMLQuaestor> fromTo = new HashMap<UMLQuaestor, UMLQuaestor>();
    private HashMap<UMLRequest, HashMap<UMLQuaestor, UMLQuaestor>> relationship = new HashMap<UMLRequest, HashMap<UMLQuaestor, UMLQuaestor>>();
    public SequenceDiagram(java.lang.String name){
        super(name);
    }
    public UMLQuaestor addQuaestor(java.lang.String name){
        if (quaestors.stream().anyMatch(x -> x.getName().equals(name))){
            return null;
        }
        var someone = new UMLQuaestor(name);
        quaestors.add(someone);
        return someone;
    }
    public UMLRequest addRequest(UMLQuaestor from, UMLQuaestor to, java.lang.String name)
    {
        if(quaestors.stream().noneMatch(x -> x.getName().equals(from.getName()))){        //vrati null ked kvestor nie je v diagrame
            return null;
        }
        if(quaestors.stream().noneMatch(x -> x.getName().equals(to.getName()))){
            return null;
        }

        var request = new UMLRequest(name);
        requests.add(request);
        fromTo.put(from, to);
        relationship.put(request, fromTo);
        return request;
    }

}
