package tetris;

import javafx.scene.paint.Color;

public class LBlock extends Block {

    /**
     * Constructor initializes specific block visual
     */
    public LBlock(int SQUARESIZE){
        super(SQUARESIZE);
        name = "l";
        color = Color.ORANGE;
        setColor();

        gridArray = new int[][]{
                {0, 0, 1, 0},
                {0, 0, 2, 0},
                {0, 0, 3, 4},
                {0, 0, 0, 0}
        };

        tetrominoGrid.setPrefWidth(Main.SQUARESIZE * 4);
        tetrominoGrid.setPrefHeight(Main.SQUARESIZE * 4);

        fillGrid();

    }
}
