import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.LinkedList;

public class GameLogicSuitTest {
    static GameLogic gameLogic;

    @Before
    public void preparePawnsHashMap(){
        gameLogic = new GameLogic();

        Pawns w1 = new Pawns(false, true, false, new Point(1,0));
        gameLogic.addPawnToMap(new Point(1,0), w1);
        Pawns w2 = new Pawns(false, true, false, new Point(3,0));
        gameLogic.addPawnToMap(new Point(3,0), w2);
        Pawns w3 = new Pawns(false, true, false, new Point(5,0));
        gameLogic.addPawnToMap(new Point(5,0), w3);
        Pawns w4 = new Pawns(false, true, false, new Point(7,0));
        gameLogic.addPawnToMap(new Point(7,0), w4);

        Pawns w5 = new Pawns(false, true, false, new Point(0,1));
        gameLogic.addPawnToMap(new Point(0,1), w5);
        Pawns w6 = new Pawns(false, true, false, new Point(2,1));
        gameLogic.addPawnToMap(new Point(2,1), w6);
        Pawns w7 = new Pawns(false, true, false, new Point(4,1));
        gameLogic.addPawnToMap(new Point(4,1), w7);
        Pawns w8 = new Pawns(false, true, false, new Point(6,1));
        gameLogic.addPawnToMap(new Point(6,1), w8);

        Pawns w9 = new Pawns(false, true, false, new Point(1,2));
        gameLogic.addPawnToMap(new Point(1,2), w9);
        Pawns w10 = new Pawns(false, true, false, new Point(3,2));
        gameLogic.addPawnToMap(new Point(3,2), w10);
        Pawns w11 = new Pawns(false, true, false, new Point(5,2));
        gameLogic.addPawnToMap(new Point(5,2), w11);
        Pawns w12 = new Pawns(false, true, false, new Point(7,2));
        gameLogic.addPawnToMap(new Point(7,2), w12);

        Pawns b1 = new Pawns(false, true, false, new Point(0,5));
        gameLogic.addPawnToMap(new Point(0,5), b1);
        Pawns b2 = new Pawns(false, true, false, new Point(2,5));
        gameLogic.addPawnToMap(new Point(2,5), b2);
        Pawns b3 = new Pawns(false, true, false, new Point(4,5));
        gameLogic.addPawnToMap(new Point(4,5), b3);
        Pawns b4 = new Pawns(false, true, false, new Point(6,5));
        gameLogic.addPawnToMap(new Point(6,5), b4);

        Pawns b5 = new Pawns(false, true, false, new Point(1,6));
        gameLogic.addPawnToMap(new Point(1,6), b5);
        Pawns b6 = new Pawns(false, true, false, new Point(3,6));
        gameLogic.addPawnToMap(new Point(3,6), b6);
        Pawns b7 = new Pawns(false, true, false, new Point(5,6));
        gameLogic.addPawnToMap(new Point(5,6), b7);
        Pawns b8 = new Pawns(false, true, false, new Point(7,6));
        gameLogic.addPawnToMap(new Point(7,6), b8);

        Pawns b9 = new Pawns(false, true, false, new Point(0,7));
        gameLogic.addPawnToMap(new Point(0,7), b9);
        Pawns b10 = new Pawns(false, true, false, new Point(2,7));
        gameLogic.addPawnToMap(new Point(2,7), b10);
        Pawns b11 = new Pawns(false, true, false, new Point(4,7));
        gameLogic.addPawnToMap(new Point(4,7), b11);
        Pawns b12 = new Pawns(false, true, false, new Point(6,7));
        gameLogic.addPawnToMap(new Point(6,7), b12);

        Assert.assertEquals(w9, gameLogic.getPawnByPoint(1,2));

    }
    @Test
    public void testSettingPawnAndAvaibleMovesListForHim(){
        LinkedList<Point>  avaibleMovesList = new LinkedList<Point>();
        Pawns w1 = gameLogic.getPawnByPoint(7,2);
        Assert.assertEquals(true, w1.isWhite);
        Assert.assertEquals(false, w1.isBlack());
        Assert.assertEquals(false, w1.isSuperWarrior());
        avaibleMovesList = gameLogic.getAvaibleMovesListForPawn(w1);
        Assert.assertEquals(1, avaibleMovesList.size());
        Assert.assertEquals(24,gameLogic.getAllPawnsPointList().size());
        Point avaibleMovePoint = new Point(6,3);
        Assert.assertEquals(avaibleMovePoint, avaibleMovesList.getFirst());
    }

}
