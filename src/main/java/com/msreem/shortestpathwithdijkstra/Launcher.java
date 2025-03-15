package com.msreem.shortestpathwithdijkstra;

import com.msreem.shortestpathwithdijkstra.linkedlist.LinkedList;
import com.msreem.shortestpathwithdijkstra.linkedlist.Node;
import com.msreem.shortestpathwithdijkstra.table.TableEntry;
import com.msreem.shortestpathwithdijkstra.util.Animation;
import com.msreem.shortestpathwithdijkstra.dijkstra.DijkstraLogic;
import com.msreem.shortestpathwithdijkstra.graph.Graph;
import com.msreem.shortestpathwithdijkstra.graph.Vertex;
import com.msreem.shortestpathwithdijkstra.io.MapInputStream;
import com.msreem.shortestpathwithdijkstra.pin.MapPin;
import com.msreem.shortestpathwithdijkstra.util.Toast;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;

import static com.msreem.shortestpathwithdijkstra.filter.Filter.*;

public class Launcher extends Application {

    private static final int SOURCE = 0, TARGET = 1;
    private static final double MAP_WIDTH = 920, MAP_HEIGHT = 520;

    private int turn;

    private DijkstraLogic dijkstra;
    private Graph graph;
    private MapPin[] mapPins;
    private MapPin srcPin, targetPin;

    private Scene scene;
    private BorderPane startPane;
    private StackPane mainPane;
    private Pane mapPane;

    private ComboBox<Vertex> sourceCB, targetCB;
    private ComboBox<String> filterCB;

    private TextArea pathTA;
    private TextField distanceTF, costTF, timeTF;
    private Label hoveredCityNameL;


    public static void main(String[] args) {
        launch();
    }


    @Override
    public void start(Stage stage) {
        initStartPane(stage);
        initMainPane();

        scene = new Scene(startPane, 950, 500);
        scene.getStylesheets().add(getClass().getResource("/com/msreem/shortestpathwithdijkstra/styles.css").toExternalForm());
        stage.setTitle("Dijkstra's Algorithm");
        stage.setScene(scene);
        stage.show();
        stage.setFullScreen(true);
        stage.getIcons().add(new Image(getClass().getResource("/com/msreem/shortestpathwithdijkstra/images/world-pins.png").toExternalForm()));
    }

    private void initStartPane(Stage stage) {
        Label head1 = new Label("World Path");
        head1.setId("h1");
        Label head2 = new Label("Finder");
        head2.setId("h2");
        Label head3 = new Label("Optimizing Routes\nwith Dijkstra’s Algorithm.");
        head3.setId("h3");
        head3.setPadding(new Insets(0, 0, 0, 10));
        head3.setTextAlignment(TextAlignment.LEFT);

        VBox titleVB = new VBox(-30, head1, head2);
        titleVB.setAlignment(Pos.CENTER);
        VBox leftLayout = new VBox(30, titleVB, head3);
        leftLayout.setPadding(new Insets(30, 0, 0, 0));

        ImageView gpsIV = new ImageView(getClass().getResource("/com/msreem/shortestpathwithdijkstra/images/gps-phone.png").toExternalForm());
        gpsIV.setFitWidth(450);
        gpsIV.setPreserveRatio(true);

        ImageView loadIV = new ImageView(getClass().getResource("/com/msreem/shortestpathwithdijkstra/images/download.png").toExternalForm());
        loadIV.setFitWidth(25);
        loadIV.setPreserveRatio(true);

        Button loadBtn = new Button(" Load Data ");
        loadBtn.setGraphic(loadIV);
        loadBtn.setContentDisplay(ContentDisplay.RIGHT);
        loadBtn.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                    new FileChooser.ExtensionFilter("CSV Files", "*.csv")
            );
            File selectedFile = fileChooser.showOpenDialog(stage);

//            For testing purposes
//            File selectedFile = null;
//            try {
//                selectedFile = new File(getClass().getResource("/com/msreem/shortestpathwithdijkstra/data.txt").toURI());
//            } catch (URISyntaxException ex) {
//                throw new RuntimeException(ex);
//            }

