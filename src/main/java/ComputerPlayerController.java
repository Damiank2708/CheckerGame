import javafx.scene.Node;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class ComputerPlayerController {

    private GameLogic gameLogic;
    private GameBoardController gameBoardController;

    public ComputerPlayerController(GameLogic gameLogic, GameBoardController gameBoardController){
        this.gameLogic = gameLogic;
        this.gameBoardController = gameBoardController;
    }

    public void Move(){
        LinkedList<Point> allPawnsList = gameLogic.getAllPawnsPointList();
        LinkedList<Point> allEmptyFields = gameLogic.getAllFreeFieldsList(allPawnsList);
        ArrayList<Pawns>  listOfPawnThatCanMove = gameLogic.getListOfPawnThatCanMove(QueueController.Player.WHITE);
        Random randomFrom = new Random();
        Random randomTo = new Random();
        Point moveFrom = listOfPawnThatCanMove.get(randomFrom.nextInt(listOfPawnThatCanMove.size() -1)).getPoint();
        Point moveTo = allEmptyFields.get(randomTo.nextInt(allEmptyFields.size() -1));

        if (gameLogic.isAvaibleAnyAttack(QueueController.Player.WHITE)){
            moveFrom = gameLogic.getFromAndDestAvaibleAttack(QueueController.Player.WHITE).get("FROM");
            moveTo = gameLogic.getFromAndDestAvaibleAttack(QueueController.Player.WHITE).get("DEST");
            if(moveFrom != null && moveTo != null) {
                setMoveOnBoardAndLogic(moveFrom,moveTo);
            }
            while(! gameLogic.isAvaibleAnyAttackForLastAttackerPawn(QueueController.Player.WHITE, gameLogic.getPawnByPointFromMap(moveTo))){
                moveFrom = gameLogic.nextFromAndDestAvaibleAttack(QueueController.Player.WHITE,gameLogic.getPawnByPointFromMap(moveTo)).get("FROM");
                moveTo = gameLogic.nextFromAndDestAvaibleAttack(QueueController.Player.WHITE,gameLogic.getPawnByPointFromMap(moveTo)).get("DEST");
                if(moveFrom == null && moveTo == null) {
                 break;
                }
                setMoveOnBoardAndLogic(moveFrom, moveTo);
            }
        }
        else {
            if(! gameLogic.isAvaibleMove(moveFrom, moveTo, true)){
                while (!gameLogic.isAvaibleMove(moveFrom, moveTo, true)) {
                    moveFrom = listOfPawnThatCanMove.get(randomFrom.nextInt(listOfPawnThatCanMove.size() -1)).getPoint();
                    moveTo = allEmptyFields.get(randomTo.nextInt(allEmptyFields.size() -1));
                    if(moveFrom == null && moveTo == null) {
                        break;
                    }
                }
            }
            if(moveFrom != null && moveTo != null) {
                setMoveOnBoardAndLogic(moveFrom,moveTo);
            }
        }
    }

    private void setMoveOnBoardAndLogic(Point moveFrom, Point moveTo){
        if(! gameLogic.isAvaibleMove(moveFrom,moveTo,true)){
            Move();
        }
        gameLogic.isAvaibleMove(moveFrom,moveTo,false);
        gameLogic.changePointOfPawnOnMapAndObjectInLogic(moveFrom,moveTo);
        gameLogic.fieldListPointToClear.add(moveFrom);
        gameBoardController.setImageFieldEmptyByFieldListPointToClear();
        if ( gameLogic.transformPawnToSuperWarriorIfIsTime(moveTo) ){
            gameBoardController.transformToSuperWarrior(moveTo);
        }
        else{
            gameBoardController.setImagePawnOnFieldByPoint(moveTo, QueueController.Player.WHITE);
        }
    }



}
