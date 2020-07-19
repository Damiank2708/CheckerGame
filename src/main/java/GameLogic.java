import java.awt.*;
import java.util.HashMap;

public class GameLogic {
    private final HashMap<Point, Pawns> pawnsHashMap = new HashMap<>();

    public void addPanwToMap(Point point, Pawns pawns){
        pawnsHashMap.put(point,pawns);
    }
}
