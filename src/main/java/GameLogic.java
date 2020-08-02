import javafx.scene.image.ImageView;

import java.awt.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameLogic {
    private final HashMap<Point, Pawns> pawnsHashMap = new HashMap<>();
    private final LinkedList<Point> allAvaibleFieldToMovesList = new LinkedList<>();
    public final LinkedList<Point> fieldListPointToClear = new LinkedList<>();
    private int QuantitiOfFieldsBetween;
    private LinkedList<Pawns> pawnsListListBetweenPoints = new LinkedList<>();

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

    public Pawns getPawnByPointFromMap(Point point){
        return pawnsHashMap.get(point);
    }

    public void changePointOfPawnOnMapAndObjectInLogic(Point oldPoint, Point newPoint){
        Pawns pawn = getPawnByPointFromMap(oldPoint);
        pawnsHashMap.put(newPoint,pawnsHashMap.remove(oldPoint));
        pawn.setPoint(newPoint);
    }

    public void removePointOfPawnOnMapByPoint(Point point){
        pawnsHashMap.remove(point);
    }

    public Boolean isAvaibleMove(Point oldPoint, Point newPoint, boolean justCheck){
        Pawns pawns = getPawnByPointFromMap(oldPoint);
        pawnsListListBetweenPoints.clear();
        pawnsListListBetweenPoints = getPawnsListBetweenPoints(oldPoint, newPoint);
        setQuantitiOfFieldsBetween(oldPoint,newPoint);
        if(getPawnByPointFromMap(newPoint) != null){
            return false;
        }
        if(pawnsListListBetweenPoints.size() == 1){
            if(QuantitiOfFieldsBetween != 1 && !pawns.isSuperWarrior){
                return false;
            }
            if (pawnsListListBetweenPoints.get(0).isBlack != pawns.isBlack){
                if(! justCheck) {
                    fieldListPointToClear.add(pawnsListListBetweenPoints.get(0).point);
                    removePointOfPawnOnMapByPoint(pawnsListListBetweenPoints.get(0).point);
                }
                return true;
            }
            else{
                return false;
            }
        }
        else if(pawnsListListBetweenPoints.size() == 0){
            if(QuantitiOfFieldsBetween != 0 && !pawns.isSuperWarrior){
                return false;
            }
            else if(QuantitiOfFieldsBetween == 0){
                if(checkThatMoveIsToFrontDirect(oldPoint,newPoint, pawns) ){
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        return false;
    }

    public LinkedList<Point>  getAllPawnsPointList(){
       return pawnsHashMap.entrySet()
                .stream()
                .map(s -> s.getValue().getPoint())
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public LinkedList<Point>  getAllWhitePawnsPointList(){
        return pawnsHashMap.entrySet()
                .stream()
                .filter(s -> s.getValue().isWhite())
                .map(s -> s.getValue().getPoint())
                .collect(Collectors.toCollection(LinkedList::new));
    }
    public LinkedList<Point>  getAllBlackPawnsPointList(){
        return pawnsHashMap.entrySet()
                .stream()
                .filter(s -> s.getValue().isBlack())
                .map(s -> s.getValue().getPoint())
                .collect(Collectors.toCollection(LinkedList::new));
    }
    private LinkedList<Point> getAllFreeFieldsList(LinkedList<Point> allPawnsPointList) {
        LinkedList<Point> allFreeFieldsList = new LinkedList<>();
        for (Point pointAvaible : allAvaibleFieldToMovesList) {
            if (getPawnByPointFromMap(pointAvaible) == null) {
                allFreeFieldsList.add(pointAvaible);
            }
        }
        return allFreeFieldsList;
    }

    private LinkedList<Pawns> getPawnsListBetweenPoints(Point oldPoint, Point newPoint){
        LinkedList<Pawns>  pawnsListBetweenPoints = new LinkedList<>();
        if(getPawnByPointFromMap(newPoint) != null){
            return pawnsListBetweenPoints;
        }
        int column = oldPoint.x;
        int row = oldPoint.y;
        if(oldPoint.x > newPoint.x && oldPoint.y > newPoint.y) {
            while (column != newPoint.x || row != newPoint.y) {
                column--;
                row--;
                Pawns pawn = getPawnByPointFromMap(new Point(column, row));
                if (pawn != null) {
                    pawnsListBetweenPoints.add(pawn);
                }
                if (column < 0 || row < 0) {
                    pawnsListBetweenPoints.clear();
                    return pawnsListBetweenPoints;
                }
            }
        }
        else if(oldPoint.x > newPoint.x && oldPoint.y < newPoint.y ){
            while (column != newPoint.x || row != newPoint.y ){
                column--;
                row++;
                Pawns pawn = getPawnByPointFromMap(new Point(column, row));
                if(pawn != null){
                    pawnsListBetweenPoints.add(pawn);
                }
                if (column < 0 || row > 8) {
                    pawnsListBetweenPoints.clear();
                    return pawnsListBetweenPoints;
                }
            }
        }
        else if(oldPoint.x < newPoint.x && oldPoint.y < newPoint.y  ){
            while (column != newPoint.x || row != newPoint.y  ){
                column++;
                row++;
                Pawns pawn = getPawnByPointFromMap(new Point(column, row));
                if(pawn != null){
                    pawnsListBetweenPoints.add(pawn);
                }
                if(column > 8 || row > 8) {
                    pawnsListBetweenPoints.clear();
                    return pawnsListBetweenPoints;
                }
            }
        }
        else if(oldPoint.x < newPoint.x && oldPoint.y > newPoint.y ){
            while (column != newPoint.x || row != newPoint.y ) {
                column++;
                row--;
                Pawns pawn = getPawnByPointFromMap(new Point(column, row));
                if (pawn != null) {
                    pawnsListBetweenPoints.add(pawn);
                }
                if(column > 8 || row < 0) {
                    pawnsListBetweenPoints.clear();
                    return pawnsListBetweenPoints;
                }
            }
        }
        return pawnsListBetweenPoints;
    }

    private void setQuantitiOfFieldsBetween(Point oldPoint, Point newPoint){
        ArrayList<Integer> quantitiBeetwenPerDirect = new ArrayList<>();
        int column = oldPoint.x;
        int row = oldPoint.y;
        int i1 = 0, i2=0, i3=0 ,i4 = 0;
        if(oldPoint.x > newPoint.x && oldPoint.y > newPoint.y){
            while (column != newPoint.x || row != newPoint.y ){
                column--;
                row--;
                i1++;
                if (column < 0 || row < 0) {
                   i1 = 0;
                   break;
                }
            }
        }
        else if(oldPoint.x > newPoint.x && oldPoint.y < newPoint.y ){
            while (column != newPoint.x || row != newPoint.y ){
                column--;
                row++;
                i2++;
                if (column < 0 || row > 8) {
                    i2 = 0;
                    break;
                }
            }
        }
        else if(oldPoint.x < newPoint.x && oldPoint.y < newPoint.y  ){
            while (column != newPoint.x || row != newPoint.y ){
                column++;
                row++;
                i3++;
                if (column > 8 || row > 8) {
                    i3 = 0;
                    break;
                }
            }
        }
        else if(oldPoint.x < newPoint.x && oldPoint.y > newPoint.y  ){
            while (column != newPoint.x || row != newPoint.y ){
                column++;
                row--;
                i4++;
                if (column > 8 || row < 0) {
                    i4 = 0;
                    break;
                }
            }
        }
        quantitiBeetwenPerDirect.add(i1);
        quantitiBeetwenPerDirect.add(i2);
        quantitiBeetwenPerDirect.add(i3);
        quantitiBeetwenPerDirect.add(i4);
       if(checkThatMoveIsInOneDirect(quantitiBeetwenPerDirect)){
           QuantitiOfFieldsBetween = setQuantitiOfFieldsBetweenFromList(quantitiBeetwenPerDirect) -1;
       }
       else{
           QuantitiOfFieldsBetween = -1;
       }

    }

    private boolean checkThatMoveIsInOneDirect(ArrayList<Integer> arrayList){
        int countOfDirect = (int) IntStream.range(0, arrayList.size())
                .filter(n -> (n==0))
                .count();
        if (countOfDirect == 1){
            return true;
        }
        return  false;
    }

    private int setQuantitiOfFieldsBetweenFromList(ArrayList<Integer> arrayList){
      ArrayList<Integer>  tempList= arrayList.stream()
                 .filter(n -> n>0)
                 .collect(Collectors.toCollection(ArrayList::new));
      if (tempList.size() > 0){
          return tempList.get(0);
      }
      else{
          return 0;
      }

    }

    private boolean checkThatMoveIsToFrontDirect(Point oldPoint,Point newPoint, Pawns pawns){
        if(oldPoint.y > newPoint.y && pawns.isBlack()){
           return true;
        }
        else if(oldPoint.y > newPoint.y && pawns.isWhite()) {
            return false;
        }
        else if(oldPoint.y < newPoint.y && pawns.isWhite()) {
            return true;
        }
        else if(oldPoint.y < newPoint.y && pawns.isBlack()) {
            return false;
        }
       return false;
    }

    public boolean isAvaibleAnyAttack(Point oldPoint) {
        if(pawnsListListBetweenPoints.size() != 0){
            return false;
        }
        Pawns pawns = getPawnByPointFromMap(oldPoint);
        if(pawns.isWhite()){
            for (Point pointWhite: getAllWhitePawnsPointList()) {
                for(Point pointDest: getAllFreeFieldsList(getAllPawnsPointList()))
                if ( isAvaibleMove(pointWhite, pointDest, true ) ) {
                    if (pawnsListListBetweenPoints.size() == 1) {
                        return true;
                    }
                }
            }
        }
        else if(pawns.isBlack()){
            for (Point pointBlack: getAllBlackPawnsPointList()) {
                for(Point pointDest: getAllFreeFieldsList(getAllPawnsPointList()))
                    if ( isAvaibleMove(pointBlack, pointDest, true ) ) {
                        if (pawnsListListBetweenPoints.size() == 1) {
                            System.out.println("Avaible AttackpointBlack: "+pointBlack+" pointDest:"+pointDest);
                            return true;
                        }
                    }
            }
        }
        return false;
    }



    }
