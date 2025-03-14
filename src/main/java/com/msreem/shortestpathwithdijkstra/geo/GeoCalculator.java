package com.msreem.shortestpathwithdijkstra.geo;

import static java.lang.Math.*;

// Utility class for calculating distances between two points
public class GeoCalculator {

    private static final double EARTH_RADIUS_KM = 6371;

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Convert from degrees to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);

        // Calculate distance
        return acos( sin(lat1)*sin(lat2) + cos(lat1)*cos(lat2)*cos(lon2-lon1) ) * EARTH_RADIUS_KM;
    }



}
