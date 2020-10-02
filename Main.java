import java.util.*;

import static java.lang.Math.abs;

class Node {
    int[][] arr;
    float f;
    int g;

    Node(int[][] arr,float f, int g) {
        this.arr = arr;
        this.f = f;
        this.g = g;
    }
}

class Node_comparator implements Comparator<Node> {
    @Override
    public int compare(Node o1, Node o2) {
        return Float.compare(o1.f, o2.f);
    }
}

public class Main {
    static int n;

    public static int[][] input() {
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        int[][] mainTable = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                mainTable[i][j] = scanner.nextInt();
            }
        }
        return mainTable;
    }

    public static float manhattanDistance(int[][] check_arr) {
        int wholeDistance = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int number = check_arr[i][j];
                int deltaX = 0, deltaY = 0;
                if (1 <= number && number <= n) {
                    deltaY = abs(i - 0);
                } else if (n + 1 <= number && number <= 2 * n) {
                    deltaY = abs(i - 1);
                } else if (2 * n + 1 <= number && number <= 3 * n) {
                    deltaY = abs(i - 2);
                } else if (3 * n + 1 <= number && number <= 4 * n) {
                    deltaY = abs(i - 3);
                }
                if (n == 4) {
                    if (number % n == 1) {
                        deltaX = abs(j - 0);
                    } else if (number % n == 2) {
                        deltaX = abs(j - 1);
                    } else if (number % n == 3) {
                        deltaX = abs(j - 2);
                    } else if (number % n == 0) {
                        deltaX = abs(j - 3);
                    }
                } else if (n == 3) {
                    if (number % n == 1) {
                        deltaX = abs(j - 0);
                    } else if (number % n == 2) {
                        deltaX = abs(j - 1);
                    } else if (number % n == 0) {
                        deltaX = abs(j - 2);
                    }
                }
                if (deltaX == n - 1)
                    deltaX = 1;
                if (deltaY == n - 1)
                    deltaY = 1;
                wholeDistance += deltaX;
                wholeDistance += deltaY;
            }
        }
        return wholeDistance;
    }

    public static void transform(int[][] a1, int[][] a2) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a1[i][j] = a2[i][j];
            }
        }
    }

    public static void moveLeft(int[][] mainTable, int i, PriorityQueue<Node> minHeap, int g) {
        int[][] junkTable = new int[n][n];
        transform(junkTable, mainTable);
        int temp = junkTable[i][0];
        for (int k = 0; k < n - 1; k++) {
            junkTable[i][k] = junkTable[i][k + 1];
        }
        junkTable[i][n - 1] = temp;
        float h = manhattanDistance(junkTable) / n;
        minHeap.add(new Node(junkTable,1 + g + h, g + 1));
    }

    public static void moveRight(int[][] mainTable, int i, PriorityQueue<Node> minHeap, int g) {
        int[][] junkTable = new int[n][n];
        transform(junkTable, mainTable);
        int temp = junkTable[i][n - 1];
        for (int k = n; k > 1; k--) {
            junkTable[i][k - 1] = junkTable[i][k - 2];
        }
        junkTable[i][0] = temp;
        float h = manhattanDistance(junkTable) / n;
        minHeap.add(new Node(junkTable,1 + g + h, g + 1));
    }

    public static void moveUp(int[][] mainTable, int i, PriorityQueue<Node> minHeap, int g) {
        int[][] junkTable = new int[n][n];
        transform(junkTable, mainTable);
        int temp = junkTable[0][i];
        for (int k = 0; k < n - 1; k++) {
            junkTable[k][i] = junkTable[k + 1][i];
        }
        junkTable[n - 1][i] = temp;
        float h = manhattanDistance(junkTable) / n;
        minHeap.add(new Node(junkTable,1 + g + h, g + 1));

    }

    public static void moveDown(int[][] mainTable, int i, PriorityQueue<Node> minHeap, int g) {
        int[][] junkTable = new int[n][n];
        transform(junkTable, mainTable);
        int temp = junkTable[n - 1][i];
        for (int k = n; k > 1; k--) {
            junkTable[k - 1][i] = junkTable[k - 2][i];
        }
        junkTable[0][i] = temp;
        float h = manhattanDistance(junkTable) / n;
        minHeap.add(new Node(junkTable,1 + g + h, g + 1 ));
    }

    public static void main(String[] args) {
        int[][] mainTable = input();
        Comparator<Node> comparator = new Node_comparator();
        PriorityQueue<Node> minHeap = new PriorityQueue<>(comparator);
        minHeap.add(new Node(mainTable,0, 0));
        Set<Integer> visited = new HashSet<>();
        while (!minHeap.isEmpty()) {
            Node node = minHeap.poll();
            mainTable = node.arr;
            int g = node.g;
            float distance = manhattanDistance(mainTable);
            if (distance == 0) {
                System.out.println(g);
                break;
            }
            if (visited.contains(Arrays.deepHashCode(mainTable))) {
                continue;
            }
            visited.add(Arrays.deepHashCode(mainTable));
            for (int i = 0; i < n; i++) {
                moveLeft(mainTable, i, minHeap, g);
                moveRight(mainTable, i, minHeap, g);
                moveUp(mainTable, i, minHeap, g);
                moveDown(mainTable, i, minHeap, g);
            }
        }
    }
}