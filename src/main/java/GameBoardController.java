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
import java.util.LinkedList;

public class GameBoardController {

    private Image imageBoard= new Image("/board.png");
    private Image whitePawnImage = new Image("/WhitePawn.png", 75, 75,false, false);
    private Image blackPawnImage = new Image("/blackPawn.png", 75, 75,false, false);
    private Image greenBackgroundImage = new Image("/GreenBackgroundImage.png", 75, 75,false, false);
    private Image emptyImage = new Image("/EmptyImage.png", 75, 75,false, false);
    private Background background;
    private GridPane gridPane;
    private GameLogic gameLogic;

    public GameBoardController(GameLogic gameLogic){
        gridPane = new GridPane();
        this.gameLogic = gameLogic;
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
        gridPane.setGridLinesVisible(true);
        gridPane.setPadding(new Insets(0, 2, 2, 3));
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
                if(checkTargetIsPawn(targetPoint)) {
                    ImageView imageView = (ImageView) event.getTarget();
                    Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
                    ClipboardContent content = new ClipboardContent();
                    content.putImage(imageView.getImage());
                    db.setContent(content);
                }
            }
            catch(Exception e){
                ;
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

        gridPane.setOnDragDropped(new EventHandler<DragEvent>()  {
            public void handle(DragEvent event) {
                try{
                    Dragboard db = event.getDragboard();
                    Point destPoint = getTargetPointFromNode((Node) event.getTarget());
                    Point sourcePoint = getTargetPointFromNode((Node) event.getGestureSource());
                    if(db.hasContent(DataFormat.IMAGE) && gameLogic.isAvaibleMove(sourcePoint,destPoint) ) {
                        Node nodeTarget = (Node) event.getTarget();
                        setImagePawnOnField(nodeTarget, db);

                        Node nodeSource = (Node) event.getGestureSource();
                        setImageFieldEmptyByNode(nodeSource);

                        gameLogic.changePointOfPawnOnMapAndObjectInLogic(getTargetPointFromNode(nodeSource),
                                                                         getTargetPointFromNode(nodeTarget));
                        event.setDropCompleted(true);
                     }
                }
                catch(Exception e){
                    ;
                }
                finally {
                    event.consume();
                }
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

    private void setImagePawnOnField(Node node, Dragboard db){
        int cIndex = GridPane.getColumnIndex(node);
        int rIndex = GridPane.getRowIndex(node);
        ImageView imageView = new ImageView((Image) db.getContent(DataFormat.IMAGE));
        gridPane.add(imageView,cIndex,rIndex);
    }

    private Point getTargetPointFromNode(Node node){
        int cIndex = GridPane.getColumnIndex(node);
        int rIndex = GridPane.getRowIndex(node);
        return new Point(cIndex, rIndex);
    }


    public void preparePawnsInLogic(){

        Pawns w1 = new Pawns(false, true, false, new Point(1,0));
        gameLogic.addPawnToMap(w1.getPoint(), w1);
        Pawns w2 = new Pawns(false, true, false, new Point(3,0));
        gameLogic.addPawnToMap(w2.getPoint(), w2);
        Pawns w3 = new Pawns(false, true, false, new Point(5,0));
        gameLogic.addPawnToMap(w3.getPoint(), w3);
        Pawns w4 = new Pawns(false, true, false, new Point(7,0));
        gameLogic.addPawnToMap(w4.getPoint(), w4);

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

        Pawns b1 = new Pawns(true, false, false, new Point(0,5));
        gameLogic.addPawnToMap(new Point(0,5), b1);
        Pawns b2 = new Pawns(true, false, false, new Point(2,5));
        gameLogic.addPawnToMap(new Point(2,5), b2);
        Pawns b3 = new Pawns(true, false, false, new Point(4,5));
        gameLogic.addPawnToMap(new Point(4,5), b3);
        Pawns b4 = new Pawns(true, false, false, new Point(6,5));
        gameLogic.addPawnToMap(new Point(6,5), b4);

        Pawns b5 = new Pawns(true, false, false, new Point(1,6));
        gameLogic.addPawnToMap(new Point(1,6), b5);
        Pawns b6 = new Pawns(true, false, false, new Point(3,6));
        gameLogic.addPawnToMap(new Point(3,6), b6);
        Pawns b7 = new Pawns(true, false, false, new Point(5,6));
        gameLogic.addPawnToMap(new Point(5,6), b7);
        Pawns b8 = new Pawns(true, false, false, new Point(7,6));
        gameLogic.addPawnToMap(new Point(7,6), b8);

        Pawns b9 = new Pawns(true, false, false, new Point(0,7));
        gameLogic.addPawnToMap(new Point(0,7), b9);
        Pawns b10 = new Pawns(true, false, false, new Point(2,7));
        gameLogic.addPawnToMap(new Point(2,7), b10);
        Pawns b11 = new Pawns(true, false, false, new Point(4,7));
        gameLogic.addPawnToMap(new Point(4,7), b11);
        Pawns b12 = new Pawns(true, false, false, new Point(6,7));
        gameLogic.addPawnToMap(new Point(6,7), b12);
    }

}
