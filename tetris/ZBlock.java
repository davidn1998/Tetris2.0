package tetris;

import javafx.scene.paint.Color;

public class ZBlock extends Block {

    /**
     * Constructor initializes specific block visual
     */
    public ZBlock(int SQUARESIZE){
        super(SQUARESIZE);
        name = "z";
        color = Color.RED;
        setColor();

        gridArray = new int[][]{
                {0, 0, 0, 0},
                {0, 4, 3, 0},
                {0, 0, 2, 1},
                {0, 0, 0, 0}
        };

        tetrominoGrid.setPrefWidth(Main.SQUARESIZE * 4);
        tetrominoGrid.setPrefHeight(Main.SQUARESIZE * 4);

        fillGrid();

    }
}
