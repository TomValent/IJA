package ija.projekt;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import ija.projekt.js.*;
import static java.lang.System.exit;

/**
 * @author Tomas Valent
 * Class for parsing input and making output.
 * Open input/output file and load json to class/file.
 */
public class Parser {
    private InOut loadData;

    /**
     * @param input string with a path to input file.
     * @throws IOException when can't open file or json is invalid.
     */
    public void parse(String input) throws IOException {
        try {
            Gson gson = new Gson();
            loadData = gson.fromJson(new FileReader(input), InOut.class);
        }catch(IllegalStateException | JsonSyntaxException exception){
            exit(1);
        }
    }

    /**
     * @throws IOException when writer can't write to file or can't find the directory.
     */
    public void save() throws IOException{
        try{
            Gson gson = new Gson();
            String output = gson.toJson(loadData);
            FileWriter myWriter = new FileWriter("./data/output.json");
            myWriter.write(output);
        }catch(IllegalStateException | IOException | JsonSyntaxException exception){
            exit(1);
        }
    }
}
