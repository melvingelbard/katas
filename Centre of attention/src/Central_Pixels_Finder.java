
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Central_Pixels_Finder extends Image {

    Map<Integer, List<Coordinate>> scores;
    static int[] scoreArray;

    public static void main(String[] args) {
        System.out.println(Coordinate.getCoordinate(7, 3));
        System.out.println(Coordinate.getCoordinate(3, 4));
        System.out.println(Coordinate.getCoordinate(46, 46));

        System.out.println(Coordinate.toFlatIndex(2, 1, 3));
        System.out.println(Coordinate.toFlatIndex(0, 3, 4));
        System.out.println(Coordinate.toFlatIndex(1, 0, 46));


        int[] image_data = {1, 1, 4, 4, 4, 4, 2, 2, 2, 2,
                1, 1, 1, 1, 2, 2, 2, 2, 2, 2,
                1, 1, 1, 1, 2, 2, 2, 2, 2, 2,
                1, 1, 1, 1, 1, 3, 2, 2, 2, 2,
                1, 1, 1, 1, 1, 3, 3, 3, 2, 2,
                1, 1, 1, 1, 1, 1, 3, 3, 3, 3};

        Central_Pixels_Finder finder = new Central_Pixels_Finder();
        finder.pixels = image_data;
        finder.width = 10;
        finder.height = 6;

        System.out.println("_____TESTING NEIGHBOURS_____");
        System.out.println(Coordinate.getCoordinate(5, finder.width).getNeighbours(finder.height, finder.width));
        System.out.println("Result:" + finder.central_pixels(5)[0]);


        GraphicalInterface ui = new GraphicalInterface(scoreArray, finder.height, finder.width);
        ui.setVisible(true);

    }

    public int[] central_pixels(int colour) {
        // Instantiate variables
        scores = new HashMap<>();
        scoreArray = new int[pixels.length];

        // Iterate through array and find blocks of pixels with same colour
        for (int index = 0; index < pixels.length; index++) {
            int pixel = pixels[index];
            if (pixel != colour)
                continue;

            Coordinate c = Coordinate.getCoordinate(index, width);
            // Give a score to 'depth-1' pixels
            if (c.isOnImageBorder(height, width) || c.hasEscapeNeighbour(pixels, height, width, colour)) {
                if (!scores.containsKey(1))
                    scores.put(1, new ArrayList<>());
                scores.get(1).add(c);
                scoreArray[c.toFlatIndex(c.h, c.w, width)] = 1;
            }
        }

        int depth = 1;
        while (scores.get(depth) != null) {
            for (Coordinate c : scores.get(depth)) {
                // Check all neighbours

                for (Coordinate neighbour: c.getNeighbours(height, width)) {
                    if (scoreArray[Coordinate.toFlatIndex(neighbour.h, neighbour.w, width)] == 0 && pixels[neighbour.toFlatIndex(neighbour.h, neighbour.w, width)] == colour) {
                        System.out.println("DEBUG: " + c + " --> " + neighbour);
                        if (!scores.containsKey(depth + 1))
                            scores.put(depth + 1, new ArrayList<>());
                        scores.get(depth + 1).add(neighbour);
                        scoreArray[neighbour.toFlatIndex(neighbour.h, neighbour.w, width)] = depth + 1;
                    }
                }
            }
            depth += 1;
        }

        // Here all the pixels should have a score
        if (scores.isEmpty())
            return new int[]{};

        List<Coordinate> coords = scores.get(scores.keySet().stream().sorted().collect(Collectors.toList()).get(scores.size()-1));
        return coords.stream().mapToInt(c -> Coordinate.toFlatIndex(c.h, c.w, width)).toArray();
    }


    public static class Coordinate {
        int h;
        int w;

        private Coordinate(int h, int w) {
            this.h = h;
            this.w = w;
        }

        public static Coordinate getCoordinate(int pos, int imageW) {
            return new Coordinate(pos / imageW, pos % imageW);
        }

        public boolean isOnImageBorder(int imageH, int imageW) {
            return h == 0 || h == imageH-1 || w == 0 || w == imageW-1;
        }

        public boolean isOutOfBounds(int imageH, int imageW) {
            return h < 0 || h >= imageH || w < 0 || w >= imageW;

        }

        public boolean hasEscapeNeighbour(int[] pixels, int imageH, int imageW, int pixelColour) {
            List<Coordinate> n = getNeighbours(imageH, imageW);
            for (Coordinate c : n) {
                int index = toFlatIndex(c.h, c.w, imageW);
                if (pixels[index] != pixelColour)
                    return true;
            }
            return false;
        }

        public List<Coordinate> getNeighbours(int imageH, int imageW) {
            List<Coordinate> n = new ArrayList<>();
            int[][] moves = new int[][]{new int[]{-1, 0}, new int[]{0, 1}, new int[]{1, 0}, new int[]{0, -1}};
            for (int i = 0; i < moves.length; i++) {
                Coordinate c = new Coordinate(h + moves[i][0], w + moves[i][1]);
                if (!c.isOutOfBounds(imageH, imageW))
                    n.add(c);
            }
            return n;
        }

        public static int toFlatIndex(int h, int w, int imageW) {
            return h * imageW + w;
        }

        @Override
        public String toString() {
            return "Coordinate{" +
                    "h=" + h +
                    ", w=" + w +
                    '}';
        }
    }
}