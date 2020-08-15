import java.awt.*;

public class QueueController {
    public enum Player{
        BLACK,WHITE
    }
    private Player currentPlayer;

    public QueueController(){
        currentPlayer = Player.BLACK;
    }

    public boolean checkColorPawnDragAndCompareWithCurrentPlayer(Player colorOfDragPawn){
        return currentPlayer == colorOfDragPawn;
    }

    public Player getColorByPoint(Point point, GameLogic gameLogic){
        if (gameLogic.getPawnByPointFromMap(point).isWhite()){
            return Player.WHITE;
        }
        else{
            return Player.BLACK;
        }
    }

    public void setNextPlayer(){
        if(currentPlayer.equals(Player.BLACK)){
            currentPlayer = Player.WHITE;
        }
        else{
            currentPlayer = Player.BLACK;
        }
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
