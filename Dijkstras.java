import java.util.Scanner;

class Dijkstras {
    int minDistance(int dist[], boolean sptSet[], int V) {
        int min = Integer.MAX_VALUE, min_index = -1;
        for (int v = 0; v < V; v++)
            if (!sptSet[v] && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }
        return min_index;
    }

    void printSolution(int dist[], int V) {
        int vertex = 65; // ASCII value for 'A'
        System.out.println("Vertex \t\t Order \t\t Distance from Source");
        for (int i = 0; i < V; i++) {
            System.out.println((char) vertex + "\t\t " + i + " \t\t " + dist[i]);
            vertex++;
        }
    }

    void dijkstra(int graph[][], int src, int V) {
        int dist[] = new int[V];
        boolean sptSet[] = new boolean[V];
        for (int i = 0; i < V; i++) {
            dist[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
        }
        dist[src] = 0;

        for (int count = 0; count < V - 1; count++) {
            int u = minDistance(dist, sptSet, V);
            sptSet[u] = true;

            for (int v = 0; v < V; v++)
                if (!sptSet[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE
                        && dist[u] + graph[u][v] < dist[v])
                    dist[v] = dist[u] + graph[u][v];
        }

        printSolution(dist, V);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of vertices:");
        int n = sc.nextInt();

        System.out.println("Enter the source vertex:");
        int src = sc.nextInt();

        int graph[][] = new int[n][n];

        System.out.println("Enter the adjacency matrix of the graph:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                graph[i][j] = sc.nextInt();
            }
        }

        Dijkstras dijkstra = new Dijkstras();
        dijkstra.dijkstra(graph, src, n);

        sc.close();
    }
}
