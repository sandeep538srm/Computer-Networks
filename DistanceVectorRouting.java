import java.util.Scanner;

public class DistanceVectorRouting {
    private static final int INFINITY = 9999;
    private int[][] distanceMatrix;
    private int numNodes;

    public DistanceVectorRouting(int numNodes) {
        this.numNodes = numNodes;
        this.distanceMatrix = new int[numNodes][numNodes];
    }

    public void initialize(int[][] initialMatrix) {
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                distanceMatrix[i][j] = initialMatrix[i][j];
            }
        }
    }

    public void updateDistanceVector() {
        for (int k = 0; k < numNodes; k++) {
            for (int i = 0; i < numNodes; i++) {
                for (int j = 0; j < numNodes; j++) {
                    if (distanceMatrix[i][k] + distanceMatrix[k][j] < distanceMatrix[i][j]) {
                        distanceMatrix[i][j] = distanceMatrix[i][k] + distanceMatrix[k][j];
                    }
                }
            }
        }
    }

    public void printDistanceMatrix() {
        System.out.println("Distance Matrix:");
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                System.out.print((distanceMatrix[i][j] == INFINITY ? "INF" : distanceMatrix[i][j]) + "\t");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of nodes: ");
        int numNodes = scanner.nextInt();

        int[][] initialMatrix = new int[numNodes][numNodes];
        System.out.println("Enter the distance matrix (use " + INFINITY + " for infinity):");
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                System.out.print("Enter the edge length form "+i+"to"+j+": ");
                initialMatrix[i][j] = scanner.nextInt();
            }
        }

        DistanceVectorRouting dvr = new DistanceVectorRouting(numNodes);
        dvr.initialize(initialMatrix);

        System.out.println("Initial Distance Matrix:");
        dvr.printDistanceMatrix();

        dvr.updateDistanceVector();

        System.out.println("\nDistance Matrix after Distance Vector Routing:");
        dvr.printDistanceMatrix();
    }
}
