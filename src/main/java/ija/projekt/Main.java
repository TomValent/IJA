package ija.projekt;

import ija.projekt.uml.SequenceDiagram;
import ija.projekt.uml.UMLQuaestor;
import ija.projekt.uml.UMLRequest;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("File not found.");
            System.exit(1);
        }
        var fileName = args[0];
        FileReader file = new FileReader(fileName);
        file.readI();
        file.closeFile();

        /*  //Navod jak robit so sekvencnym diagramom
        var diagram = new SequenceDiagram("seq1");
        UMLQuaestor a = diagram.addQuaestor("a");
        UMLQuaestor b = diagram.addQuaestor("b");
        a.addObject();      //objekt na casovej osi
        b.addObject();
        diagram.addRequest(a, 0, b, 0, "insert");       //request sa na objektoch indexuje manualne
        UMLQuaestor c = diagram.addQuaestor("c");
        c.addObject();
        diagram.addRequest(c, 0, a, 0, "newLink");
        diagram.removeQuaestor(a); */       //zmaze requesty ktore na dany questor ukazuju alebo naopak
    }
}
