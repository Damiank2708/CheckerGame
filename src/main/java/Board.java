import javafx.application.Application;
import javafx.stage.Stage;


public class Board extends  Application{

    public static void main(String[] args)  {
      launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        GameLogic gameLogic = new GameLogic();
        GameBoardController gameBoardController = new GameBoardController(gameLogic);
        gameBoardController.prepareBackGround();
        gameBoardController.prepareGridPane();
        gameBoardController.preparePawnsOnBoard();
        gameBoardController.prepareEventsOnGridPane();

        gameBoardController.preparePawnsInLogic();


        primaryStage.setTitle("Checker Game");
        primaryStage.setScene(gameBoardController.prepareScene());
        primaryStage.setResizable(false);
        primaryStage.show();


    }

}
