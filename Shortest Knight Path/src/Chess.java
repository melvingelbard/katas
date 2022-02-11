import java.util.*;

public class Chess {

    public static final List<Integer[]> coordMoves = new ArrayList<>();
    public static Set<Coordinate> bestMove = null;  // Only used for DFS

    public static int knight(String start, String  finish) {
        coordMoves.add(new Integer[]{-1, -2});
        coordMoves.add(new Integer[]{1, -2});
        coordMoves.add(new Integer[]{2, -1});
        coordMoves.add(new Integer[]{2, 1});
        coordMoves.add(new Integer[]{1, 2});
        coordMoves.add(new Integer[]{-1, 2});
        coordMoves.add(new Integer[]{-2, 1});
        coordMoves.add(new Integer[]{-2, -1});

        Coordinate startC = new Coordinate(start);
        Coordinate finishC = new Coordinate(finish);

        return breadthFirstSearch(startC, finishC);
    }

    public static int breadthFirstSearch(Coordinate init, Coordinate finish) {
        Queue<Coordinate> queue = new ArrayDeque<>();
        Set<Coordinate> pos = new HashSet<>();
        boolean[][] visited = new boolean[8][8];
        visited[init.col][init.row] = true;
        int[][] nMoves = new int[8][8];
        queue.add(init);


        while(!queue.isEmpty()) {
            Coordinate currentNode = queue.remove();

            if (currentNode.equals(finish))
                return nMoves[currentNode.col][currentNode.row];

            for (Coordinate n: currentNode.getPossibleMoves()) {
                if (!n.isOutOfBounds() && !visited[n.col][n.row]) {
                    visited[n.col][n.row] = true;
                    nMoves[n.col][n.row] = nMoves[currentNode.col][currentNode.row] + 1;
                    queue.add(n);
                }
            }
        }
        return -1;
    }


    /**
     * This is Depth-first search (recursive).
     * @param init
     * @param finish
     * @param pastPos
     */
    public static void move(Coordinate init, Coordinate finish, Set<Coordinate> pastPos) {
        pastPos.add(init);
        for (Coordinate nextPos: init.getPossibleMoves()) {
            if (bestMove != null && pastPos.size() >= bestMove.size())
                return;
            if (pastPos.size() > 5)
                return;
            if (pastPos.contains(nextPos))
                continue;
            if (nextPos.equals(finish)) {
                if (bestMove == null || bestMove.size() > pastPos.size())
                    bestMove = pastPos;
                return;
            }

            move(nextPos, finish, new HashSet<>(pastPos));
        }
    }

    public static class Coordinate {
        private int col;
        private int row;
        private Set<Coordinate> c = new HashSet<>();

        public Coordinate(String pos) {
            this.col = Integer.valueOf(pos.charAt(0) - 'a');
            this.row = Character.getNumericValue(pos.charAt(1))-1;
        }

        public Coordinate(Coordinate coord, int moveCol, int moveRow) {
            this.col = coord.col + moveCol;
            this.row = coord.row + moveRow;
        }



        List<Coordinate> getPossibleMoves() {
            List<Coordinate> newCoords = new ArrayList<>();
            for (Integer[] coord: coordMoves) {
                Coordinate newCoord = new Coordinate(this, coord[0], coord[1]);
                if (newCoord != null && !newCoord.isOutOfBounds())
                    newCoords.add(newCoord);
            }
            return newCoords;
        }

        public boolean isOutOfBounds() {
            return col < 0 || col >= 8 || row < 0 || row >= 8;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return col == that.col && row == that.row;
        }

        @Override
        public int hashCode() {
            return Objects.hash(col, row);
        }

        @Override
        public String toString() {
            return "Coordinate{" +
                    "row=" + row +
                    ", col=" + col +
                    '}';
        }
    }
}
