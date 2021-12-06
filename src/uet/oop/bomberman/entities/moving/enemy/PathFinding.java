package uet.oop.bomberman.entities.moving.enemy;

//An implementation of BFS with an adjacency list.

import uet.oop.bomberman.Level;
import uet.oop.bomberman.util.Constants;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class PathFinding {

    public  Level level;
    public  HashMap<Integer, Integer> visited = new HashMap<>();

    private final int size;
    private Integer[] prev;
    public List<List<Edge>> graph;

    public PathFinding(int size, Level level) {
        this.size = size;
        this.level = level;
        this.graph = createEmptyGraph(size);
    }

    /**
     * Reconstructs the path (of nodes) from 'start' to 'end' inclusive. If the edges are unweighted
     * then this method returns the shortest path from 'start' to 'end'
     *
     * @return An array of nodes indexes of the shortest path from 'start' to 'end'. If 'start' and
     * 'end' are not connected then an empty array is returned.
     */
    public List<Integer> reconstructPath(int start, int end) {
        bfs(start);
        List<Integer> path = new ArrayList<>();
        for (Integer at = end; at != null; at = prev[at]) path.add(at);
        Collections.reverse(path);
        if (path.get(0) == start) return path;
        path.clear();
        return path;
    }

    // Perform a breadth first search on a graph a starting node 'start'.
    private void bfs(int start) {
        prev = new Integer[size];
        boolean[] visited = new boolean[size];
        Deque<Integer> queue = new ArrayDeque<>(size);

        // Start by visiting the 'start' node and add it to the queue.
        queue.offer(start);
        visited[start] = true;

        // Continue until the BFS is done.
        while (!queue.isEmpty()) {
            int node = queue.poll();
            List<Edge> edges = graph.get(node);

            // Loop through all edges attached to this node. Mark nodes as visited once they're
            // in the queue. This will prevent having duplicate nodes in the queue and speedup the BFS.
            for (Edge edge : edges) {
                if (!visited[edge.to]) {
                    visited[edge.to] = true;
                    prev[edge.to] = node;
                    queue.offer(edge.to);
                }
            }
        }
    }

    // Initialize an empty adjacency list that can hold up to n nodes.
    public  List<List<Edge>> createEmptyGraph(int n) {
        List<List<Edge>> graph = new ArrayList<>(n);
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
        return graph;
    }

    // Add a directed edge from node 'u' to node 'v' with cost 'cost'.
    public  void addDirectedEdge(List<List<Edge>> graph, int u, int v, int cost) {
        graph.get(u).add(new Edge(u, v, cost));
    }

    // Add an undirected edge between nodes 'u' and 'v'.
    public  void addUndirectedEdge(List<List<Edge>> graph, int u, int v, int cost) {
        addDirectedEdge(graph, u, v, cost);
        addDirectedEdge(graph, v, u, cost);
    }

    // Add an undirected unweighted edge between nodes 'u' and 'v'. The edge added
    // will have a weight of 1 since its intended to be unweighted.
    public  void addUnweightedUndirectedEdge(List<List<Edge>> graph, int u, int v) {
        addUndirectedEdge(graph, u, v, 1);
    }

    public void checkNode(int node_num, List<List<Edge>> graph) {
        int col;
        int row;
        if (node_num % Constants.COLUMNS == 0 && node_num != 0) {
            node_num--;
        }
        row = node_num / Constants.COLUMNS;
        col = node_num - row * Constants.COLUMNS;


//        System.out.println("Check Node " + " " + row + " " + col + " " + level.tileMap[row][col]);
//        if (level.tileMap[row][col].equals(" ")) {


            //up
            int count = 0;
            int previousNodeNum;
            previousNodeNum = node_num;
            while (row - count >= 0) {
                int nextNode = row - count;
                if (count != 0) {
                    previousNodeNum = (nextNode + 1) * Constants.COLUMNS + col;
                }
            if (nodeIsSolidBlock(previousNodeNum)) {
//                System.out.println("up " + nextNode + " " + col + " " + previousNodeNum
//                        + " " + (nextNode * Constants.COLUMNS + col));

                checkEdge(col, nextNode, previousNodeNum, graph);
            }
                count++;
            }

            //left
            count = 0;
            previousNodeNum = node_num;
            while (col - count >= 0) {
                int nextNode = col - count;
                if (count != 0) {
                    previousNodeNum = row * Constants.COLUMNS + nextNode + 1;
                }
                if (nodeIsSolidBlock(previousNodeNum)) {
//                    System.out.println("left " + " " + row + " " + nextNode + " "
//                            + previousNodeNum + " " + (row * Constants.COLUMNS + nextNode));

                    checkEdge(nextNode, row, previousNodeNum, graph);
                }
                count++;
            }

            //right
            count = 0;
            previousNodeNum = node_num;
            while (col + count < Constants.COLUMNS) {
                int nextNode = col + count;
                if (count != 0) {
                    previousNodeNum = row * Constants.COLUMNS + nextNode - 1;
                }
            if (nodeIsSolidBlock(previousNodeNum)) {
//                System.out.println("right " + " " + row + " " + nextNode + " " + previousNodeNum
//                        + " " + (row * Constants.COLUMNS + nextNode));

                checkEdge(nextNode, row, previousNodeNum, graph);
            }
                count++;
            }

            //down
            count = 0;
            previousNodeNum = node_num;
            while (row + count < Constants.ROWS - 1) {
                int nextNode = row + count;
                if (count != 0) {
                    previousNodeNum = (nextNode - 1) * Constants.COLUMNS + col;
                }

                if (nodeIsSolidBlock(previousNodeNum)) {
//                    System.out.println("down " + nextNode + " " + col + " "
//                            + previousNodeNum + " " + (nextNode * Constants.COLUMNS + col));

                    checkEdge(col, nextNode, previousNodeNum, graph);
                }
                count++;
            }
        }

    private boolean nodeIsSolidBlock(int previousNodeNum) {
        int row, col;
        if (previousNodeNum % Constants.COLUMNS == 0 && previousNodeNum != 0) {
            previousNodeNum--;
        }
        row = previousNodeNum / Constants.COLUMNS;
        col = previousNodeNum - row * Constants.COLUMNS;
        return level.tileMap[row][col].equals(" ");
    }

    private  void checkEdge(int col, int row, int from, List<List<Edge>> graph) {

        int to = row * Constants.COLUMNS + col;

//        if (to == 33 && !visited.isEmpty()) {
//            System.out.println("\t\t\t" + from + " " + to + " " + visited.containsKey(from)
//                    + " " + (visited.get(from)));
//        }
        if (!graph.get(from).isEmpty() && graph.get(from).get(0).to == to) {
            System.out.println("already have " + from + " " + visited.get(from) + " Actual to " + to);

        } else {

            System.out.println("\t\tCol & Row to insert " + row + " " + col);

            if (level.tileMap[row][col].equals(" ")) {


                System.out.println("\t\tInsert Edge " + from + " " + to);


                addUnweightedUndirectedEdge(graph, from, to);
                visited.put(from, to);
            } else {

                System.out.println("\t\tCan't Insert This " + level.tileMap[row][col] + " " + from + " " + to);

            }
        }
    }

    public String formatPath(List<Integer> path) {
        return path.stream().map(Object::toString).collect(Collectors.joining(" -> "));
    }
}
