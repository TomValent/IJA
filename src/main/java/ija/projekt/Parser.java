package ija.projekt;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.security.KeyException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ija.projekt.js.*;
import ija.projekt.uml.*;

import static ija.projekt.Controller.history;
import static java.lang.System.exit;

/**
 * @author Tomas Valent
 * Class for parsing input and making output.
 * Open input/output file and load json to class/file.
 */
public class Parser {
    public static InOut loadData;
    private InOut stashData;

    static ClassDiagram classDiagram;
    static SequenceDiagram sequenceDiagram;
    /**
     * @param input string with a path to input file.
     * @throws IOException when can't open file or json is invalid.
     */
    public void parse(String input) throws IOException, KeyException {
        try {
            Gson gson = new Gson();
            loadData = gson.fromJson(new FileReader(input), InOut.class);
        }catch(IllegalStateException | JsonSyntaxException exception){
            exit(1);
        }

        classDiagram = new ClassDiagram(loadData.getNameClass());
        for (JSClass new_class : loadData.getClasses()) {
            UMLClass created_class = classDiagram.createClass(new_class.getName());
            created_class.setAbstract(Boolean.valueOf(new_class.getAbs()));
            for (JSAttr new_attribute : new_class.getAttr()) {
                UMLClassifier new_classifier = classDiagram.classifierForName(new_attribute.getType());
                UMLAttribute created_attribute = new UMLAttribute(new_attribute.getAttr(), new_classifier, UMLAttributeModifier.PRIVATE);
                created_class.addAttribute(created_attribute);
            }
            for (JSMessage new_method : loadData.getMessages()) {
                if (new_method.getSender().equals(created_class.getName())){
                    UMLClassifier new_classifier = classDiagram.classifierForName(new_method.getType());
                    UMLOperation created_method = new UMLOperation(new_method.getName(), new_classifier);
                    created_class.addMethod(created_method);
                }
            }
        }
        for (JSAssociation new_association : loadData.getAssociations()) {
            Association created_association = classDiagram.createAssociation(new_association.getDescription());
            created_association.addClasswithCardinality(classDiagram.findClass(new_association.getClass1()),new_association.getCardinality1());
            created_association.addClasswithCardinality(classDiagram.findClass(new_association.getClass2()),new_association.getCardinality2());
        }
        for (JSAggrComp new_aggrcomp : loadData.getAggr_comp()) {
            AggregationComposition created_aggrcomp = classDiagram.createAggrComp("aggrcomp", classDiagram.findClass(new_aggrcomp.getParentClass()),classDiagram.findClass(new_aggrcomp.getChildClass()), new_aggrcomp.getCardinality(), Integer.valueOf(new_aggrcomp.getType()));
        }
        for (JSInheritance new_genspec : loadData.getInherit()) {
            GeneralizationSpecification created_genspec = classDiagram.createGenSpec("genspec", classDiagram.findClass(new_genspec.getParentClass()), Integer.valueOf(new_genspec.getType()));
            for (JSChild new_child : new_genspec.getChildClasses()){
                created_genspec.addChild(classDiagram.findClass(new_child.getchild()));
            }
        }
//--------------------------------------------------------------------
        sequenceDiagram = new SequenceDiagram(loadData.getNameSeq());
        for (JSClass quaestorName : loadData.getClasses()) {        //className == quaestorName
            sequenceDiagram.addQuaestor(quaestorName.getName());
        }

        for (JSMessage request : loadData.getMessages()) {
            sequenceDiagram.getQ(request.getSender()).addLink(sequenceDiagram.getQ(request.getReceiver()));
            for(LifelineObject obj : sequenceDiagram.getQ(request.getSender()).getObjects()){
                obj.setTransmittion(request.getTransmition().equals("true"));
                obj.setDesc(request.getName());
            }
        }

        history.push(loadData);
    }

    /**
     * @throws IOException when writer can't write to file or can't find the directory.
     */
    public void save(String filename) throws IOException {
        loadData.removeClassDiagram();
        for (UMLClass classs: classDiagram.getClasses()){
            List<JSAttr> attrList = new ArrayList<>();
            for (UMLAttribute attribute: classs.getAttributes()){
                JSAttr attr = new JSAttr(attribute.getName(), attribute.getType().toString());
                attrList.add(attr);
            }
            List<String> methods= new ArrayList<>();
            for (UMLOperation method: classs.getMethods()){
                methods.add(method.getName());
            }
            for (UMLOperation method: classs.getMethods()){
                List<String> messages = new ArrayList<>();
                for (JSMessage message : loadData.getMessages()){
                    messages.add(message.getName());
                    if (!methods.contains(message.getName())){
                        loadData.rmMsg(message);
                    }
                }
                if (!messages.contains(method.getName())){
                    JSMessage message = new JSMessage(method.getName(),method.getType().toString(),classs.getName(), "", "false");
                    loadData.addMsg(message);
                }
            }
            JSClass jsClass = new JSClass(String.valueOf(classs.isAbstract()), classs.getName(),attrList);
            loadData.addClass(jsClass);
        }
        for (Association association : classDiagram.getAssociations()){
            ArrayList<UMLClass> containers = new ArrayList<>();
            ArrayList<String> cardinality = new ArrayList<>();
            for (Map.Entry<UMLClass, String> class_cardinality : association.getClassCardinality().entrySet()) {
                containers.add(class_cardinality.getKey());
                cardinality.add(class_cardinality.getValue());
            }
            String cardinality1 = cardinality.get(0);
            String cardinality2 = cardinality.get(1);
            UMLClass class1 = containers.get(0);
            UMLClass class2 = containers.get(1);
            JSAssociation jsAssociation = new JSAssociation(association.getName(),class1.getName(),class2.getName(),cardinality1,cardinality2);
            loadData.addAssociation(jsAssociation);
        }
        for (GeneralizationSpecification generalization : classDiagram.getGenspecs()){
            List<JSChild> jsChildren = new ArrayList<>();
            for(UMLClass child : generalization.getChildren()){
                JSChild jsChild = new JSChild(child.getName());
                jsChildren.add(jsChild);
            }
            JSInheritance inheritance = new JSInheritance(generalization.getParent().getName(),String.valueOf(generalization.getType()),jsChildren);
            loadData.addInherit(inheritance);
        }
        for (AggregationComposition aggrcomp : classDiagram.getAggrcomps()){
            JSAggrComp jsAggrComp = new JSAggrComp(aggrcomp.getParent().getName(),aggrcomp.getChild().getName(),aggrcomp.getChildCardinality(),String.valueOf(aggrcomp.getType()));
            loadData.addAggrComp(jsAggrComp);
        }
        try{
            Writer writer = new FileWriter("./data/" + filename + ".json");;
            new Gson().toJson(loadData, writer);
            writer.close();
        }catch(IllegalStateException | IOException | JsonSyntaxException exception){
            exit(1);
        }
    }

    public void stash(){
        stashData = loadData;
    }
    public void undo(){
        loadData = stashData;
    }
}