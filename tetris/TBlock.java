package tetris;

import javafx.scene.paint.Color;

public class TBlock extends Block {

    /**
     * Constructor initializes specific block visual
     */
    public TBlock(int SQUARESIZE){
        super(SQUARESIZE);
        name = "t";
        color = Color.PURPLE;
        setColor();

        gridArray = new int[][]{
                {0, 0, 0, 0},
                {0, 1, 2, 3},
                {0, 0, 4, 0},
                {0, 0, 0, 0}
        };

        tetrominoGrid.setPrefWidth(Main.SQUARESIZE * 4);
        tetrominoGrid.setPrefHeight(Main.SQUARESIZE * 4);

        fillGrid();

    }
}
