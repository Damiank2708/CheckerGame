import javafx.scene.image.ImageView;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class GameLogic {
    private final HashMap<Point, Pawns> pawnsHashMap = new HashMap<>();
    private final LinkedList<Point> allAvaibleFieldToMovesList = new LinkedList<>();

    public  GameLogic(){
        allAvaibleFieldToMovesList.add( new Point(1,0) );
        allAvaibleFieldToMovesList.add( new Point(3,0) );
        allAvaibleFieldToMovesList.add( new Point(5,0) );
        allAvaibleFieldToMovesList.add( new Point(7,0) );

        allAvaibleFieldToMovesList.add( new Point(0,1) );
        allAvaibleFieldToMovesList.add( new Point(2,1) );
        allAvaibleFieldToMovesList.add( new Point(4,1) );
        allAvaibleFieldToMovesList.add( new Point(6,1) );

        allAvaibleFieldToMovesList.add( new Point(1,2) );
        allAvaibleFieldToMovesList.add( new Point(3,2) );
        allAvaibleFieldToMovesList.add( new Point(5,2) );
        allAvaibleFieldToMovesList.add( new Point(7,2) );

        allAvaibleFieldToMovesList.add( new Point(0,3) );
        allAvaibleFieldToMovesList.add( new Point(2,3) );
        allAvaibleFieldToMovesList.add( new Point(4,3) );
        allAvaibleFieldToMovesList.add( new Point(6,3) );

        allAvaibleFieldToMovesList.add( new Point(1,4) );
        allAvaibleFieldToMovesList.add( new Point(3,4) );
        allAvaibleFieldToMovesList.add( new Point(5,4) );
        allAvaibleFieldToMovesList.add( new Point(7,4) );

        allAvaibleFieldToMovesList.add( new Point(0,5) );
        allAvaibleFieldToMovesList.add( new Point(2,5) );
        allAvaibleFieldToMovesList.add( new Point(4,5) );
        allAvaibleFieldToMovesList.add( new Point(6,5) );

        allAvaibleFieldToMovesList.add( new Point(1,6) );
        allAvaibleFieldToMovesList.add( new Point(3,6) );
        allAvaibleFieldToMovesList.add( new Point(5,6) );
        allAvaibleFieldToMovesList.add( new Point(7,6) );

        allAvaibleFieldToMovesList.add( new Point(0,7) );
        allAvaibleFieldToMovesList.add( new Point(2,7) );
        allAvaibleFieldToMovesList.add( new Point(4,7) );
        allAvaibleFieldToMovesList.add( new Point(6,7) );

    }

    public void addPawnToMap(Point point, Pawns pawns){
        pawnsHashMap.put(point,pawns);
    }

    public Pawns getPawnByPoint(int column, int row){
        Point point = new Point(column, row);
        return pawnsHashMap.get(point);
    }

    public LinkedList<Point> getAvaibleMovesListForPawn(Pawns pawns) {
        LinkedList<Point>  allPawnsPointList = getAllPawnsPointList();

          return getAllFreeFieldsList(allPawnsPointList).stream()
                                  .filter(t -> (t.getX() == pawns.getPoint().getX() + 1) ||
                                               (t.getX() == pawns.getPoint().getX() - 1))
                                  .filter(t -> (t.getY() == pawns.getPoint().getY() + 1) ||
                                               (t.getY() == pawns.getPoint().getY() - 1))
                                  .collect(Collectors.toCollection(LinkedList::new));

        }

        public LinkedList<Point>  getAllPawnsPointList(){
           return pawnsHashMap.entrySet()
                    .stream()
                    .map(s -> s.getValue().getPoint())
                    .collect(Collectors.toCollection(LinkedList::new));
        }

        private LinkedList<Point> getAllFreeFieldsList(LinkedList<Point> allPawnsPointList){
            LinkedList<Point>  allFreeFieldsList = new LinkedList<>();
            for (Point pointAvaible:  allAvaibleFieldToMovesList) {

                boolean isOccupied = false;

                for(Point pointOccupied: allPawnsPointList){
                    if(pointAvaible.equals(pointOccupied)) {
                        isOccupied = true;
                        break;
                    }
                }

                if(! isOccupied){
                    allFreeFieldsList.add(pointAvaible);
                }
            }
            return  allFreeFieldsList;
        }


    }
