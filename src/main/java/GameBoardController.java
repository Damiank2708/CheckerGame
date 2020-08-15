import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;

import java.awt.*;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameBoardController {

    private Image imageBoard= new Image("/board.png");
    private Image whitePawnImage = new Image("/WhitePawn.png", 75, 75,false, false);
    private Image blackPawnImage = new Image("/blackPawn.png", 75, 75,false, false);
    private Image superWhitePawnImage = new Image("/superWhitePawn.png", 75, 75,false, false);
    private Image superBlackPawnImage = new Image("/superblackPawn.png", 75, 75,false, false);
    private Image greenBackgroundImage = new Image("/GreenBackgroundImage.png", 75, 75,false, false);
    private Image emptyImage = new Image("/EmptyImage.png", 75, 75,false, false);
    private Background background;
    private GridPane gridPane;
    private GameLogic gameLogic;
    private QueueController queueController;
    public ComputerPlayerController computerPlayerController;

    public GameBoardController(GameLogic gameLogic){
        gridPane = new GridPane();
        this.gameLogic = gameLogic;
        this.queueController = new QueueController();
        this.computerPlayerController = new ComputerPlayerController(gameLogic, this);
    }

    public void prepareBackGround(){
        BackgroundSize backgroundSize = new BackgroundSize(1200, 1200, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(imageBoard, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        background = new Background(backgroundImage);
    }

    public void prepareGridPane(){
        gridPane.setBackground(background);
        gridPane.setHgap(1);
        gridPane.setVgap(1);
        gridPane.setGridLinesVisible(false);
        gridPane.setPadding(new Insets(0, 0, 0, 0));
    }

    public void preparePawnsOnBoard(){
        gridPane.add(new ImageView(emptyImage),0,0);
        gridPane.add(new ImageView(whitePawnImage),1,0);
        gridPane.add(new ImageView(emptyImage),2,0);
        gridPane.add(new ImageView(whitePawnImage), 3, 0);
        gridPane.add(new ImageView(emptyImage),4,0);
        gridPane.add(new ImageView(whitePawnImage), 5, 0);
        gridPane.add(new ImageView(emptyImage),6,0);
        gridPane.add(new ImageView(whitePawnImage), 7, 0);

        gridPane.add(new ImageView(whitePawnImage),0,1);
        gridPane.add(new ImageView(emptyImage),1,1);
        gridPane.add(new ImageView(whitePawnImage),2,1);
        gridPane.add(new ImageView(emptyImage), 3, 1);
        gridPane.add(new ImageView(whitePawnImage),4,1);
        gridPane.add(new ImageView(emptyImage), 5, 1);
        gridPane.add(new ImageView(whitePawnImage),6,1);
        gridPane.add(new ImageView(emptyImage), 7, 1);

        gridPane.add(new ImageView(emptyImage),0,2);
        gridPane.add(new ImageView(whitePawnImage),1,2);
        gridPane.add(new ImageView(emptyImage),2,2);
        gridPane.add(new ImageView(whitePawnImage), 3, 2);
        gridPane.add(new ImageView(emptyImage),4,2);
        gridPane.add(new ImageView(whitePawnImage), 5, 2);
        gridPane.add(new ImageView(emptyImage),6,2);
        gridPane.add(new ImageView(whitePawnImage), 7, 2);

        gridPane.add(new ImageView(greenBackgroundImage),0,3);
        gridPane.add(new ImageView(emptyImage),1,3);
        gridPane.add(new ImageView(greenBackgroundImage),2,3);
        gridPane.add(new ImageView(emptyImage), 3, 3);
        gridPane.add(new ImageView(greenBackgroundImage),4,3);
        gridPane.add(new ImageView(emptyImage), 5, 3);
        gridPane.add(new ImageView(greenBackgroundImage),6,3);
        gridPane.add(new ImageView(emptyImage), 7, 3);

        gridPane.add(new ImageView(emptyImage),0,4);
        gridPane.add(new ImageView(greenBackgroundImage),1,4);
        gridPane.add(new ImageView(emptyImage),2,4);
        gridPane.add(new ImageView(greenBackgroundImage), 3, 4);
        gridPane.add(new ImageView(emptyImage),4,4);
        gridPane.add(new ImageView(greenBackgroundImage), 5, 4);
        gridPane.add(new ImageView(emptyImage),6,4);
        gridPane.add(new ImageView(greenBackgroundImage), 7, 4);

        gridPane.add(new ImageView(blackPawnImage),0,5);
        gridPane.add(new ImageView(emptyImage),1,5);
        gridPane.add(new ImageView(blackPawnImage),2,5);
        gridPane.add(new ImageView(emptyImage), 3, 5);
        gridPane.add(new ImageView(blackPawnImage),4,5);
        gridPane.add(new ImageView(emptyImage), 5, 5);
        gridPane.add(new ImageView(blackPawnImage),6,5);
        gridPane.add(new ImageView(emptyImage), 7, 5);

        gridPane.add(new ImageView(emptyImage),0,6);
        gridPane.add(new ImageView(blackPawnImage),1,6);
        gridPane.add(new ImageView(emptyImage),2,6);
        gridPane.add(new ImageView(blackPawnImage), 3, 6);
        gridPane.add(new ImageView(emptyImage),4,6);
        gridPane.add(new ImageView(blackPawnImage), 5, 6);
        gridPane.add(new ImageView(emptyImage),6,6);
        gridPane.add(new ImageView(blackPawnImage), 7, 6);

        gridPane.add(new ImageView(blackPawnImage),0,7);
        gridPane.add(new ImageView(emptyImage),1,7);
        gridPane.add(new ImageView(blackPawnImage),2,7);
        gridPane.add(new ImageView(emptyImage), 3, 7);
        gridPane.add(new ImageView(blackPawnImage),4,7);
        gridPane.add(new ImageView(emptyImage), 5, 7);
        gridPane.add(new ImageView(blackPawnImage),6,7);
        gridPane.add(new ImageView(emptyImage), 7, 7);
    }
    public void prepareEventsOnGridPane(){

        gridPane.setOnDragDetected((MouseEvent event) -> {
            try{

                Point targetPoint = getTargetPointFromNode((Node) event.getTarget());
                if(checkTargetIsPawn(targetPoint)
                   & queueController.checkColorPawnDragAndCompareWithCurrentPlayer(queueController.getColorByPoint(targetPoint,gameLogic))) {
                    ImageView imageView = (ImageView) event.getTarget();
                    Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
                    ClipboardContent content = new ClipboardContent();
                    content.putImage(imageView.getImage());
                    db.setContent(content);
                }
            }
            catch(Exception e){
            }
            finally {
                event.consume();
            }
        });


        gridPane.setOnDragOver (new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                try {
                    event.acceptTransferModes(TransferMode.ANY);
                }
                catch(Exception e){
                    ;
                }
                finally {
                    event.consume();
                }
            }
        });

        gridPane.setOnDragDropped(event -> {
            try{
                Dragboard db = event.getDragboard();
                Point destPoint = getTargetPointFromNode((Node) event.getTarget());
                Point sourcePoint = getTargetPointFromNode((Node) event.getGestureSource());
                if(db.hasContent(DataFormat.IMAGE) && gameLogic.isAvaibleMove(sourcePoint,destPoint, false) ) {
                    if(! gameLogic.isAvaibleAnyAttack(queueController.getCurrentPlayer())) {

                        Node nodeTarget = (Node) event.getTarget();
                        setImagePawnOnField(nodeTarget, db);

                        Node nodeSource = (Node) event.getGestureSource();
                        setImageFieldEmptyByNode(nodeSource);

                        gameLogic.changePointOfPawnOnMapAndObjectInLogic(sourcePoint,destPoint);
                        if ( gameLogic.transformPawnToSuperWarriorIfIsTime(destPoint) ){
                            transformToSuperWarrior(destPoint);
                        }

                        if(gameLogic.checkThatLastMoveWasAttack()){
                            if(! gameLogic.isAvaibleAnyAttackForLastAttackerPawn(queueController.getCurrentPlayer(),
                                 gameLogic.getPawnByPointFromMap(destPoint))){
                              queueController.setNextPlayer();
                            }
                        }
                        else{
                            queueController.setNextPlayer();
                        }
                        setImageFieldEmptyByFieldListPointToClear();
                        if(queueController.getCurrentPlayer().equals(QueueController.Player.WHITE)){
                            computerPlayerController.Move();
                            queueController.setNextPlayer();
                        }
                        event.setDropCompleted(true);
                    }
                 }
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
            finally {
                event.consume();
            }
        });
    }

    private Boolean checkTargetIsPawn(Point target){
        return gameLogic.getAllPawnsPointList().contains(target);
    }

    public Scene prepareScene(){
        return new Scene(gridPane, 600, 600);
    }

    private void setImageFieldEmptyByNode(Node node){
        int cIndex = GridPane.getColumnIndex(node);
        int rIndex = GridPane.getRowIndex(node);
        gridPane.getChildren().remove(node);
        gridPane.add(new ImageView(greenBackgroundImage), cIndex, rIndex);
    }

    public void transformToSuperWarrior(Point point){
        int cIndex = point.x;
        int rIndex = point.y;
        gameLogic.fieldListPointToClear.add(new Point(cIndex,rIndex));
        setImageFieldEmptyByFieldListPointToClear();
        if (gameLogic.getPawnByPointFromMap(point).isWhite()){
          gridPane.add(new ImageView(superWhitePawnImage),cIndex,rIndex);
        }
        else{
          gridPane.add(new ImageView(superBlackPawnImage),cIndex,rIndex);
        }

    }
    public void setImageFieldEmptyByFieldListPointToClear(){
        if(gameLogic.fieldListPointToClear.size() >0){
            for (Point p: gameLogic.fieldListPointToClear) {
                for(Node n: getNodesByPoint(p.x,p.y)){
                    setImageFieldEmptyByNode(n);
                }
            }
        }
        gameLogic.fieldListPointToClear.clear();
    }
    public List<Node> getNodesByPoint (final int column, final int row) {
        List<Node>  NodeList = new ArrayList<>();
        for (Node node : gridPane.getChildren()) {
            if(node instanceof ImageView ){
              if (GridPane.getColumnIndex(node) == column && GridPane.getRowIndex(node) == row) {
                  NodeList.add(node);
              }
            }
        }
        return NodeList;
    }

    private void setImagePawnOnField(Node node, Dragboard db){
        int cIndex = GridPane.getColumnIndex(node);
        int rIndex = GridPane.getRowIndex(node);
        ImageView imageView = new ImageView((Image) db.getContent(DataFormat.IMAGE));
        gridPane.add(imageView,cIndex,rIndex);
    }

    public void setImagePawnOnFieldByPoint(Point pointDest, QueueController.Player colorPlayer){
        ImageView imageView;
       Pawns pawn = gameLogic.getPawnByPointFromMap(pointDest);
       if(colorPlayer.equals(QueueController.Player.WHITE)) {
           if (pawn.isSuperWarrior()){
               imageView = new ImageView(superWhitePawnImage);
           }
           else {
               imageView = new ImageView(whitePawnImage);
           }
       }
       else {
           if (pawn.isSuperWarrior()){
               imageView = new ImageView(superBlackPawnImage);
           }
           else {
               imageView = new ImageView(blackPawnImage);
           }
       }
        gridPane.add(imageView,pointDest.x,pointDest.y);
    }

    private Point getTargetPointFromNode(Node node){
        int cIndex = GridPane.getColumnIndex(node);
        int rIndex = GridPane.getRowIndex(node);
        return new Point(cIndex, rIndex);
    }

}
