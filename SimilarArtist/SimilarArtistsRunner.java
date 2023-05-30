import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class SimilarArtistsRunner {
    HashMap<Artist, HashSet<Edge>> artistMap;
    Artist start, end;
    Graph graph;
    Stack<Artist> currentPath;
    HashSet<Artist> visited;
    public SimilarArtistsRunner(){
        artistMap = new HashMap<Artist, HashSet<Edge>>();
        graph = new Graph();
        File file = new File("SimilarArtists.txt");
        try {
            BufferedReader input = new BufferedReader(new FileReader(file));
            String text;
            while((text = input.readLine()) != null) {
                String [] info = text.split(",");
                Artist a1 = new Artist(info[0]);
                Artist a2 = new Artist(info[1]);
                graph.addArtist(a1);
                graph.addArtist(a2);
                graph.addEdge(a1, a2);
                graph.addEdge(a2, a1);
                if(!artistMap.containsKey(a1)) {
                    artistMap.put(a1, new HashSet<Edge>());
                }
                if(!artistMap.containsKey(a2)) {
                    artistMap.put(a2, new HashSet<Edge>());
                }
                artistMap.get(a1).add(new Edge(a1, a2));
                artistMap.get(a2).add(new Edge(a2, a1));
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        System.out.println("Edges -- Connecting artists with similar");
        for (Edge edge : graph.getEdges()) {
            System.out.println("\t" + edge);
        }

        for (Artist startingArtist : graph.getArtists()) {
            System.out.println(startingArtist);
            for (Artist endArtist : graph.getArtists()) {
                if(!startingArtist.equals(endArtist)) {
                    currentPath = new Stack<Artist>();
                    visited = new HashSet<Artist>();
                    dft(startingArtist, endArtist);
                }
            }
        }
    }

    public void dft(Artist curr, Artist dest) {
        currentPath.push(curr);
        visited.add(curr);
        if(curr.equals(dest)) {
            printCurrentPath();
        } else {
            for (Edge edge : graph.getEdges()) {
                Artist art = edge.getArtist();
                Artist sim = edge.getSimilar();
                if(visited.contains(art) && !visited.contains(sim)) {
                    dft(sim, dest);
                }
                if(visited.contains(sim) && !visited.contains(art)) {
                    dft(art, dest);
                }
            }
        }
    }

    public void printCurrentPath(){
        String output = "";
        while(!currentPath.isEmpty()) {
            output = currentPath.pop() + output;
            if(!currentPath.isEmpty()) {
                output = " -> " + output;
            }
        }
        System.out.println("\t" + output);
    }

    public class Artist {
        String name;
        int uniqueID;
        public Artist(String name){
            this.name = name;
            uniqueID = name.hashCode();
        }
        public String getName() {
            return name;
        }
        public int getUniqueID() {
            return uniqueID;
        }
        public boolean equals(Object obj) {
            if(obj == null || getClass() != obj.getClass())
            return false;

            Artist other = (Artist) obj;
            return other.hashCode() == uniqueID;
        }
        public String toString() {
            return name;
        }
    }

    public class Edge {
        Artist artist, similar;
        int uniqueID;
        public Edge(Artist artist, Artist similar){
            this.artist = artist;
            this.similar = similar;
            uniqueID = artist.hashCode() + similar.hashCode();
        }
        public Artist getArtist() {
            return artist;
        }
        public Artist getSimilar() {
            return similar;
        }
        public int getUniqueID() {
            return uniqueID;
        }
        public String toString() {
            return artist + " is similar to " + similar;
        }
        public boolean equals(Object obj) {
            if(obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Edge other = (Edge) obj;
            return other.getUniqueID() == uniqueID;
        }
    }

    public class Graph {
        HashSet<Artist> artists;
        HashSet<Edge> edges;
        public Graph(){
            artists = new HashSet<Artist>();
            edges = new HashSet<Edge>();
        }
        public HashSet<Artist> getArtists() {
            return artists;
        }
        public HashSet<Edge> getEdges() {
            return edges;
        }
        public void addArtist(Artist a) {
            artists.add(a);
        }
        public void addEdge(Artist a, Artist b) {
            edges.add(new Edge(a, b));
        }
    }

    public static void main(String[]args) {
        SimilarArtistsRunner runner = new SimilarArtistsRunner();
    }
}