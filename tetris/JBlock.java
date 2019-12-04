package tetris;

import javafx.scene.paint.Color;

public class JBlock extends Block {

    /**
     * Constructor initializes specific block visual
     */
    public JBlock(int SQUARESIZE){
        super(SQUARESIZE);
        name = "j";
        color = Color.BLUE;
        setColor();

        gridArray = new int[][]{
                {0, 0, 1, 0},
                {0, 0, 2, 0},
                {0, 4, 3, 0},
                {0, 0, 0, 0}
        };

        tetrominoGrid.setPrefWidth(Main.SQUARESIZE * 4);
        tetrominoGrid.setPrefHeight(Main.SQUARESIZE * 4);

        fillGrid();

    }
}
