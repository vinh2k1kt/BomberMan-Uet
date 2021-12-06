package uet.oop.bomberman.entities.moving.enemy;

public class Edge {
    int from, to, cost;

    public Edge(int from, int to, int cost) {
        this.from = from;
        this.to = to;
        this.cost = cost;
    }
}