import java.lang.reflect.Field;
import java.util.Random;

public class ShortestKnightPath {

    public static void main(String[] args) {
        System.out.println("Checking conversion: (a1):" + new Chess.Coordinate("a1"));
        System.out.println("Checking conversion: (c1):" + new Chess.Coordinate("c1"));
        System.out.println("Checking conversion: (f1):" + new Chess.Coordinate("f1"));
        System.out.println("Checking conversion: (f3):" + new Chess.Coordinate("f3"));
        System.out.println("Checking conversion: (f4):" + new Chess.Coordinate("f4"));
        System.out.println("Checking conversion: (f7):" + new Chess.Coordinate("f7"));
        System.out.println("Checking conversion: (f8):" + new Chess.Coordinate("f8"));
        System.out.println("Checking conversion: (f9):" + new Chess.Coordinate("f9"));
        System.out.println("Checking conversion: (g8):" + new Chess.Coordinate("g8"));

//        System.out.println("Checking moves");
        Chess.knight("a1", "c1");
        System.out.println("From (a1 - " + new Chess.Coordinate("a1") + "): " + new Chess.Coordinate("a1").getPossibleMoves());
        System.out.println("From (f4 - " + new Chess.Coordinate("f4") + "): " + new Chess.Coordinate("f4").getPossibleMoves());


        System.out.println("______________________________________________________________");
        System.out.println("Test for (a1, c1): 2 == " + Chess.knight("a1", "c1"));
        System.out.println("Test for (a1, f1): 3 == " + Chess.knight("a1", "f1"));
        System.out.println("Test for (a1, f3): 3 == " + Chess.knight("a1", "f3"));
        System.out.println("Test for (a1, f4): 4 == " + Chess.knight("a1", "f4"));
        System.out.println("Test for (a1, f7): 5 == " + Chess.knight("a1", "f7"));
        System.out.println("Test for (c4, g8): ? == " + Chess.knight("c4", "g8"));
        System.out.println("Test for (d5, h6): ? == " + Chess.knight("d5", "h6"));
        System.out.println("Test for (f6, c8): ? == " + Chess.knight("f6", "c8"));
        System.out.println("Test for (h6, h8): ? == " + Chess.knight("h6", "h8"));
        System.out.println("Test for (h1, a5): ? == " + Chess.knight("h1", "a5"));

    }
}
