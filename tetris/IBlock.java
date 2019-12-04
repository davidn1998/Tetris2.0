package tetris;

import javafx.scene.paint.Color;

import java.awt.*;

public class IBlock extends Block {

  /**
   * Constructor initializes specific block visual
   */
    public IBlock(int SQUARESIZE){
        super(SQUARESIZE);
        name = "i";
        color = Color.CYAN;
        setColor();

        gridArray = new int[][]{
                {0, 0, 4, 0},
                {0, 0, 3, 0},
                {0, 0, 2, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 0}
        };

        tetrominoGrid.setPrefWidth(Main.SQUARESIZE * 5);
        tetrominoGrid.setPrefHeight(Main.SQUARESIZE * 5);

        tetrominoGrid.setLayoutY(Main.SQUARESIZE );
        tetrominoGrid.setLayoutX(Main.width/2 - Main.SQUARESIZE );

        this.fillGrid();

    }

    @Override
    public void fillGrid(){

        tetrominoGrid.getChildren().clear();

        for (int y = 0; y < gridArray.length - 1; y++) {
            for (int x = 0; x < gridArray[y].length + 1; x++) {
                if(gridArray[x][y] != 0){
                    switch (gridArray[x][y]){
                        case 1:
                            tetrominoGrid.getChildren().add(a);
                            a.setLayoutX(x * Main.SQUARESIZE);
                            a.setLayoutY(y * Main.SQUARESIZE);
                            break;
                        case 2:
                            tetrominoGrid.getChildren().add(b);
                            b.setLayoutX(x * Main.SQUARESIZE);
                            b.setLayoutY(y * Main.SQUARESIZE);
                            break;
                        case 3:
                            tetrominoGrid.getChildren().add(c);
                            c.setLayoutX(x * Main.SQUARESIZE);
                            c.setLayoutY(y * Main.SQUARESIZE);
                            break;
                        case 4:
                            tetrominoGrid.getChildren().add(d);
                            d.setLayoutX(x * Main.SQUARESIZE);
                            d.setLayoutY(y * Main.SQUARESIZE);
                            break;

                    }
                }
            }
        }
    }

    @Override
    public void rotateTetromino(){

        changeRotation();

        switch (rotation){

            case 1:
                gridArray = new int[][]{
                        {0, 0, 4, 0},
                        {0, 0, 3, 0},
                        {0, 0, 2, 0},
                        {0, 0, 1, 0},
                        {0, 0, 0, 0}
                };
                break;
            case 2:
                gridArray = new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {4, 3, 2, 1},
                        {0, 0, 0, 0},
                        {0, 0, 0, 0}
                };
                break;
            case 3:
                gridArray = new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 1, 0},
                        {0, 0, 2, 0},
                        {0, 0, 3, 0},
                        {0, 0, 4, 0}
                };
                break;
            case 4:
                gridArray = new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 2, 3, 4},
                        {0, 0, 0, 0},
                        {0, 0, 0, 0}
                };
                break;

        }

        this.fillGrid();
    }
}
