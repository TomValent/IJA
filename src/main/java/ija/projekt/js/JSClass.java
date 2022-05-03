package ija.projekt.js;

import java.util.ArrayList;
import java.util.List;

public class JSClass {
    private String abs;
    private String name;
    private final List<String> attr = new ArrayList<>();

    public JSClass(String abs, String name, List<String> attr) {
        this.abs = abs;
        this.name = name;
        this.attr.addAll(attr);
    }

    public String getAbs() {
        return abs;
    }

    public String getName() {
        return name;
    }

    public List<String> getAttr() {
        return attr;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addAttr(String attr) {
        this.attr.add(attr);
    }

    public void rmAttr(String attr) {
        if (this.attr.contains(attr)) {
            this.attr.remove(attr);
        }
    }
}
