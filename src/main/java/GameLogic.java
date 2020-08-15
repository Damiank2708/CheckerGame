import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameLogic {
    private final HashMap<Point, Pawns> pawnsHashMap = new HashMap<>();
    private final LinkedList<Point> allAvaibleFieldToMovesList = new LinkedList<>();
    public final LinkedList<Point> fieldListPointToClear = new LinkedList<>();
    private int QuantitiOfFieldsBetween;
    private LinkedList<Pawns> pawnsListListBetweenPoints = new LinkedList<>();

    public  GameLogic(){
       createAllAvaibleFieldToMovesList();
       preparePawnsInLogic();
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
                return checkThatMoveIsToFrontDirect(oldPoint, newPoint, pawns);
            }
            else
                return QuantitiOfFieldsBetween >= 0 && pawns.isSuperWarrior();
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

    public LinkedList<Point> getAllFreeFieldsList(LinkedList<Point> allPawnsPointList) {
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
        return countOfDirect == 1;
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

    public boolean isAvaibleAnyAttack(QueueController.Player currentPlayer) {
        if(pawnsListListBetweenPoints.size() > 1){
            return false;
        }
        if(currentPlayer.equals(QueueController.Player.WHITE)){
            for (Point pointWhite: getAllWhitePawnsPointList()) {
                for(Point pointDest: getAllFreeFieldsList(getAllPawnsPointList()))
                if ( isAvaibleMove(pointWhite, pointDest, true ) ) {
                    if (pawnsListListBetweenPoints.size() == 1) {
                        return true;
                    }
                }
            }
        }
        else if(currentPlayer.equals(QueueController.Player.BLACK)){
            for (Point pointBlack: getAllBlackPawnsPointList()) {
                for(Point pointDest: getAllFreeFieldsList(getAllPawnsPointList()))
                    if ( isAvaibleMove(pointBlack, pointDest, true ) ) {
                        if (pawnsListListBetweenPoints.size() == 1) {
                            return true;
                        }
                    }
            }
        }
        return false;
    }

    public boolean isAvaibleAnyAttackForLastAttackerPawn(QueueController.Player currentPlayer, Pawns pawn) {
        if(currentPlayer.equals(QueueController.Player.WHITE)){
                for(Point pointDest: getAllFreeFieldsList(getAllPawnsPointList()))
                    if ( isAvaibleMove(pawn.getPoint(), pointDest, true ) ) {
                        if (pawnsListListBetweenPoints.size() == 1) {
                            return true;
                        }
                    }
        }
        else if(currentPlayer.equals(QueueController.Player.BLACK)){
                for(Point pointDest: getAllFreeFieldsList(getAllPawnsPointList()))
                    if ( isAvaibleMove(pawn.getPoint(), pointDest, true ) ) {
                        if (pawnsListListBetweenPoints.size() == 1) {
                            return true;
                        }
                    }
        }
        return false;
    }

    public HashMap<String,Point> nextFromAndDestAvaibleAttack(QueueController.Player currentPlayer, Pawns pawn){
        HashMap<String,Point> nextFromAndDestAvaibleAttackMap = new HashMap<>();
        if(currentPlayer.equals(QueueController.Player.WHITE)){
                for(Point pointDest: getAllFreeFieldsList(getAllPawnsPointList()))
                    if ( isAvaibleMove(pawn.getPoint(), pointDest, true ) ) {
                        if (pawnsListListBetweenPoints.size() == 1) {
                            nextFromAndDestAvaibleAttackMap.put("FROM",pawn.getPoint());
                            nextFromAndDestAvaibleAttackMap.put("DEST",pointDest);
                            return nextFromAndDestAvaibleAttackMap;
                        }
                    }
        }
        else if(currentPlayer.equals(QueueController.Player.BLACK)){
                for(Point pointDest: getAllFreeFieldsList(getAllPawnsPointList()))
                    if ( isAvaibleMove(pawn.getPoint(), pointDest, true ) ) {
                        if (pawnsListListBetweenPoints.size() == 1) {
                            nextFromAndDestAvaibleAttackMap.put("FROM",pawn.getPoint());
                            nextFromAndDestAvaibleAttackMap.put("DEST",pointDest);
                            return nextFromAndDestAvaibleAttackMap;
                        }
                    }
        }
        return nextFromAndDestAvaibleAttackMap;
    }

    public HashMap<String,Point> getFromAndDestAvaibleAttack(QueueController.Player currentPlayer){
        HashMap<String,Point> fromAndDestAvaibleAttackMap = new HashMap<>();
        if(currentPlayer.equals(QueueController.Player.WHITE)){
            for (Point pointWhite: getAllWhitePawnsPointList()) {
                for(Point pointDest: getAllFreeFieldsList(getAllPawnsPointList()))
                    if ( isAvaibleMove(pointWhite, pointDest, true ) ) {
                        if (pawnsListListBetweenPoints.size() == 1) {
                            fromAndDestAvaibleAttackMap.put("FROM",pointWhite);
                            fromAndDestAvaibleAttackMap.put("DEST",pointDest);
                            return fromAndDestAvaibleAttackMap;
                        }
                    }
            }
        }
        else if(currentPlayer.equals(QueueController.Player.BLACK)){
            for (Point pointBlack: getAllBlackPawnsPointList()) {
                for(Point pointDest: getAllFreeFieldsList(getAllPawnsPointList()))
                    if ( isAvaibleMove(pointBlack, pointDest, true ) ) {
                        if (pawnsListListBetweenPoints.size() == 1) {
                            fromAndDestAvaibleAttackMap.put("FROM",pointBlack);
                            fromAndDestAvaibleAttackMap.put("DEST",pointDest);
                            return fromAndDestAvaibleAttackMap;
                        }
                    }
            }
        }
        return fromAndDestAvaibleAttackMap;
    }


    public boolean transformPawnToSuperWarriorIfIsTime(Point destPoint){
        Pawns pawns = getPawnByPointFromMap(destPoint);
        if(pawns.isBlack() && destPoint.getY() == 0 ){
          pawns.setSuperWarrior(true);
          return true;
        }
        else if(pawns.isWhite() && destPoint.getY() == 7 ) {
          pawns.setSuperWarrior(true);
          return true;
        }
        return false;
    }

    public boolean checkThatLastMoveWasAttack(){
        return fieldListPointToClear.size() > 0;
    }

    public ArrayList<Pawns> getListOfPawnThatCanMove(QueueController.Player forColorPlayer){
        ArrayList<Pawns> listOfPawnThatCanMove = new ArrayList<>();
        if(forColorPlayer.equals(QueueController.Player.WHITE)){
            for (Point pointWhite: getAllWhitePawnsPointList()) {
                for(Point pointDest: getAllFreeFieldsList(getAllPawnsPointList()))
                    if ( isAvaibleMove(pointWhite, pointDest, true ) ) {
                       listOfPawnThatCanMove.add(getPawnByPointFromMap(pointWhite));
                    }
            }
        }
        else{
            for (Point pointBlack: getAllBlackPawnsPointList()) {
                for(Point pointDest: getAllFreeFieldsList(getAllPawnsPointList()))
                    if ( isAvaibleMove(pointBlack, pointDest, true ) ) {
                        listOfPawnThatCanMove.add(getPawnByPointFromMap(pointBlack));
                    }
            }
        }
        return listOfPawnThatCanMove;
    }

    public void createAllAvaibleFieldToMovesList(){
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

    public void preparePawnsInLogic(){

        Pawns w1 = new Pawns(false, true, false, new Point(1,0));
        addPawnToMap(w1.getPoint(), w1);
        Pawns w2 = new Pawns(false, true, false, new Point(3,0));
        addPawnToMap(w2.getPoint(), w2);
        Pawns w3 = new Pawns(false, true, false, new Point(5,0));
        addPawnToMap(w3.getPoint(), w3);
        Pawns w4 = new Pawns(false, true, false, new Point(7,0));
        addPawnToMap(w4.getPoint(), w4);

        Pawns w5 = new Pawns(false, true, false, new Point(0,1));
        addPawnToMap(w5.getPoint(), w5);
        Pawns w6 = new Pawns(false, true, false, new Point(2,1));
        addPawnToMap(w6.getPoint(), w6);
        Pawns w7 = new Pawns(false, true, false, new Point(4,1));
        addPawnToMap(w7.getPoint(), w7);
        Pawns w8 = new Pawns(false, true, false, new Point(6,1));
        addPawnToMap(w8.getPoint(), w8);

        Pawns w9 = new Pawns(false, true, false, new Point(1,2));
        addPawnToMap(w9.getPoint(), w9);
        Pawns w10 = new Pawns(false, true, false, new Point(3,2));
        addPawnToMap(w10.getPoint(), w10);
        Pawns w11 = new Pawns(false, true, false, new Point(5,2));
        addPawnToMap(w11.getPoint(), w11);
        Pawns w12 = new Pawns(false, true, false, new Point(7,2));
        addPawnToMap(w12.getPoint(), w12);

        Pawns b1 = new Pawns(true, false, false, new Point(0,5));
        addPawnToMap(b1.getPoint(),b1);
        Pawns b2 = new Pawns(true, false, false, new Point(2,5));
        addPawnToMap(b2.getPoint(), b2);
        Pawns b3 = new Pawns(true, false, false, new Point(4,5));
        addPawnToMap(b3.getPoint(), b3);
        Pawns b4 = new Pawns(true, false, false, new Point(6,5));
        addPawnToMap(b4.getPoint(), b4);

        Pawns b5 = new Pawns(true, false, false, new Point(1,6));
        addPawnToMap(b5.getPoint(), b5);
        Pawns b6 = new Pawns(true, false, false, new Point(3,6));
        addPawnToMap(b6.getPoint(), b6);
        Pawns b7 = new Pawns(true, false, false, new Point(5,6));
        addPawnToMap(b7.getPoint(), b7);
        Pawns b8 = new Pawns(true, false, false, new Point(7,6));
        addPawnToMap(b8.getPoint(), b8);

        Pawns b9 = new Pawns(true, false, false, new Point(0,7));
        addPawnToMap(b9.getPoint(), b9);
        Pawns b10 = new Pawns(true, false, false, new Point(2,7));
        addPawnToMap(b10.getPoint(), b10);
        Pawns b11 = new Pawns(true, false, false, new Point(4,7));
        addPawnToMap(b11.getPoint(),b11);
        Pawns b12 = new Pawns(true, false, false, new Point(6,7));
        addPawnToMap(b12.getPoint(), b12);
    }


    }
