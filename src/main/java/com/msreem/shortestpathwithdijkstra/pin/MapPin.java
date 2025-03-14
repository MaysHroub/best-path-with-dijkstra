package com.msreem.shortestpathwithdijkstra.pin;

import com.msreem.shortestpathwithdijkstra.graph.Vertex;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// Represent the pin used on the map
public class MapPin extends ImageView {

    private static final String DEFAULT_PIN = "/com/msreem/shortestpathwithdijkstra/images/red-pin.png";
    private static final String START_PIN = "/com/msreem/shortestpathwithdijkstra/images/blue-pin.png";
    private static final String END_PIN = "/com/msreem/shortestpathwithdijkstra/images/green-pin.png";

    private static final int PIN_SIZE = 20;


    private Vertex vertex;
    private double mapWidth, mapHeight;

    public MapPin(Vertex vertex, double mapWidth, double mapHeight) {
        this.vertex = vertex;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        initUI();
        initCoordinates();
    }

    public Vertex getVertex() {
        return vertex;
    }

    public String getCityName() {
        return vertex.getCity().getName();
    }

    private void initUI() {
        setImage(new Image(getClass().getResource(DEFAULT_PIN).toExternalForm()));
        setFitWidth(PIN_SIZE);
        setFitHeight(PIN_SIZE);
    }

    private void initCoordinates() {
        double x = convertLongitudeToX(mapWidth);
        setLayoutX(x);
        vertex.setX(x);
        double y = convertLatitudeToY(mapHeight);
        setLayoutY(y);
        vertex.setY(y);
    }

    private double convertLongitudeToX(double mapWidth) {
        // Convert lon from (-180...180) to (0...mapWidth)
        return (vertex.getCity().getLongitude() + 180) * (mapWidth / 360) - PIN_SIZE / 2;
    }

    private double convertLatitudeToY(double mapHeight) {
        // Convert latitude from (-90...90) to (0...mapHeight)
        // We subtract from map_height because y=0 is at the top
        return mapHeight - ((vertex.getCity().getLatitude() + 90) * (mapHeight / 180)) - PIN_SIZE;
    }

    public void markAsStart() {
        setImage(new Image(getClass().getResource(START_PIN).toExternalForm()));
        setEffect(null);
    }

    public void markAsEnd() {
        setImage(new Image(getClass().getResource(END_PIN).toExternalForm()));
        setEffect(null);
    }

    public void markAsHovered() {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5); // Reduce brightness (range: -1.0 to 1.0)
        setEffect(colorAdjust);
    }

    public void unmarkAsHovered() {
        setEffect(null);
    }

    public void reset() {
        setImage(new Image(getClass().getResource(DEFAULT_PIN).toExternalForm()));
        setEffect(null);
    }

    @Override
    public String toString() {
        return getCityName();
    }
}


















