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
        gameLogic.preparePawnsInLogic();
        Pawns pawn = gameLogic.getPawnByPointFromMap(new Point( 1,2));
        Assert.assertEquals(pawn, gameLogic.getPawnByPointFromMap(new Point( 1,2)));
    }

    @Test
    public void testSettingPawnAndAvaibleMovesListForHim(){
        Pawns w1 = gameLogic.getPawnByPointFromMap(new Point (7,2));
        Assert.assertTrue(w1.isWhite);
        Assert.assertFalse(w1.isBlack());
        Assert.assertFalse(w1.isSuperWarrior());
        Assert.assertEquals(24,gameLogic.getAllPawnsPointList().size());

    }

}
