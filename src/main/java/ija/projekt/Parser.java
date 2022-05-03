package ija.projekt;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import ija.projekt.js.*;

/**
 * @author Tomas Valent
 * Class where program starts
 * open input/output file and load json to class.
 */
public class Parser {
    private InOut loadData;
    public void parse(String input) throws IOException {           //parsing json input
        try {
            Gson gson = new Gson();
            loadData = gson.fromJson(new FileReader(input), InOut.class);
        }catch(IllegalStateException | JsonSyntaxException exception){
            System.err.println(exception);
        }
    }
    public void save() throws IOException{                        //save json to file
        try{
            Gson gson = new Gson();
            String output = gson.toJson(loadData);
            FileWriter myWriter = new FileWriter("./data/output.json");
            myWriter.write(output);
        }catch(IllegalStateException | IOException | JsonSyntaxException exception){
            System.err.println(exception);
        }

    }
}
