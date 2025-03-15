# Path Finder using Dijkstra's Algorithm ✈️


## Description
This is a JavaFX application that implements Dijkstra's algorithm to find the best path between two cities based on:
- **Distance** (shortest route)
- **Cost** (cheapest flight)
- **Time** (fastest travel)
 
The application simulates a flight booking system for a fictitious airport, providing both visual (map-based) and textual (text area) results.


## Features
- **Real-world data** – Uses actual longitude, latitude, cost, and flight time between at least 50 capital cities.
- **Interactive map** – Users can select cities directly from the map or via a combo-box.
- **Flexible filtering** – Choose the optimal path based on distance, cost, or time.
- **Visualized results** – Displays the shortest path as lines on the map and text output.
- **Dynamic calculations** – Converts latitude/longitude to x/y coordinates for accurate city positioning.


## Input File Format
```
[Number of capitals]
Country,Capital,Latitude,Longitude
country1,captial1,lat1,long1
country2,captial2,lat2,long2
...

[Number of pairs]
Source,Destination,Time(minutes),Price($)
captial1,captial2,t1,p1
capital1,captial3,t2,p2
...
```


## Calculations
### Coordinate Conversion
latitude & longitude are converted to x/y positions on the map using the following formulas:
- Longitude to x: ` (longitude + 180) * (mapWidth / 360) `
- Latitude to y: ` (latitude + 90) * (mapHeight / 180) `

### Distance Calculation
The distance between two cities is computed using:

**` acos( sin(lat1)*sin(lat2) + cos(lat1)*cos(lat2)*cos(lon2-lon1) ) * EARTH_RADIUS_KM `** , where `EARTH_RADIUS_KM` = 6371.

Note: Longitude and Latitude values must be converted to radians first.


## Screenshots of the Program
![Start](https://github.com/user-attachments/assets/3c552532-1a8e-440e-9b6d-c6e178371a3d)

![Screenshot (23)](https://github.com/user-attachments/assets/3fd4f879-56dd-4266-9ec1-1bf95220b0e6)

![Screenshot (24)](https://github.com/user-attachments/assets/bd9a090f-7901-444a-a33e-8ddbe2af4120)

![Screenshot (25)](https://github.com/user-attachments/assets/b3aba06f-f333-4608-abc1-aed20c75da60)


## Resources
Data was taken from https://www.skyscanner.net/









