import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;

import java.awt.*;

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
                ImageView imageView = (ImageView)event.getTarget();
                Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putImage(imageView.getImage());
                db.setContent(content);
            }
            catch(Exception e){
                ;
            }
            finally {
                event.consume();
            }
        });

        gridPane.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                try {
                    ImageView imageView = (ImageView)event.getTarget();
                    if (imageView != null) {
                        event.acceptTransferModes(TransferMode.ANY);
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

        gridPane.setOnDragDropped(new EventHandler<DragEvent>()  {
            public void handle(DragEvent event) {
                try{
                    Dragboard db = event.getDragboard();
                    if(db.hasContent(DataFormat.IMAGE)){
                        Node node = (Node) event.getTarget();
                        Integer cIndex = GridPane.getColumnIndex(node);
                        Integer rIndex = GridPane.getRowIndex(node);
                        ImageView imageView = new ImageView((Image) db.getContent(DataFormat.IMAGE));
                        gridPane.add(imageView,cIndex,rIndex);
                        node = (Node) event.getGestureSource();
                        cIndex = GridPane.getColumnIndex(node);
                        rIndex = GridPane.getRowIndex(node);
                        gridPane.getChildren().remove(node);
                        gridPane.add(new ImageView(greenBackgroundImage), cIndex, rIndex);
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

    public Scene prepareScene(){
        return new Scene(gridPane, 600, 600);
    }

    public void preparePawnsInLogic(){

        Pawns p1 = new Pawns(false, true, false, new Point(1,0));
        gameLogic.addPawnToMap(new Point(1,0), p1);
    }

}
