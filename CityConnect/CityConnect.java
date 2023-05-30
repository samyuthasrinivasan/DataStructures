import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class CityConnect{
    HashMap<City, HashSet<Edge>> cityMap;
    HashSet<City> cities;
    HashSet<Edge> edges;
    City start, end; 

    public CityConnect(){
        cities = new HashSet<City>();
        ArrayList<String> cityList = new ArrayList<String>();
        edges = new HashSet<Edge>();
        cityMap = new HashMap<City, HashSet<Edge>>();
        File file = new File("City Distances.txt");
        try {
            BufferedReader input = new BufferedReader(new FileReader(file));
            String text = "";
            while((text = input.readLine()) != null) {
                String [] info = text.split(",");
                City c1 = new City(info[0]);
                City c2 = new City(info[1]);
                int distance = Integer.parseInt(info[2]);
                if(!cityList.contains(c1.getCityName())) {
                    cityList.add(c1.getCityName());
                }
                if(!cityList.contains(c2.getCityName())) {
                    cityList.add(c2.getCityName());
                }
                cities.add(c1);
                cities.add(c2);
                edges.add(new Edge(c1, c2, distance));
                edges.add(new Edge(c2, c1, distance));
                if(!cityMap.containsKey(c1)) {
                    cityMap.put(c1, new HashSet<Edge>());
                } 
                if(!cityMap.containsKey(c2)) {
                    cityMap.put(c2, new HashSet<Edge>());
                }
                cityMap.get(c1).add(new Edge(c1, c2, distance));
                cityMap.get(c2).add(new Edge(c2, c1, distance));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Vertices - Cities");
        for (City c : cities) {
            System.out.println("\t" + c.getCityName());
        }

        System.out.println("\nEdges - Connecting cities and distances between");
        for (Edge e : edges) {
            System.out.println("\t" + e);
        }

        System.out.println();
        for(int i = 0; i < cityList.size(); i++){
            for(int j = i + 1; j < cityList.size(); j++) {
                for (City city : cities) {
                    if(city.getCityName() == cityList.get(i)) {
                        start = city;
                    }
                    if(city.getCityName() == cityList.get(j)) {
                        end = city;
                    }
                }

                Graph graph = new Graph(cities, edges);
                DijkstrasAlgorithm dijkstra = new DijkstrasAlgorithm(graph);
                dijkstra.createTravelsPaths(start);
                ArrayList<City> shortestPath = dijkstra.getShortestPath(end);
                int distance = 0;

                System.out.println("Shortest path between " + start + " to " + end + ".");
                for(int k = 0; k < shortestPath.size() - 1; k++) {
                    City c1 = shortestPath.get(k);
                    City c2 = shortestPath.get(k + 1);
                    System.out.println("\t" + c1 + " to " + c2);
                    for (Edge e : cityMap.get(c1)) {
                        if(e.getStart() == c1 && e.getEnd() == c2) {
                            distance += e.getDist();
                        }
                        if(e.getStart() == c2 && e.getEnd() == c1) {
                            distance += e.getDist();
                        }
                    }
                }

                System.out.println("Distance between:" + distance + " miles\n\n");
            }
        }
    }

    public class City {
        String cityName;
        int ID;
        public City(String name){
            cityName = name;
            ID = name.hashCode();
        }

        public String getCityName() {
            return cityName;
        }

        public boolean equals(Object obj) {
            if(obj == this)
            return true;

            City other = (City)obj;
            return this.hashCode() == other.hashCode();
        }

        public int hashCode() {
            return ID;
        }

        public String toString() {
            return String.valueOf(cityName);
        }

    }

    public class Edge {
        City start, end;
        int dist, ID;
        public Edge(City start, City end, int dist){
            this.start = start;
            this.end = end;
            this.dist = dist;
            ID = start.hashCode() + end.hashCode();
        }

        public int hashCode() {
            return ID;
        }

        public City getEnd() {
            return end;
        }

        public City getStart() {
            return start;
        }

        public int getDist() {
            return dist;
        }
        
        public String toString() {
            return start + " to " + end + ": " + dist;
        }

        public boolean equals(Object obj) {
            if(obj == this)
            return true;

            Edge other = (Edge) obj;
            return this.hashCode() == other.hashCode();
        }
    }

    public class Graph {
        HashSet<City> cities;
        HashSet<Edge> edges;
        public Graph(HashSet<City> cities, HashSet<Edge> edges) {
            this.cities = cities;
            this.edges = edges;
        }

        public HashSet<City> getCities() {
            return cities;
        }

        public HashSet<Edge> getEdges() {
            return edges;
        }
    }

    public class DijkstrasAlgorithm {
        ArrayList<City> cities;
        ArrayList<Edge> edges;
        HashSet<City> visitedCities;
        HashSet<City> unvisitedCities;
        HashMap<City, City> predecessors;
        HashMap<City, Integer> distance;
        public DijkstrasAlgorithm(Graph graph){
            this.cities = new ArrayList<City>(graph.getCities());
            this.edges = new ArrayList<Edge>(graph.getEdges());
        }

        public void createTravelsPaths(City source) {
            visitedCities = new HashSet<City>();
            unvisitedCities = new HashSet<City>();
            predecessors = new HashMap<City, City>();
            distance = new HashMap<City, Integer>();

            distance.put(source, 0);
            unvisitedCities.add(source);

            while(unvisitedCities.size() > 0){
                City city = getMinimum(unvisitedCities);
                visitedCities.add(city);
                unvisitedCities.remove(city);
                findMinimalDistances(city);
            }
        }

        public HashMap<City, City> getPred() {
            return predecessors;
        }

        public void findMinimalDistances(City tempCity) {
            ArrayList<City> adjacentNodes = getNeighbors(tempCity);
            for (City targetCity : adjacentNodes) {
                if(getShortestDistance(targetCity) > getShortestDistance(tempCity)) {
                    distance.put(targetCity, getShortestDistance(tempCity) + getDistance(tempCity, targetCity));
                    predecessors.put(targetCity, tempCity);
                    unvisitedCities.add(targetCity);
                } 
            }
        }

        public int getDistance(City tempCity, City targetCity){
            for (Edge edge : edges) {
                if((edge.getStart().equals(tempCity) && edge.getEnd().equals(targetCity)) || (edge.getStart().equals(targetCity) && edge.getEnd().equals(tempCity))) {
                    return edge.getDist();
                }
            }
            throw new RuntimeException();
        }

        public ArrayList<City> getNeighbors(City tempCity){
            ArrayList<City> neighbors = new ArrayList<City>();
            for (Edge edge : edges) {
                if(edge.getStart().equals(tempCity) && !wasVisited(edge.getEnd())) {
                    neighbors.add(edge.getEnd());
                }

                if(edge.getEnd().equals(tempCity) && !wasVisited(edge.getStart())) {
                    neighbors.add(edge.getStart());
                }
            }
            return neighbors;
        }

        public City getMinimum(HashSet<City> cities){
            City minimum = null;
            for (City city : cities) {
                if(minimum == null) {
                    minimum = city;
                } else if(getShortestDistance(city) < getShortestDistance(minimum)){
                    minimum = city;
                }
            }
            return minimum;
        }

        public boolean wasVisited(City city) {
            return visitedCities.contains(city);
        }

        public int getShortestDistance(City dest) {
            Integer dist = distance.get(dest);
            if(dist == null) {
                return Integer.MAX_VALUE;
            }
            return dist;
        }

        
        public ArrayList<City> getShortestPath(City target) {
            ArrayList<City> connectingCities = new ArrayList<City>();
            City step = target;
            if(predecessors.get(step) == null) {
                return null;
            }

            connectingCities.add(step);

            while(predecessors.get(step) != null) {
                step = predecessors.get(step);
                connectingCities.add(step);
            }
            Collections.reverse(connectingCities);
            return connectingCities;
        }
        
    }
    
    public static void main(String[]args) {
        CityConnect app = new CityConnect();
    }
}