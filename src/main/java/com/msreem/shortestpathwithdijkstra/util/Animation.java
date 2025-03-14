package com.msreem.shortestpathwithdijkstra.util;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

// Provide utility methods for applying animation (fade and transition) for ui controls.
public class Animation {

    // Add fade-in animation for given node during given time.
    public static void installFadeTransition(Node node, double durationInSecs) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(durationInSecs), node);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(false);
        fadeTransition.play();
    }

    // Add y-transition animation for given node during given time and from y position to another.
    public static void installTranslateYTransition(Node node, double durationInSecs, double fromY, double toY) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(durationInSecs), node);
        translateTransition.setFromY(fromY);
        translateTransition.setToY(toY);
        translateTransition.play();
    }

    // Add x-transition animation for given node during given time and from x position to another.
    public static void installTranslateXTransition(Node node, double durationInSecs, double fromX, double toX) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(durationInSecs), node);
        translateTransition.setFromX(fromX);
        translateTransition.setToX(toX);
        translateTransition.play();
    }

}
