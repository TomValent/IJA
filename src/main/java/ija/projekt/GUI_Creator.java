package ija.projekt;

import ija.projekt.uml.*;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static ija.projekt.Parser.classDiagram;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class GUI_Creator {
    private Map<String, Integer> relationship_count = new HashMap<>();
    private int type; // 0 - aggregation, 1 - composition, 2 - generalization, 3 - specification, 4 - association
    public Pane createClasses(Pane class_pane){
        String cssLayout = "-fx-border-color: black;\n" +
                "-fx-border-insets: 0;\n" +
                "-fx-border-width: 1;\n";

        int x = 20;
        int y = 20;
        for (UMLClass classes : classDiagram.getClasses()){
            ListView<Object> new_class = new ListView<>();
            Label name = new Label();
            name.setFont(Font.font(15));
            name.setText(classes.getName());
            for(UMLAttribute attribute : classes.getAttributes()){
                new_class.getItems().add(attribute.toString());
            }

            VBox container = new VBox();
            container.getChildren().addAll(name, new_class);
            container.setAlignment(Pos.CENTER);
            container.setStyle(cssLayout);
            container.setPrefSize(200, 100);
            container.setUserData(classes.getName());

            class_pane.getChildren().add(container);
            container.relocate(x,y);
            x += 300;
            if (x > 400){
                y += 200;
                x = 20;
            }
        }
        return class_pane;
    }
    public Pane createAssociations(Pane class_pane){
        for( Association new_assoc : classDiagram.getAssociations()) {
            ArrayList<Double> coordination = new ArrayList<>();
            ArrayList<String> classes = new ArrayList<>();
            ArrayList<String> cardinality = new ArrayList<>();
            for (Map.Entry<UMLClass, ArrayList<String>> class_cardinality : new_assoc.getClassCardinality().entrySet()) {
                for (Node n : class_pane.getChildren()) {
                    if (class_cardinality.getKey().getName().equals(n.getUserData())) {
                        relationship_count.merge(n.getUserData().toString(), 1, Integer::sum);
                        coordination.add(n.getLayoutX());
                        coordination.add(n.getLayoutY());
                        classes.add(n.getUserData().toString());
                        for(String value:class_cardinality.getValue()){
                            cardinality.add(value);
                        }
                    }
                }
            }
            Double x1 = coordination.get(0);
            Double y1 = coordination.get(1);
            Double x2 = coordination.get(2);
            Double y2 = coordination.get(3);
            int shift1 = relationship_count.get(classes.get(0)) * 10;
            int shift2 = relationship_count.get(classes.get(1)) * 10;
            String cardinality1 = cardinality.get(0);
            String cardinality2 = cardinality.get(0);
            Group line = this.createLine(x1, y1, x2, y2, shift1, shift2, 4, cardinality1,cardinality2);
            class_pane.getChildren().add(line);
        }
        return class_pane;
    }

    public Pane createGenspecs(Pane class_pane){
        for( GeneralizationSpecification new_genspec : classDiagram.getGenspecs()) {
            ArrayList<Double> coordination = new ArrayList<>();
            ArrayList<String> classes = new ArrayList<>();
            for (Node n : class_pane.getChildren()) {
                if (new_genspec.getParent().getName().equals(n.getUserData())) {
                    relationship_count.merge(n.getUserData().toString(), 1, Integer::sum);
                    coordination.add(n.getLayoutX());
                    coordination.add(n.getLayoutY());
                    classes.add(n.getUserData().toString());
                }
            }
            for (UMLClass child: new_genspec.getChildren()) {
                for (Node n : class_pane.getChildren()) {
                    if (child.getName().equals(n.getUserData())) {
                        relationship_count.merge(n.getUserData().toString(), 1, Integer::sum);
                        coordination.add(n.getLayoutX());
                        coordination.add(n.getLayoutY());
                        classes.add(n.getUserData().toString());
                    }
                }
            }
            int type = new_genspec.getType() + 2;
            Double x1 = coordination.get(0);
            Double y1 = coordination.get(1);
            coordination.remove(0);
            coordination.remove(0);
            while (coordination.size()>0){
                Double x2 = coordination.get(0);
                Double y2 = coordination.get(1);
                int shift1 = relationship_count.get(classes.get(0)) * 10;
                int shift2 = relationship_count.get(classes.get(1)) * 10;
                Group line = this.createLine(x1, y1, x2, y2, shift1, shift2, type,"","");
                class_pane.getChildren().add(line);
                coordination.remove(0);
                coordination.remove(0);
            }
        }
        return class_pane;
    }

    public Pane createAggrComps(Pane class_pane){
        for( AggregationComposition new_aggrcomp : classDiagram.getAggrcomps()) {
            ArrayList<Double> coordination = new ArrayList<>();
            ArrayList<String> classes = new ArrayList<>();
            String child_cardinality = "";
            for (Node n : class_pane.getChildren()) {
                if (new_aggrcomp.getParent().getName().equals(n.getUserData()))  {
                    relationship_count.merge(n.getUserData().toString(), 1, Integer::sum);
                    coordination.add(n.getLayoutX());
                    coordination.add(n.getLayoutY());
                    classes.add(n.getUserData().toString());
                }
            }
            for (Node n : class_pane.getChildren()) {
                if (new_aggrcomp.getChild().getName().equals(n.getUserData()))  {
                    relationship_count.merge(n.getUserData().toString(), 1, Integer::sum);
                    coordination.add(n.getLayoutX());
                    coordination.add(n.getLayoutY());
                    classes.add(n.getUserData().toString());
                    child_cardinality = new_aggrcomp.getChildCardinality();
                }
            }
            int type = new_aggrcomp.getType();
            Double x1 = coordination.get(0);
            Double y1 = coordination.get(1);
            Double x2 = coordination.get(2);
            Double y2 = coordination.get(3);
            int shift1 = relationship_count.get(classes.get(0)) * 10;
            int shift2 = relationship_count.get(classes.get(1)) * 10;
            Group line = this.createLine(x1, y1, x2, y2, shift1, shift2, type,child_cardinality, "");
            class_pane.getChildren().add(line);
        }
        return class_pane;
    }
    public Group createLine(Double x1, Double y1, Double x2, Double y2, int shift1, int shift2, int type, String cardinality1, String cardinality2){
        Line line = new Line();
        if ((x1.compareTo(x2) == 0) && (y1 < y2)) {
            x1 += 100 - shift1;
            y1 += 100;
            x2 = x1;
        }
        if ((x1.compareTo(x2) == 0) && (y1 > y2)) {
            x1 += 100 - shift1;
            y2 += 100;
            x2 = x1;
        }
        if ((x1 < x2) && (y1.compareTo(y2) == 0)) {
            x1 += 200;
            y1 += 50 - shift1;
            y2 = y1;
        }
        if ((x1 > x2) && (y1.compareTo(y2) == 0)) {
            x2 += 200;
            y1 += 50 - shift1;
            y2 = y1;
        }
        if ((x1 < x2) && (y1 < y2)) {
            x1 += 200 - shift1;
            y1 += 100;
            x2 += shift2;
        }
        if ((x1 > x2) && (y1 > y2)) {
            y2 += 100;
            x1 += shift2;
        }
        if ((x1 > x2) && (y1 < y2)) {
            System.out.println("6");
            x2 += 200 - shift2;
            y1 += 100;
            x1 += shift1;
        }
        if ((x1 < x2) && (y1 > y2)) {
            x1 += 200 - shift1;
            y2 += 100;
            x2 += shift2;
        }
        Group arrow_line = new Group();
        Polygon polygon = new Polygon();
        Double bx = x1;
        Double by = y1;

        if (type == 0) {
            Double length = sqrt(pow(x1-x2,2)+pow(y1-y2,2));
            Double partition = 20/length;
            Double sx = x2 - x1;
            Double sy = y2 - y1;
            bx = x1 + (sx * partition);
            by = y1 + (sy * partition);
            Double middlex = (x1 + bx) / 2;
            Double middley = (y1 + by) / 2;
            Double nx = - sy;
            Double ny = sx;
            Double v1x = middlex - (nx * (partition/3));
            Double v1y = middley - (ny * (partition/3));
            Double v2x = middlex + (nx * (partition/3));
            Double v2y = middley + (ny * (partition/3));
            polygon.getPoints().addAll(x1,y1,v1x,v1y,bx,by,v2x,v2y);
            polygon.setStrokeWidth(1);
            polygon.setStroke(Color.BLACK);
            polygon.setFill(Color.WHITE.deriveColor(0, 1.2, 1, 0));
            Double cx = x2 - (sx * (partition-0.1));
            Double cy = y2 - (sy * (partition-0.1));
            Text t1 = new Text(cx, cy, cardinality1);
            t1.setFont(new Font(10));
            line.setStartX(bx);
            line.setStartY(by);
            line.setEndX(x2);
            line.setEndY(y2);
            arrow_line.getChildren().addAll(polygon,line,t1);
            return arrow_line;
        }
        if (type == 1){
            Double length = sqrt(pow(x1-x2,2)+pow(y1-y2,2));
            Double partition = 20/length;
            Double sx = x2 - x1;
            Double sy = y2 - y1;
            bx = x1 + (sx * partition);
            by = y1 + (sy * partition);
            Double middlex = (x1 + bx) / 2;
            Double middley = (y1 + by) / 2;
            Double nx = - sy;
            Double ny = sx;
            Double v1x = middlex - (nx * (partition/3));
            Double v1y = middley - (ny * (partition/3));
            Double v2x = middlex + (nx * (partition/3));
            Double v2y = middley + (ny * (partition/3));
            polygon.getPoints().addAll(x1,y1,v1x,v1y,bx,by,v2x,v2y);
            polygon.setStrokeWidth(1);
            polygon.setStroke(Color.BLACK);
            polygon.setFill(Color.BLACK);
            Double cx = x2 - (sx * (partition-0.1));
            Double cy = y2 - (sy * (partition-0.1));
            Text t1 = new Text(cx, cy, cardinality1);
            t1.setFont(new Font(10));
            line.setStartX(bx);
            line.setStartY(by);
            line.setEndX(x2);
            line.setEndY(y2);
            arrow_line.getChildren().addAll(polygon,line,t1);
            return arrow_line;
        }
        if(type == 2){
            Double length = sqrt(pow(x1-x2,2)+pow(y1-y2,2));
            Double partition = 20/length;
            Double sx = x2 - x1;
            Double sy = y2 - y1;
            bx = x1 + (sx * partition);
            by = y1 + (sy * partition);
            Double nx = - sy;
            Double ny = sx;
            Double v1x = bx - (nx * (partition/3));
            Double v1y = by - (ny * (partition/3));
            Double v2x = bx + (nx * (partition/3));
            Double v2y = by + (ny * (partition/3));
            polygon.getPoints().addAll(x1,y1,v1x,v1y,v2x,v2y);
            polygon.setStrokeWidth(1);
            polygon.setStroke(Color.BLACK);
            polygon.setFill(Color.WHITE.deriveColor(0, 1.2, 1, 0));
        }
        if(type == 3){
            //Double tmpx=x1;
            //Double tmpy=y1;
            //x1=x2;y1=y2;x2=tmpx;y2=tmpy;
            Double length = sqrt(pow(x1-x2,2)+pow(y1-y2,2));
            Double partition = 20/length;
            Double sx = x2 - x1;
            Double sy = y2 - y1;
            bx = x1 + (sx * partition);
            by = y1 + (sy * partition);
            Double nx = - sy;
            Double ny = sx;
            Double v1x = bx - (nx * (partition/3));
            Double v1y = by - (ny * (partition/3));
            Double v2x = bx + (nx * (partition/3));
            Double v2y = by + (ny * (partition/3));
            polygon.getPoints().addAll(x1,y1,v1x,v1y,v2x,v2y);
            polygon.setStrokeWidth(1);
            polygon.setStroke(Color.BLACK);
            polygon.setFill(Color.WHITE.deriveColor(0, 1.2, 1, 0));
        }
        if (type==4){
            Double length = sqrt(pow(x1-x2,2)+pow(y1-y2,2));
            Double partition = 5/length;
            Double sx = x2 - x1;
            Double sy = y2 - y1;
            bx = x1 + (sx * partition);
            by = y1 + (sy * partition);
            Double cx = x2 - (sx * (partition+0.06));
            Double cy = y2 - (sy * (partition+0.06));
            Text t1 = new Text(bx, by, cardinality1);
            t1.setFont(new Font(10));
            Text t2 = new Text(cx, cy, cardinality2);
            t2.setFont(new Font(10));
            line.setStartX(x1);
            line.setStartY(y1);
            line.setEndX(x2);
            line.setEndY(y2);
            arrow_line.getChildren().addAll(t1,t2,line);
            return arrow_line;
        }

        line.setStartX(bx);
        line.setStartY(by);
        line.setEndX(x2);
        line.setEndY(y2);
        arrow_line.getChildren().addAll(polygon,line);
        return arrow_line;
    }

    /*
    public void createPolygon(){
        Double length = sqrt(pow(x1-x2,2)+pow(y1-y2,2));
        Double partition = 20/length;
        Double sx = x2 - x1;
        Double sy = y2 - y1;
        Double bx = x1 + (sx * partition);
        Double by = y1 + (sy * partition);
        Double middlex = (x1 + bx) / 2;
        Double middley = (y1 + by) / 2;
        Double nx = - sy;
        Double ny = sx;
        Double v1x = middlex - (nx * (partition/4));
        Double v1y = middley - (ny * (partition/4));
        Double v2x = middlex + (nx * (partition/4));
        Double v2y = middley + (ny * (partition/4));
        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(x1,y1,v1x,v1y,bx,by,v2x,v2y);
        polygon.setStrokeWidth(1);
        polygon.setStroke(Color.BLACK);
        polygon.setFill(Color.WHITE.deriveColor(0, 1.2, 1, 0));
        //polygon.setFill(Color.BLACK);
        //polygon.getPoints().addAll(x1,y1,v1x,v1y,bx,by,v2x,v2y);
        line.setStartX(bx);
        line.setStartY(by);
        line.setEndX(x2);
        line.setEndY(y2);
        arrow_line.getChildren().addAll(polygon,line);
    }
     */
}