            if (selectedFile == null) {
                System.out.println("No File Selected");
                return;
            }
            try {
                MapInputStream in = new MapInputStream(selectedFile);
                graph = in.readAndBuildGraph();
                dijkstra = new DijkstraLogic(graph);
                scene.setRoot(mainPane);
                fillMainScreenWithData();
            } catch (FileNotFoundException ex) {
                Toast.show(mainPane, ex.getMessage(), 2000);
            }
        });

        startPane = new BorderPane();
        startPane.setLeft(leftLayout);
        startPane.setRight(gpsIV);
        startPane.setBottom(loadBtn);
        startPane.setPadding(new Insets(80, 120, 80, 120));
        BorderPane.setAlignment(loadBtn, Pos.CENTER);

        Animation.installFadeTransition(titleVB, 1);
        Animation.installFadeTransition(head3, 2);
        Animation.installFadeTransition(gpsIV, 1);
        Animation.installFadeTransition(loadBtn, 1);
        Animation.installTranslateXTransition(titleVB, 1, titleVB.getTranslateX()-100, titleVB.getTranslateX());
        Animation.installTranslateXTransition(gpsIV, 1, gpsIV.getTranslateX()+100, gpsIV.getTranslateX());
        Animation.installTranslateYTransition(loadBtn, .8, loadBtn.getTranslateY()+50, loadBtn.getTranslateY());
    }

    private void fillMainScreenWithData() {
        sourceCB.getItems().addAll(graph.getVertices());
        targetCB.getItems().addAll(graph.getVertices());
        targetCB.getItems().addAll(graph.getVertices());
        mapPins = new MapPin[graph.getVertices().length];
        for (int i = 0; i < mapPins.length; i++) {
            mapPins[i] = new MapPin(graph.getVertices()[i], MAP_WIDTH, MAP_HEIGHT);
            mapPane.getChildren().add(mapPins[i]);
            applyClickHandler(mapPins[i]);
            applyHoverHandler(mapPins[i]);
        }
    }

    private void applyHoverHandler(MapPin mapPin) {
        mapPin.setOnMouseEntered(e -> {
            mapPin.markAsHovered();
            hoveredCityNameL.setText(mapPin.getCityName());
        });
        mapPin.setOnMouseExited(e -> {
            mapPin.unmarkAsHovered();
            hoveredCityNameL.setText("");
        });
    }

    private void applyClickHandler(MapPin mapPin) {
        mapPin.setOnMouseClicked(e -> {
            if (turn == SOURCE) {
                if (srcPin != null) srcPin.reset();  // reset previous selected
                srcPin = mapPin;
                srcPin.markAsStart();
                sourceCB.getSelectionModel().select(srcPin.getVertex());
                turn = TARGET;
            }
            else {
                if (targetPin != null) targetPin.reset();  // reset previous selected
                targetPin = mapPin;
                targetPin.markAsEnd();
                targetCB.getSelectionModel().select(targetPin.getVertex());
                turn = SOURCE;
            }
        });
    }


    private void initMainPane() {
        mainPane = new StackPane();

        Label titleL = new Label("You can select cities either from the combo-box or from the map.");
        titleL.setId("h4");

        ImageView pilotIV = new ImageView(getClass().getResource("/com/msreem/shortestpathwithdijkstra/images/pilot.png").toExternalForm());
        pilotIV.setFitWidth(40);
        pilotIV.setPreserveRatio(true);
        titleL.setGraphic(pilotIV);

        hoveredCityNameL = new Label();
        hoveredCityNameL.setId("toast");

        ImageView mapIV = new ImageView(getClass().getResource("/com/msreem/shortestpathwithdijkstra/images/world-map.jpg").toExternalForm());
        mapIV.setFitWidth(MAP_WIDTH);
        mapIV.setFitHeight(MAP_HEIGHT);
        mapPane = new Pane(mapIV, hoveredCityNameL);
        mapPane.setMaxWidth(MAP_WIDTH);
        mapPane.setMaxHeight(MAP_HEIGHT);

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> {
            resetMainPane();
            scene.setRoot(startPane);
        });

        BorderPane leftLayout = new BorderPane();
        leftLayout.setTop(titleL);
        leftLayout.setCenter(mapPane);
        leftLayout.setBottom(backBtn);

        Button runBtn = new Button(" Run ");
        runBtn.setOnAction(e -> {
            if (sourceCB.getSelectionModel().getSelectedItem() == null ||
                    targetCB.getSelectionModel().getSelectedItem() == null) {
                Toast.show(mainPane, "Please select source and target cities", 2000);
                return;
            }
            if (sourceCB.getSelectionModel().getSelectedItem() == targetCB.getSelectionModel().getSelectedItem()) {
                Toast.show(mainPane, "Please select different cities", 2000);
                return;
            }
            turn = SOURCE;
            deleteLines();
            runAlgorithm();
        });

        GridPane upperRightGP = new GridPane();
        upperRightGP.setHgap(15);
        upperRightGP.setVgap(10);
        Label srcL = new Label("Source"), targetL = new Label("Target");
        srcL.setStyle("-fx-background-color: #49bcff;");
        targetL.setStyle("-fx-background-color: #43c739;");
        upperRightGP.add(srcL, 0, 0);
        upperRightGP.add(targetL, 0, 1);
        upperRightGP.add(new Label("Filter   "), 0, 2);

        sourceCB = new ComboBox<>();
        targetCB = new ComboBox<>();
        filterCB = new ComboBox<>();

        filterCB.getItems().addAll("Distance", "Time", "Cost");
        filterCB.getSelectionModel().select(0);
        sourceCB.setMaxWidth(Double.MAX_VALUE);
        targetCB.setMaxWidth(Double.MAX_VALUE);
        filterCB.setMaxWidth(Double.MAX_VALUE);
        upperRightGP.add(sourceCB, 1, 0);
        upperRightGP.add(targetCB, 1, 1);
        upperRightGP.add(filterCB, 1, 2);
        GridPane.setHgrow(sourceCB, Priority.ALWAYS);
        GridPane.setHgrow(targetCB, Priority.ALWAYS);
        GridPane.setHgrow(filterCB, Priority.ALWAYS);

        sourceCB.setOnAction(e -> {
            if (sourceCB.getSelectionModel().getSelectedItem() == null) return;
            if (srcPin != null) srcPin.reset();  // reset previous selected
            srcPin = mapPins[sourceCB.getSelectionModel().getSelectedItem().getIndex()];
            srcPin.markAsStart();
            turn = TARGET;
        });
        targetCB.setOnAction(e -> {
            if (targetCB.getSelectionModel().getSelectedItem() == null) return;
            if (targetPin != null) targetPin.reset();  // reset previous selected
            targetPin = mapPins[targetCB.getSelectionModel().getSelectedItem().getIndex()];
            if (targetPin == srcPin) return;
            targetPin.markAsEnd();
            turn = SOURCE;
        });

        pathTA = new TextArea();
        pathTA.setWrapText(true);
        pathTA.setEditable(false);
        VBox midRightVB = new VBox(10, new Label("Path"), pathTA);

        GridPane lowerRightGP = new GridPane();
        lowerRightGP.setHgap(15);
        lowerRightGP.setVgap(10);

        Label l1 = new Label("Distance (KM)"),
                l2 = new Label("Cost (USD)"),
                l3 = new Label("Time (MIN)");
        l1.setMaxWidth(Double.MAX_VALUE);
        l2.setMaxWidth(Double.MAX_VALUE);
        l3.setMaxWidth(Double.MAX_VALUE);
        l1.setMinWidth(120);
        GridPane.setHgrow(l1, Priority.ALWAYS);
        GridPane.setHgrow(l2, Priority.ALWAYS);
        GridPane.setHgrow(l3, Priority.ALWAYS);

        distanceTF = new TextField();
        costTF = new TextField();
        timeTF = new TextField();
        distanceTF.setEditable(false);
        costTF.setEditable(false);
        timeTF.setEditable(false);
        GridPane.setHgrow(distanceTF, Priority.ALWAYS);
        GridPane.setHgrow(costTF, Priority.ALWAYS);
        GridPane.setHgrow(timeTF, Priority.ALWAYS);

        lowerRightGP.add(l1, 0, 1);
        lowerRightGP.add(distanceTF, 1, 1);
        lowerRightGP.add(l2, 0, 2);
        lowerRightGP.add(costTF, 1, 2);
        lowerRightGP.add(l3, 0, 3);
        lowerRightGP.add(timeTF, 1, 3);

        VBox rightLayout = new VBox(20, upperRightGP, runBtn, midRightVB, lowerRightGP);
        rightLayout.setAlignment(Pos.CENTER);
        rightLayout.setMaxWidth(300);

        runBtn.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(runBtn, Priority.ALWAYS);

        HBox layout = new HBox(20, leftLayout, rightLayout);
        layout.setAlignment(Pos.CENTER);

        mainPane.setPadding(new Insets(10));
        mainPane.getChildren().addAll(layout);
    }

    private void resetMainPane() {
        deleteLines();
        deletePins();
        turn = SOURCE;
        if (srcPin != null) srcPin.reset();
        if (targetPin != null) targetPin.reset();
        sourceCB.getSelectionModel().clearSelection();
        targetCB.getSelectionModel().clearSelection();
        filterCB.getSelectionModel().selectFirst();
        pathTA.setText("");
        distanceTF.setText("");
        timeTF.setText("");
        costTF.setText("");
    }

    private void deleteLines() {
        mapPane.getChildren().removeIf(node -> node instanceof Line);
    }

    private void deletePins() {
        mapPane.getChildren().removeIf(node -> node instanceof MapPin);
    }

    private void runAlgorithm() {
        if (srcPin == null || targetPin == null) return;

        String filter = filterCB.getSelectionModel().getSelectedItem();
        dijkstra.run(srcPin.getVertex(), targetPin.getVertex(), mapWithIndex(filter));

        LinkedList<Vertex> path = dijkstra.getPath();
        if (path.isEmpty()) {
            pathTA.setText("No path found.");
            return;
        }

        Node<Vertex> curr = path.getHead();
        double xDisplacement = 10, yDisplacement = 20;
        double startX = curr.getData().getX() + xDisplacement,
                startY = curr.getData().getY() + yDisplacement;

        StringBuilder strbld = new StringBuilder();
        strbld.append(curr.getData()).append(" --> ");

        int i = 1;
        curr = curr.getNext();
        while (curr != null) {
            strbld.append(curr.getData());
            if (i++ != path.length()-1) strbld.append(" --> ");
            double endX = curr.getData().getX() + xDisplacement,
                    endY = curr.getData().getY() + yDisplacement;
            Line line = new Line();
            line.setStyle("-fx-stroke: #504a3c; -fx-stroke-width: 2;");
            line.setStartX(startX);
            line.setStartY(startY);
            line.setEndX(endX);
            line.setEndY(endY);
            startX = endX; startY = endY;
            mapPane.getChildren().add(line);
            curr = curr.getNext();
        }
        pathTA.setText(strbld.toString());

        TableEntry result = dijkstra.getTableEntry(targetPin.getVertex().getIndex());
        distanceTF.setText(String.format("%.2f", result.getWeight(DISTANCE)));
        timeTF.setText(result.getWeight(TIME)+"");
        costTF.setText(result.getWeight(COST)+"");
    }

    private int mapWithIndex(String filter) {
        if (filter.equalsIgnoreCase("distance")) return DISTANCE;
        if (filter.equalsIgnoreCase("time")) return TIME;
        if (filter.equalsIgnoreCase("cost")) return COST;
        return 0;
    }

}









































