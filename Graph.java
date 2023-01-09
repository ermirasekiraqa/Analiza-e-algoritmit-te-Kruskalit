/* Algoritmi i Kruskalit pas optimizimit */
import java.util.Arrays;
public class Graph {

    // Klase e cila reprezenton nje brinje ne graf
    private static class Edge implements Comparable<Edge> {
        int src, dest, weight;

        // Funksioni krahasues i cili perdoret per te sortuar brinjet ne baze te peshes se tyre
        public int compareTo(Edge compareEdge) {
            return this.weight - compareEdge.weight;
        }
    }

    // Klase e cila reprezenton nje subset
    private static class subset {
        int parent, rank;
    }

    int V, E; // Numri i kulmeve dhe brinjeve, perkatesisht
    Edge[] edge; // vargu i cili permban te gjitha brinjet

    // Krijojme nje graf me v - kulme dhe e - brinje
    Graph(int v, int e) {
        V = v;
        E = e;
        edge = new Edge[E];
        for (int i = 0; i < e; ++i) {
            edge[i] = new Edge();
        }
    }

    /* Path compression */
    int find(subset[] subsets, int i) {
        // gjej rrenjen dhe beje rrenjen prind te i-se
        if (subsets[i].parent != i) {
            subsets[i].parent = find(subsets, subsets[i].parent);
        }
        return subsets[i].parent;
    }

    // Union by rank
    void Union(subset[] subsets, int x, int y)
    {
        int xRoot = find(subsets, x);
        int yRoot = find(subsets, y);

        // Bashko pemen me rank me te vogel tek ajo me rank me te madh
        if (subsets[xRoot].rank < subsets[yRoot].rank) {
            subsets[xRoot].parent = yRoot;
        }
        else if (subsets[xRoot].rank > subsets[yRoot].rank) {
            subsets[yRoot].parent = xRoot;
        }

        // Nese te dy pemet kane rank te njejte, beje njeren rrenje dhe rrit rankun per 1
        else {
            subsets[yRoot].parent = xRoot;
            subsets[xRoot].rank++;
        }
    }

    // Gjej MST duke perdorur algoritmin e Kruskalit
    void optimizedKruskalMST() {
        // Ketu ruajme MST
        Edge[] result = new Edge[V];

        int e = 0;

        for (int i = 0; i < V; ++i) {
            result[i] = new Edge();
        }

        // Hapi 1: Sorto brinjet
        Arrays.sort(edge);

        // Krijo subsets
        subset[] subsets = new subset[V];
        for (int i = 0; i < V; ++i) {
            subsets[i] = new subset();
        }
        for (int v = 0; v < V; ++v) {
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }

        int i = 0;
        while (e < V - 1) {
            // Hapi2: Merr brinjen me peshen me te vogel
            Edge next_edge = edge[i++];

            int x = find(subsets, next_edge.src);
            int y = find(subsets, next_edge.dest);

            // Nese perfshirja e kesaj brinje nuk formon cikel, perfshije ne rezultat dhe rrite indeksin i per 1
            if (x != y) {
                result[e++] = next_edge;
                Union(subsets, x, y);
            }
        }

        // Shfaq MST-ne e formuar
        System.out.println("Following are the edges in " + "the constructed MST");
        int minCost = 0;
        for (i = 0; i < e; ++i) {
            System.out.println(result[i].src + " -- " + result[i].dest + " == " + result[i].weight);
            minCost += result[i].weight;
        }
        System.out.println("Minimum Cost Spanning Tree " + minCost);
    }

    public static void main(String[] args)
    {
        /* Krijojme grafin e meposhtem
             A---5---B
             |       |
           6 |       | 2
             |       |
             C---3---D   */
        int V = 4; // Numri i kulmeve ne graf
        int E = 4; // Numri i brinjeve ne graf
        Graph graph = new Graph(V, E);

        // shto brinjen 0-1
        graph.edge[0].src = 0;
        graph.edge[0].dest = 1;
        graph.edge[0].weight = 5;

        // shto brinjen 0-2
        graph.edge[1].src = 0;
        graph.edge[1].dest = 2;
        graph.edge[1].weight = 6;

        // shto brinjen 1-3
        graph.edge[2].src = 1;
        graph.edge[2].dest = 3;
        graph.edge[2].weight = 2;

        // shto brinjen 2-3
        graph.edge[3].src = 2;
        graph.edge[3].dest = 3;
        graph.edge[3].weight = 3;

        // Gjejme MST per grafin me lart
        graph.optimizedKruskalMST();
    }
}

