package ija.projekt.js;

import java.util.ArrayList;
import java.util.List;

public class JSClass {
    private final boolean abs;
    private final String name;
    private final List<String> attr = new ArrayList<>();

    public JSClass(boolean abs, String name, List<String> attr) {
        this.abs = abs;
        this.name = name;
        this.attr.addAll(attr);
    }

    public boolean isAbs() {
        return abs;
    }

    public String getName() {
        return name;
    }

    public List<String> getAttr() {
        return attr;
    }
}
