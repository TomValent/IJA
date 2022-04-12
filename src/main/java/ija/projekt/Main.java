package ija.projekt;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

import ija.projekt.uml.SequenceDiagram;
import ija.projekt.uml.UMLQuaestor;
import ija.projekt.uml.UMLRequest;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Tomas Valent
 * Class where program starts
 * open input/output file and load json to variables.
 */
public class Main {
    public static void main(String[] args) {
        File input = new File("./data/example.json");
        try {
            JsonElement fileElement = JsonParser.parseReader(new FileReader(input));        //input file
            FileWriter myWriter = new FileWriter("output.json");                    //output file
            JsonObject fileObject = fileElement.getAsJsonObject();
            String title = fileObject.get("name").getAsString();

            myWriter.write("Title: " + title + "\n");           //write to output

            //classes/quaestors
            JsonArray classes = fileObject.get("classes").getAsJsonArray();
            for(JsonElement c : classes)
            {
                JsonObject classObject = c.getAsJsonObject();
                boolean isAbstract = classObject.get("abstract").getAsBoolean();
                String name = classObject.get("name").getAsString();
                boolean userDefined = classObject.get("userDefined").getAsBoolean();

                myWriter.write("\tAbstract: " + isAbstract + "\n");           //write to output
                myWriter.write("\tName: " + name + "\n");
                myWriter.write("\tUserDefined: " + userDefined + "\n");

                //attributes
                JsonArray attributes = classObject.get("attributes").getAsJsonArray();
                JsonObject attrObject;
                for(JsonElement a : attributes)
                {
                    attrObject = a.getAsJsonObject();
                    myWriter.write("\t\tAttributes: " + attrObject + "\n\n");
                }
            }
            myWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        /*
          //Navod jak robit so sekvencnym diagramom
        var diagram = new SequenceDiagram("seq1");
        UMLQuaestor a = diagram.addQuaestor("a");
        UMLQuaestor b = diagram.addQuaestor("b");
        a.addObject();      //objekt na casovej osi
        b.addObject();
        diagram.addRequest(a, 0, b, 0, "insert");       //request sa na objektoch indexuje manualne
        UMLQuaestor c = diagram.addQuaestor("c");
        c.addObject();
        diagram.addRequest(c, 0, a, 0, "newLink");
        diagram.removeQuaestor(a);        //zmaze requesty ktore na dany questor ukazuju alebo naopak*/
    }
}
