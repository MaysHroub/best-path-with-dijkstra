package com.msreem.shortestpathwithdijkstra.util;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Toast {
    
    private static Label toastLabel = new Label();

    public static void show(Pane container, String message, int durationInMillis) {
        container.getChildren().remove(toastLabel);
        toastLabel.setId("toast");
        toastLabel.setText(message);
        toastLabel.setOpacity(0);
        container.getChildren().add(toastLabel);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), toastLabel);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(500), toastLabel);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setDelay(Duration.millis(durationInMillis));

        fadeOut.setOnFinished(e -> container.getChildren().remove(toastLabel));

        fadeIn.setOnFinished(e -> fadeOut.play());
        fadeIn.play();
    }
    
}
