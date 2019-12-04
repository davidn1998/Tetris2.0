package tetris;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Controller {

    public static final int SQUARESIZE = Main.SQUARESIZE;
    public static int width = Main.width;
    public static int height = Main.height;
    public static int[][] GRID = Main.GRID;

    private static Block currObject;
    public static int score = 0;
    public static int top = 0;
    private static Block nextObj = makeBlock();
    private static int lineNo = 0;
    private static int speed = 3;
    private static int time = 0;

    private int canMove = 0;

    @FXML
    private Pane tetrisPane;

    @FXML
    private Label linesText;

    @FXML
    private Label scoreText;

    @FXML
    public void initialize() {

        /** Set width and height of the tetris play space
         *
         */
        tetrisPane.setPrefWidth(width);
        tetrisPane.setPrefHeight(height);

        tetrisPane.setLayoutX(Main.xMin);
        tetrisPane.setLayoutY(Main.yMin);

        playGame();

    }

    public void playGame() {

        /** Create first block
         *
         */
        Block a = nextObj;
        tetrisPane.getChildren().add(a.getTetromino());
        currObject = a;
        nextObj = makeBlock();

        //Timeline for main gameplay loop

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {

            if(currObject.getGridYPos(currObject.a) <= 0 || currObject.getGridYPos(currObject.b) <= 0 || currObject.getGridYPos(currObject.c) <= 0 || currObject.getGridYPos(currObject.d) <= 0){
                top++;
            }
            else {
                top = 0;
            }
            if (top == 2) {
                /** GAME OVER
                 *
                 */
                Text over = new Text("GAME OVER");
                over.setFill(Color.RED);
                over.setStyle("-fx-font: 70 arial;");
                over.setY(250);
                over.setX(10);
                tetrisPane.getChildren().add(over);
                Main.playingGame = false;
            }

            /** END GAME
             *
             */
            if (top == 15) {
                Main.playingGame = false;
            }

            /** PLAYING GAME
             * @param Main.playingGame is checked to make sure the Main.playingGame has not ended before continuing
             */
            if (Main.playingGame) {
                MoveDown(currObject);
                moveOnKeyPress(currObject);
                canMove++;
                time++;

                linesText.setText("LINES: " + lineNo);
                scoreText.setText("SCORE: " + score);
            }

        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        if(time % 2 == 0){
            speed+=1;
            timeline.setRate(speed);
        }

        timeline.setRate(speed);

    }

    private void moveOnKeyPress(Block block) {
        Main.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case RIGHT:
                        if (Main.playingGame)
                        block.MoveRight();
                        break;
                    case LEFT:
                        if (Main.playingGame)
                        block.MoveLeft();
                        break;
                    case DOWN:
                        if (Main.playingGame && canMove > 1)
                            MoveDown(block);
                        break;
                    case UP:
                        if (block.canRotate() && Main.playingGame) {
                            block.rotateTetromino();
                        }
                        break;
                }
            }
        });
    }

    /**
     * CREATE THE BLOCKS
     * creates a random number of blocks then sets the position of a, b, c, and d based on that size.
     */
    public static Block makeBlock() {

        /** Random block
         * @param block becomes a Integer between 0 and 70
         */
        int block = (int) (Math.random() * 70);

        if (block < 10) {
            return new JBlock(SQUARESIZE);
        } else if (block < 20) {
            return new LBlock(SQUARESIZE);
        } else if (block < 30) {
            return new OBlock(SQUARESIZE);
        } else if (block < 40) {
            return new SBlock(SQUARESIZE);
        } else if (block < 50) {
            return new TBlock(SQUARESIZE);
        } else if (block < 60) {
            return new ZBlock(SQUARESIZE);
        } else {
            return new IBlock(SQUARESIZE);
        }
    }


    public void MoveDown(Block block) {
        if (block.hasHitGround()) {
            GRID[(int) block.getGridXPos(block.a) / SQUARESIZE][(int) block.getGridYPos(block.a) / SQUARESIZE] = 1;
            GRID[(int) block.getGridXPos(block.b) / SQUARESIZE][(int) block.getGridYPos(block.b) / SQUARESIZE] = 1;
            GRID[(int) block.getGridXPos(block.c) / SQUARESIZE][(int) block.getGridYPos(block.c) / SQUARESIZE] = 1;
            GRID[(int) block.getGridXPos(block.d) / SQUARESIZE][(int) block.getGridYPos(block.d) / SQUARESIZE] = 1;


            System.out.println("------------------------------ROOF----------------------------");

            for (int k = 0; k < GRID[0].length; k++) {
                for (int j = 0; j < GRID.length; j++) {
                    System.out.print(GRID[j][k]);
                }
                System.out.println("\n");
            }

            System.out.println("------------------------------FLOOR----------------------------");


            DeleteRow();

            score++;
            Block a = nextObj;
            canMove = 0;
            nextObj = makeBlock();
            currObject = a;
            tetrisPane.getChildren().add(a.getTetromino());
            moveOnKeyPress(a);
        } else {
            block.MoveDown();
        }
    }

    public void DeleteRow() {

        for (int i = 0; i < GRID[0].length; i++) {

            boolean lineFilled = true;
            for (int j = 0; j < GRID.length; j++) {
                if (GRID[j][i] == 0) {
                    lineFilled = false;
                }
            }

            if (lineFilled) {

                for (Node node : tetrisPane.getChildren()) {
                    if (node instanceof AnchorPane) {
                        for (Node rect : ((AnchorPane) node).getChildren()) {
                            if (rect instanceof Rectangle) {

                                if ((int) getGridYPos((Rectangle) rect) == (i * Main.SQUARESIZE)) {
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            ((AnchorPane) node).getChildren().remove(rect);
                                        }
                                    });
                                }
                            }
                        }
                    }
                }

                for (int j = 0; j < GRID.length; j++) {
                    GRID[j][i] = 0;
                }


                for (int lineNo = i - 1; lineNo >= 0; lineNo--) {

                    for (Node node : tetrisPane.getChildren()) {
                        if (node instanceof AnchorPane) {
                            for (Node rect : ((AnchorPane) node).getChildren()) {
                                if (rect instanceof Rectangle) {
                                    if ((int) getGridYPos((Rectangle) rect) == (lineNo * Main.SQUARESIZE)) {

                                        if (!hasHitGround((Rectangle) rect)) {

                                            rect.setLayoutY(rect.getLayoutY() + Main.SQUARESIZE);
                                            Main.GRID[(int) getGridXPos((Rectangle) rect) / Main.SQUARESIZE][((int) getGridYPos((Rectangle) rect) / Main.SQUARESIZE)] = 1;
                                            Main.GRID[(int) getGridXPos((Rectangle) rect) / Main.SQUARESIZE][((int) getGridYPos((Rectangle) rect) / Main.SQUARESIZE) - 1] = 0;

                                        }
                                    }
                                }
                            }
                        }
                    }

                }

                score += 50;
                lineNo++;
                for (int k = 0; k < GRID[0].length; k++) {
                    System.out.println("\n");
                    for (int j = 0; j < GRID.length; j++) {
                        System.out.print(GRID[j][k]);
                    }
                }

                System.out.println("------------------------------CLEARED-----------------------------");

            }

        }

    }


    public static double getGridXPos(Rectangle piece){
        return piece.localToScene(piece.getX(),piece.getY()).getX() - Main.xMin;
    }

    public static double getGridYPos(Rectangle piece){
        return piece.localToScene(piece.getX(),piece.getY()).getY() - Main.yMin;
    }

    public boolean hasHitGround(Rectangle rect){
        if(getGridYPos(rect) >= Main.height + Main.SQUARESIZE )
        {
            return true;
        }else{

            if(((int) getGridYPos(rect) / Main.SQUARESIZE) + 1 < 24){
                return isDownBlocked(rect);
            }else{
                return true;
            }
        }
    }

    private boolean isDownBlocked(Rectangle rect) {

        return (  (Main.GRID[(int) getGridXPos(rect) / Main.SQUARESIZE][((int) getGridYPos(rect) / Main.SQUARESIZE) + 1] == 1));

    }

}
