package tetris;

import javafx.scene.paint.Color;

public class SBlock extends Block {

    /**
     * Constructor initializes specific block visual
     */
    public SBlock(int SQUARESIZE){
        super(SQUARESIZE);
        name = "s";
        color = Color.LIMEGREEN;
        setColor();

        gridArray = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 2, 1},
                {0, 4, 3, 0},
                {0, 0, 0, 0}
        };

        tetrominoGrid.setPrefWidth(Main.SQUARESIZE * 4);
        tetrominoGrid.setPrefHeight(Main.SQUARESIZE * 4);

        fillGrid();

    }
}
