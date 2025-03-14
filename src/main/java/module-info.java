module com.msreem.shortestpathwithdijkstra {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.msreem.shortestpathwithdijkstra to javafx.fxml;
    exports com.msreem.shortestpathwithdijkstra;
}