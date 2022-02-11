import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Kata - https://www.codewars.com/kata/58f4cc4e43251b1be6000082
 */
public class IceMaze {

    public enum Direction {
        UP(c -> new Coordinate(c.h - 1, c.w), 'u'),
        RIGHT(c -> new Coordinate(c.h, c.w + 1), 'r'),
        DOWN(c -> new Coordinate(c.h + 1, c.w), 'd'),
        LEFT(c -> new Coordinate(c.h, c.w-1), 'l');

        Function<Coordinate, Coordinate> fun;
        char c;

        Direction(Function<Coordinate, Coordinate> fun, char c) {
            this.fun = fun;
            this.c = c;
        }
    }

    public static List<Character> solve(String map) {
        char[][] board = toMap(map);
        return breadthFirstSearch(board);
    }

    private static List<Character> breadthFirstSearch(char[][] board) {
        int nBestMove = Integer.MAX_VALUE;
        Coordinate initial = getInitialPos(board);
        Coordinate bestCoordinate = null;
        if (initial == null)
            return null;

        ArrayDeque<Coordinate> queue = new ArrayDeque<>();
        initial.trail.add(initial);
        queue.add(initial);

        // Optimisation
        int[][] leastDirections = new int[board.length][board[0].length];
        for (int[] row: leastDirections) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        while (!queue.isEmpty()) {
            Coordinate current = queue.pop();
            if (board[current.h][current.w] == 'E' && (bestCoordinate == null || bestCoordinate.directions.size() >= current.directions.size())) {
                if (nBestMove > current.nMoves) {
                    bestCoordinate = current;
                    nBestMove = current.nMoves;
                }
                continue;
            }

            // Optimisation - cut short the exploration
            if (current.directions.size() <= leastDirections[current.h][current.w])
                leastDirections[current.h][current.w] = current.directions.size();
            else
                continue;

            for (Direction d: Direction.values()) {
                Coordinate next = move(board, current, d);
                if (!next.isOutOfBounds(board) && !current.trail.contains(next) && next != initial) {
                    next.trail.addAll(current.trail);
                    next.trail.add(next);
                    next.directions.addAll(current.directions);
                    next.directions.add(d.c);
                    next.nMoves = current.nMoves + current.distance(next);
                    queue.add(next);
                }
            }
        }
        return bestCoordinate == null ? null : bestCoordinate.directions;
    }

    private static Coordinate getInitialPos(char[][] board) {
        for (int line = 0; line < board.length; line++) {
            for (int row = 0; row < board[0].length; row++) {
                if (board[line][row] == 'S')
                    return new Coordinate(line, row);
            }
        }
        return null;
    }

    protected static char[][] toMap(String map) {
        List<String> lines = map.lines().collect(Collectors.toList());
        char[][] out = new char[lines.size()][lines.get(0).length()];
        for (int index = 0; index < lines.size(); index++) {
            out[index] = lines.get(index).toCharArray();
        }

        return out;
    }


    protected static Coordinate move(char[][] board, Coordinate c, Direction d) {
        Coordinate previous = c;
        Coordinate current = d.fun.apply(c);
        while (!current.isOutOfBounds(board)) {
            if (board[current.h][current.w] == '#')
                return previous;
            if (board[current.h][current.w] == 'x' || board[current.h][current.w] == 'E')
                return current;

            previous = current;
            current = d.fun.apply(previous);
        }
        return previous;
    }

    public static class Coordinate {

        int h;
        int w;
        List<Coordinate> trail = new ArrayList<>();
        List<Character> directions = new ArrayList<>();
        int nMoves = 0;

        public Coordinate(int h, int w) {
            this.h = h;
            this.w = w;
        }

        public boolean isOutOfBounds(char[][] board) {
            return h < 0 || h >= board.length || w < 0 || w >= board[0].length;
        }

        public int distance(Coordinate c) {
            return Math.abs((c.w - w) + (c.h - h));
        }

        @Override
        public String toString() {
            return "[h=" + h + "; w=" + w + "]";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return h == that.h && w == that.w;
        }

        @Override
        public int hashCode() {
            return Objects.hash(h, w);
        }

    }
}
