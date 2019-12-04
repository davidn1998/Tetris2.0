package tetris;

import javafx.scene.paint.Color;

public class OBlock extends Block {

  /**
   * Constructor initializes specific block visual
   */
  public OBlock(int SQUARESIZE){
    super(SQUARESIZE);
    name = "o";
    color = Color.GOLD;
    setColor();

    gridArray = new int[][]{
            {0, 0, 0, 0},
            {0, 1, 2, 0},
            {0, 4, 3, 0},
            {0, 0, 0, 0}
    };

    tetrominoGrid.setPrefWidth(Main.SQUARESIZE * 4);
    tetrominoGrid.setPrefHeight(Main.SQUARESIZE * 4);

    fillGrid();

  }

  @Override
  public void rotateTetromino() {
    return;
  }
}
