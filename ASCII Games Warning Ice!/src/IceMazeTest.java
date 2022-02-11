import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IceMazeTest {

    @Test
    public void myTest() {
        String map = "    x \n" +
                "  #   \n" +
                "   E  \n" +
                " #    \n" +
                "    # \n" +
                "S    #";

        System.out.println(IceMaze.move(IceMaze.toMap(map), new IceMaze.Coordinate(0, 0), IceMaze.Direction.RIGHT));
        System.out.println(IceMaze.move(IceMaze.toMap(map), new IceMaze.Coordinate(0, 4), IceMaze.Direction.DOWN));
    }

    @Test
    public void exmpleTests() {

        String map = "    x \n" +
                "  #   \n" +
                "   E  \n" +
                " #    \n" +
                "    # \n" +
                "S    #";
        System.out.printf("A simple spiral\n%s\n", map);
        assertEquals(List.of('u','r','d','l','u','r'), IceMaze.solve(map));

        map =   " #    \n" +
                "x   E \n" +
                "      \n" +
                "     S\n" +
                "      \n" +
                " #    ";
        System.out.printf("Ice puzzle has one-way routes\n%s\n", map);
        assertEquals(List.of('l','u','r'), IceMaze.solve(map));

        map =   "E#    \n" +
                "      \n" +
                "      \n" +
                "      \n" +
                "      \n" +
                " #   S";
        System.out.printf("The end is unreachable\n%s\n", map);
        assertEquals(null, IceMaze.solve(map));

        map =   "E#   #\n" +
                "      \n" +
                "#     \n" +
                "  #   \n" +
                " #    \n" +
                " S    ";
        System.out.printf("Tiebreak by least number of moves first\n%s\n", map);
        assertEquals(List.of('r','u','l','u'), IceMaze.solve(map));

        map =   "    E \n" +
                "     #\n" +
                "      \n" +
                "# #   \n" +
                "    # \n" +
                " #  S ";
        System.out.printf("Then by total distance traversed\n%s\n", map);
        assertEquals(List.of('l','u','r','u','r'), IceMaze.solve(map));

        map =   "E#xxx\n" +
                "x#x#x\n" +
                "x#x#x\n" +
                "x#x#x\n" +
                "xxx#S";
        System.out.printf("Covering all tiles with x should reduce the problem to simple pathfinding\n%s\n", map);
        assertEquals(List.of('u','u','u','u','l','l','d','d','d','d','l','l','u','u','u','u'), IceMaze.solve(map));

        map =   "E# \n" +
                "#S#\n" +
                " # ";
        System.out.printf("There is no escape\n%s\n", map);
        assertEquals(null, IceMaze.solve(map));

        map = " # x #      #    #   # #  x  \n" +
                "      ##x  #     x       x # \n" +
                "## #     x x #  #     x      \n" +
                "#  ### #   #     x # # # #   \n" +
                "x     #       #   ## #     # \n" +
                "  ##        #  # #  #   x#   \n" +
                " x    #  #              # #  \n" +
                "   #    # E    #x      #     \n" +
                "# #  #  # #  # #x       #    \n" +
                "    #   x ## #   x          #\n" +
                "  x     S    #x#   ##  x  #  \n" +
                "     #x   #  #  ###    #    #\n" +
                "         #   #       x#      \n" +
                "#  x    x# #   x      #      \n" +
                "  #  x xx  # #   x   x# #    \n" +
                "       #    #            #   ";

        System.out.printf("Covering all tiles with x should reduce the problem to simple pathfinding\n%s\n", map);
        assertEquals(List.of('u', 'r', 'u', 'r'), IceMaze.solve(map));

        map = "         xx# x         # E \n" +
                "#x  #   #  ##             #\n" +
                "  #### #  # ##  x#   xx ## \n" +
                "   #x   x x  x         ##  \n" +
                "# ##     # # #      #    # \n" +
                "#           #       # ##   \n" +
                "    x  # # #    # x    #   \n" +
                "#    ##    # # #x   # #    \n" +
                "  #x      #   #### #  # # #\n" +
                "#   #  # ##    # x    x    \n" +
                "  # # #     x #x#   xxS   #\n" +
                " # ###   xx# ##   # #   x  \n" +
                "      #    x ###     #     \n" +
                " #    x  #     #   #    # #\n" +
                "                 x #  #  # \n" +
                "  #      #  #      #   x   \n" +
                "     x ##  #x    x #    #  \n" +
                "### # xx#                # \n" +
                "     # x      ###  ## #  # \n" +
                " #    #     #x#          # ";

        System.out.printf("Covering all tiles with x should reduce the problem to simple pathfinding\n%s\n", map);
        System.out.println(IceMaze.solve(map));
        assertEquals(List.of('l', 'u', 'r', 'r', 'u', 'r', 'u'), IceMaze.solve(map));
    }
}
