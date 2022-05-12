package ija.projekt;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.security.KeyException;

import ija.projekt.js.*;
import ija.projekt.uml.*;

import static java.lang.System.exit;

/**
 * @author Tomas Valent
 * Class for parsing input and making output.
 * Open input/output file and load json to class/file.
 */
public class Parser {
    private InOut loadData;
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

        classDiagram = new ClassDiagram("loadData.getName()");
        for (JSClass new_class : loadData.getClasses()) {
            UMLClass created_class = classDiagram.createClass(new_class.getName());
            created_class.setAbstract(Boolean.valueOf(new_class.getAbs()));
            for (JSAttr new_attribute : new_class.getAttr()) {
                UMLClassifier new_classifier = classDiagram.classifierForName(new_attribute.getType());
                UMLAttribute created_attribute = new UMLAttribute(new_attribute.getAttr(), new_classifier);
                created_class.addAttribute(created_attribute);
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
            }
        }
    }

    /**
     * @throws IOException when writer can't write to file or can't find the directory.
     */
    public void save() throws IOException{
        try{
            Writer writer = new FileWriter("./data/output.json");;
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
