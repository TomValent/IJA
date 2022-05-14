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
import static java.lang.Math.*;

/**
 * @author Magdalena Bellayova
 * Class with methods for printing class diagram
 * Prints classes and their relationships.
 */

public class GUI_Creator {
    private int type; // 0 - aggregation, 1 - composition, 2 - generalization, 3 - specification, 4 - association
    DraggableMaker draggableMaker = new DraggableMaker();
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
            draggableMaker.makeDraggable(container,class_pane);
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
            ArrayList<String> cardinality = new ArrayList<>();
            for (Map.Entry<UMLClass, ArrayList<String>> class_cardinality : new_assoc.getClassCardinality().entrySet()) {
                for (Node n : class_pane.getChildren()) {
                    if (class_cardinality.getKey().getName().equals(n.getUserData())) {
                        coordination.add(n.getLayoutX());
                        coordination.add(n.getLayoutY());
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
            String cardinality1 = cardinality.get(0);
            String cardinality2 = cardinality.get(1);
            Group line = this.createLine(x1, y1, x2, y2, 4, cardinality1,cardinality2);
            class_pane.getChildren().add(line);
        }
        return class_pane;
    }

    public Pane createGenspecs(Pane class_pane){
        for( GeneralizationSpecification new_genspec : classDiagram.getGenspecs()) {
            ArrayList<Double> coordination = new ArrayList<>();
            for (Node n : class_pane.getChildren()) {
                if (new_genspec.getParent().getName().equals(n.getUserData())) {
                    coordination.add(n.getLayoutX());
                    coordination.add(n.getLayoutY());
                }
            }
            for (UMLClass child: new_genspec.getChildren()) {
                for (Node n : class_pane.getChildren()) {
                    if (child.getName().equals(n.getUserData())) {
                        coordination.add(n.getLayoutX());
                        coordination.add(n.getLayoutY());
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
                Group line = this.createLine(x1, y1, x2, y2,  type,"","");
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
            String child_cardinality = "";
            for (Node n : class_pane.getChildren()) {
                if (new_aggrcomp.getParent().getName().equals(n.getUserData()))  {
                    coordination.add(n.getLayoutX());
                    coordination.add(n.getLayoutY());
                }
            }
            for (Node n : class_pane.getChildren()) {
                if (new_aggrcomp.getChild().getName().equals(n.getUserData()))  {
                    coordination.add(n.getLayoutX());
                    coordination.add(n.getLayoutY());
                    child_cardinality = new_aggrcomp.getChildCardinality();
                }
            }
            int type = new_aggrcomp.getType();
            Double x1 = coordination.get(0);
            Double y1 = coordination.get(1);
            Double x2 = coordination.get(2);
            Double y2 = coordination.get(3);

            Group line = this.createLine(x1, y1, x2, y2, type,child_cardinality, "");
            class_pane.getChildren().add(line);
        }
        return class_pane;
    }
    public Group createLine(Double x1, Double y1, Double x2, Double y2, int type, String cardinality1, String cardinality2){
        Line line = new Line();
        double height = 100;
        double width = 200;


        double help_x1 = x1;
        double help_y1 = y1;
        double help_x2 = x2;
        double help_y2 = y2;
        x2 +=  width/2;
        y2 += height/2;
        double x11 = x1+ width/2;
        double y11 =y1+height/2;
        double x22 =x2;
        double y22 = y2;

        double x3 = x1;
        double y3 = y1;
        double x4 = x1+width;
        double y4= y1;

        double help_x = x1 +  width/2;
        double help_y = y1 + height/2;
        double point1_x=0;
        double point1_y=0;
        double point2_x=0;
        double point2_y=0;
        double point3_x=0;
        double point3_y=0;
        double point4_x=0;
        double point4_y=0;

        double d = (x11-x22)*(y3-y4) - (y11-y22)*(x3-x4);
        if (d != 0) {
            point1_x = ((x3-x4)*(x11*y22-y11*x22)-(x11-x22)*(x3*y4-y3*x4))/d;
            point1_y = ((y3-y4)*(x11*y22-y11*x22)-(y11-y22)*(x3*y4-y3*x4))/d;
        }

        x3 = x1;
        y3 = y1 + height;
        x4 = x1 + width;
        y4= y1 + height;

        d = (x11-x22)*(y3-y4) - (y11-y22)*(x3-x4);
        if (d != 0) {
            point2_x = ((x3-x4)*(x11*y22-y11*x22)-(x11-x22)*(x3*y4-y3*x4))/d;
            point2_y = ((y3-y4)*(x11*y22-y11*x22)-(y11-y22)*(x3*y4-y3*x4))/d;
        }

        x3 = x1;
        y3 = y1;
        x4 = x1;
        y4= y1 +height;

        d = (x11-x22)*(y3-y4) - (y11-y22)*(x3-x4);
        if (d != 0) {
            point3_x = ((x3-x4)*(x11*y22-y11*x22)-(x11-x22)*(x3*y4-y3*x4))/d;
            point3_y = ((y3-y4)*(x11*y22-y11*x22)-(y11-y22)*(x3*y4-y3*x4))/d;
        }

        x3 = x1 + width;
        y3 = y1;
        x4 = x1 + width;
        y4= y1 + height;

        d = (x11-x22)*(y3-y4) - (y11-y22)*(x3-x4);
        if (d != 0) {
            point4_x = ((x3-x4)*(x11*y22-y11*x22)-(x11-x22)*(x3*y4-y3*x4))/d;
            point4_y = ((y3-y4)*(x11*y22-y11*x22)-(y11-y22)*(x3*y4-y3*x4))/d;
        }

        x1 = help_x;
        y1 = help_y;


        double secondpath_x = 0;
        double secondpath_y = 0;
        double path11=0;
        double path22=0;

        double path1 = sqrt((point1_y - y1) * (point1_y - y1) + (point1_x - x1) * (point1_x - x1));
        double minpath = path1;
        help_x = point1_x;
        help_y = point1_y;
        double path2 = sqrt((point2_y - y1) * (point2_y - y1) + (point2_x - x1) * (point2_x - x1));
        if (path2 == minpath){
            secondpath_x = point2_x;
            secondpath_y = point2_y;
        }
        if(path2<minpath){
            minpath = path2;
            help_x = point2_x;
            help_y = point2_y;
        }
        double path3 = sqrt((point3_y - y1) * (point3_y - y1) + (point3_x - x1) * (point3_x - x1));
        if (path3 == minpath){
            secondpath_x = point3_x;
            secondpath_y = point3_y;
        }
        if(path3<minpath){
            minpath = path3;
            help_x = point3_x;
            help_y = point3_y;
        }
        double path4 = sqrt((point4_y - y1) * (point4_y - y1) + (point4_x - x1) * (point4_x - x1));
        if (path4 == minpath){
            secondpath_x = point4_x;
            secondpath_y = point4_y;
        }
        if(path4<minpath){
            minpath = path4;
            help_x = point4_x;
            help_y = point4_y;
        }

        if (secondpath_y!=0 && secondpath_x!=0){
            path11= sqrt((help_y - y2) * (help_y - y2) + (help_x - x2) * (help_x - x2));
            path22= sqrt((secondpath_y - y2) * (secondpath_y - y2) + (secondpath_x - x2) * (secondpath_x - x2));
            if (path11 > path22){
                help_x=secondpath_x;
                help_y=secondpath_y;
            }
        }

        x1 = help_x;
        y1 = help_y;

        help_x = x2;
        help_y = y2;
        x2 -=  width/2;
        y2 -= height/2;

        x11 = help_x1+ width/2;
        y11 =help_y1+height/2;
        x22 =x2+ width/2;
        y22 = y2+ height/2;
        x3 = x2;
        y3 = y2;
        x4 = x2+width;
        y4= y2;
        point1_x=0;
        point1_y=0;
        point2_x=0;
        point2_y=0;
        point3_x=0;
        point3_y=0;
        point4_x=0;
        point4_y=0;

        d = (x11-x22)*(y3-y4) - (y11-y22)*(x3-x4);
        if (d != 0) {
            point1_x = ((x3-x4)*(x11*y22-y11*x22)-(x11-x22)*(x3*y4-y3*x4))/d;
            point1_y = ((y3-y4)*(x11*y22-y11*x22)-(y11-y22)*(x3*y4-y3*x4))/d;
        }

        x3 = x2;
        y3 = y2 + height;
        x4 = x2 + width;
        y4= y2 +height;

        d = (x11-x22)*(y3-y4) - (y11-y22)*(x3-x4);
        if (d != 0) {
            point2_x = ((x3-x4)*(x11*y22-y11*x22)-(x11-x22)*(x3*y4-y3*x4))/d;
            point2_y = ((y3-y4)*(x11*y22-y11*x22)-(y11-y22)*(x3*y4-y3*x4))/d;
        }

        x3 = x2;
        y3 = y2;
        x4 = x2;
        y4= y2 +height;

        d = (x11-x22)*(y3-y4) - (y11-y22)*(x3-x4);
        if (d != 0) {
            point3_x = ((x3-x4)*(x11*y22-y11*x22)-(x11-x22)*(x3*y4-y3*x4))/d;
            point3_y = ((y3-y4)*(x11*y22-y11*x22)-(y11-y22)*(x3*y4-y3*x4))/d;
        }

        x3 = x2 + width;
        y3 = y2;
        x4 = x2 + width;
        y4= y2 + height;

        d = (x11-x22)*(y3-y4) - (y11-y22)*(x3-x4);
        if (d != 0) {
            point4_x = ((x3-x4)*(x11*y22-y11*x22)-(x11-x22)*(x3*y4-y3*x4))/d;
            point4_y = ((y3-y4)*(x11*y22-y11*x22)-(y11-y22)*(x3*y4-y3*x4))/d;
        }
        x2 = help_x;
        y2 = help_y;

        secondpath_x =0;
        secondpath_y =0;
        path1 = sqrt((point1_y - y2) * (point1_y - y2) + (point1_x - x2) * (point1_x - x2));
        minpath = path1;
        help_x2 = point1_x;
        help_y2 = point1_y;
        path2 = sqrt((point2_y - y2) * (point2_y - y2) + (point2_x - x2) * (point2_x - x2));
        if(path2<minpath){
            minpath = path2;
            help_x2 = point2_x;
            help_y2 = point2_y;
        }
        else if (path2 == minpath){
            secondpath_x = point2_x;
            secondpath_y = point2_y;
        }
        path3 = sqrt((point3_y - y2) * (point3_y - y2) + (point3_x - x2) * (point3_x - x2));
        if(path3<minpath){
            minpath = path3;
            help_x2 = point3_x;
            help_y2 = point3_y;
        }
        else if (path3 == minpath){
            secondpath_x = point3_x;
            secondpath_y = point3_y;
        }
        path4 = sqrt((point4_y - y2) * (point4_y - y2) + (point4_x - x2) * (point4_x - x2));
        if(path4 < minpath){
            minpath = path4;
            help_x2 = point4_x;
            help_y2 = point4_y;
        }
        else if (path4 == minpath){
            secondpath_x = point4_x;
            secondpath_y = point4_y;
        }

        if (secondpath_y!=0 && secondpath_x!=0){
            path11= sqrt((help_y2 - y1) * (help_y2 - y1) + (help_x2 - x1) * (help_x2 - x1));
            path22= sqrt((secondpath_y - y1) * (secondpath_y - y1) + (secondpath_x - x1) * (secondpath_x - x1));
            if (path11 > path22){
                help_x2=secondpath_x;
                help_y2=secondpath_y;
            }
        }
        x2 = help_x2;
        y2 = help_y2;




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

}