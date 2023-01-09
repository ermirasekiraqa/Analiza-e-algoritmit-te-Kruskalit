/* Algoritmi i Kruskalit para optimizimit */
public class KruskalsAlgorithm {
    private final static int V = 4; // numri i kulmeve te grafit
    private final static int[] setOfVertex = new int[V];
    private final static int INF = Integer.MAX_VALUE;

    /* find(int x) gjen bashkesine ne te cilen ndodhet kulmi x */
    private static int find(int x) {
        while (setOfVertex[x] != x) {
            x = setOfVertex[x];
        }
        return x;
    }

    /* union(int x, int y) bashkon dy kulme x dhe y */
    private static void union(int x, int y) {
        // Se pari gjejme bashkesite ne te cilat ndodhen kulmet x dhe y
        int setOfX = find(x);
        int setOfY = find(y);
        setOfVertex[setOfX] = setOfVertex[setOfY];
    }

    /* Implementimi i algoritmit te Kruskalit */
    private static void findMST(int[][] cost) {
        int minCost = 0; // kostoja fillestare e MST

        // Make subset
        for (int i = 0; i < V; i++) {
            setOfVertex[i] = i;
        }

        System.out.println("MST:");
        int edgeCount = 0;
        while (edgeCount < V - 1) {
            int min = INF, x = -1, y = -1;
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if ((find(i) != find(j)) && (cost[i][j] < min)) {
                        min = cost[i][j];
                        x = i;
                        y = j;
                    }
                }
            }

            union(x, y);
            System.out.println("Edge " + edgeCount + ": Vertices(" + x + ", " + y + "), cost: " + min);
            edgeCount++;
            minCost += min;
        }
        System.out.println("\n Minimum cost of MST: " + minCost);
    }

    public static void main(String[] args) {
        /* Krijojme grafin e meposhtem
             A---5---B
             |       |
           6 |       | 2
             |       |
             C---3---D   */
        int[][] cost = {
                {INF, 5, 6, INF},
                {5, INF, INF, 2},
                {6, INF, INF, 3},
                {INF, 2, 3, INF}
        };

        // Gjejme MST per grafin me lart
        findMST(cost);
    }
}
