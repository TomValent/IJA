package ija.projekt.js;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Tomas Valent
 * Class for storing json.
 */
public class InOut{
    private String name;
    private List<JSClass> classes = new ArrayList<>();
    private List<JSMessage> messages = new ArrayList<>();

    public void InOut(String name, List<JSClass> classes, List<JSMessage>  messages){
        this.name = name;
        this.classes = classes;
        this.messages = messages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}