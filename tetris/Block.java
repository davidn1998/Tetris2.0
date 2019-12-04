package tetris;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Transform;

import java.awt.*;
import java.awt.desktop.SystemEventListener;
import java.util.ArrayList;
import java.util.MissingFormatArgumentException;

public abstract class Block {
    protected Rectangle a;
    protected Rectangle b;
    protected Rectangle c;
    protected Rectangle d;
    protected String name;
    protected Color color;
    public int rotation = 1;

    protected AnchorPane tetrominoGrid;
    protected int[][] gridArray;
    protected ArrayList<Point> coordinates = new ArrayList<>();
    protected ArrayList<Integer> coordinateValue = new ArrayList<>();
    protected Point origin;

    protected Rectangle[] tetromino = new Rectangle[4];

//    protected Group tetromino = new Group();

    /**
     * Constructor creates 4 new rectangles for the block
     */
    public Block(int SQUARESIZE){
        a = new Rectangle(SQUARESIZE-1, SQUARESIZE-1);
        b = new Rectangle(SQUARESIZE-1, SQUARESIZE-1);
        c = new Rectangle(SQUARESIZE-1, SQUARESIZE-1);
        d = new Rectangle(SQUARESIZE-1, SQUARESIZE-1);

        tetrominoGrid = new AnchorPane();

        tetrominoGrid.setLayoutY(-Main.SQUARESIZE );
        tetrominoGrid.setLayoutX(Main.width/2 - Main.SQUARESIZE * 2);

        tetromino[0] = a;
        tetromino[1] = b;
        tetromino[2] = c;
        tetromino[3] = d;

//        tetromino.getChildren().addAll(a,b,c,d);
    }

    public void fillGrid(){

        tetrominoGrid.getChildren().clear();

        for (int y = 0; y < gridArray.length; y++) {
            for (int x = 0; x < gridArray[y].length; x++) {
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

    public AnchorPane getTetromino() {
        return tetrominoGrid;
    }
    
    /** Getters for Rectangles
     */
    public Rectangle a() {
        return a;
    }

    public Rectangle b() {
        return b;
    }

    public Rectangle c() {
        return c;
    }

    public Rectangle d() {
        return d;
    }

    /** Sets the color of the blocks
     *
     */
    public void setColor(){
        this.a.setFill(color);
        this.b.setFill(color);
        this.c.setFill(color);
        this.d.setFill(color);

    }

    /** Getter for Name
      */
    public String getName(){
        return this.name;
    }

    /**
     * changes value for Blocks rotation
     */
    public void changeRotation(){
        if(rotation != 4){
            rotation++;
        }else{
            rotation = 1;
        }
    }

    /**
     * rotation of Block to be implemented in subClasses
     */
    //public abstract void rotateBlock();

    public void rotateTetromino(){

        coordinates.clear();
        coordinateValue.clear();

        // Create new Tetromino Grid
        for (int y = 0; y < gridArray.length; y++) {
            for (int x = 0; x < gridArray[y].length; x++) {
                switch (gridArray[x][y]) {
                    case 2:
                        origin = new Point(x,y);
                        break;
                    case 1:
                        case 3:
                        case 4:
                        coordinates.add(new Point(x,y));
                        coordinateValue.add(gridArray[x][y]);
                        break;

                }

                // Empty cell.
                gridArray[x][y] = 0;
            }
        }

        //Reference to rotation algorithm that was learnt: https://stackoverflow.com/questions/233850/tetris-piece-rotation-algorithm

        Point[] rotatedCoordinates = new Point[coordinates.size()];

        for(int i = 0; i < coordinates.size(); i++){

            // Translates current coordinate to be relative to (0,0)
            Point translationCoordinate = new Point(coordinates.get(i).x - origin.x, coordinates.get(i).y - origin.y);

            // Java coordinates start at 0 and increase as a point moves down, so
            // multiply by -1 to reverse
            translationCoordinate.y *= -1;

            // Clone coordinates, so I can use translation coordinates
            // in upcoming calculation
            rotatedCoordinates[i] = (Point)translationCoordinate.clone();

            // May need to round results after rotation
            rotatedCoordinates[i].x = (int)Math.round(translationCoordinate.x * Math.cos(Math.PI/2) - translationCoordinate.y * Math.sin(Math.PI/2));
            rotatedCoordinates[i].y = (int)Math.round(translationCoordinate.x * Math.sin(Math.PI/2) + translationCoordinate.y * Math.cos(Math.PI/2));

            // Multiply y-coordinate by -1 again
            rotatedCoordinates[i].y *= -1;

            // Translate to get new coordinates relative to
            // original origin
            rotatedCoordinates[i].x += origin.x;
            rotatedCoordinates[i].y += origin.y;

            gridArray[rotatedCoordinates[i].x][rotatedCoordinates[i].y] = coordinateValue.get(i);

        }

//        for(int i = 0; i < rotatedCoordinates.length; i++){
//            gridArray[rotatedCoordinates[i].x][rotatedCoordinates[i].y] = 1;
//        }

        gridArray[origin.x][origin.y] = 2;

        this.fillGrid();
    }

    public boolean canRotate(){

        double gridXPos = tetrominoGrid.localToParent(tetrominoGrid.getLayoutX(),tetrominoGrid.getLayoutY()).getX();
        double gridYPos = tetrominoGrid.localToParent(tetrominoGrid.getLayoutX(),tetrominoGrid.getLayoutY()).getY();

        if(gridXPos - Main.SQUARESIZE >= 0 && gridXPos <= Main.width + Main.SQUARESIZE * 4
        ){
            return true;
        }else {
            return false;
        }
    }

    //MOVING THE BLOCKS

    /** Move Right
     * checks that blocks a, b, c, and d are all less than the max size before moving them to the right
     */
    public void MoveRight(){


        if(getGridXPos(a) <= Main.width - Main.SQUARESIZE * 2 && getGridXPos(b) <= Main.width - Main.SQUARESIZE * 2 && getGridXPos(c) <= Main.width - Main.SQUARESIZE * 2 && getGridXPos(d) <= Main.width - Main.SQUARESIZE * 2){
            tetrominoGrid.setLayoutX(tetrominoGrid.getLayoutX() + Main.SQUARESIZE);
        }

    }

    /** Move Left
     * checks that blocks a, b, c, and d are all greater than 0 before moving them to the left
     */
    public void MoveLeft(){


        if(getGridXPos(a) - Main.SQUARESIZE >= 0 && getGridXPos(b) - Main.SQUARESIZE >= 0 && getGridXPos(c) - Main.SQUARESIZE >= 0 && getGridXPos(d) - Main.SQUARESIZE >= 0){
            tetrominoGrid.setLayoutX(tetrominoGrid.getLayoutX() - Main.SQUARESIZE);
        }

    }

    public void MoveDown(){
            tetrominoGrid.setLayoutY(tetrominoGrid.getLayoutY() + Main.SQUARESIZE);
    }

    private boolean isDownBlocked() {

        return (  (Main.GRID[(int) getGridXPos(a) / Main.SQUARESIZE][((int) getGridYPos(a) / Main.SQUARESIZE) + 1] == 1)
                ||(Main.GRID[(int) getGridXPos(b) / Main.SQUARESIZE][((int) getGridYPos(b) / Main.SQUARESIZE) + 1] == 1)
                ||(Main.GRID[(int) getGridXPos(c) / Main.SQUARESIZE][((int) getGridYPos(c) / Main.SQUARESIZE) + 1] == 1)
                ||(Main.GRID[(int) getGridXPos(d)  / Main.SQUARESIZE][((int) getGridYPos(d) / Main.SQUARESIZE) + 1] == 1)
        );

    }
    private boolean isLefttBlocked() {

        return (  (Main.GRID[(int) getGridXPos(a) / Main.SQUARESIZE - 1][((int) getGridYPos(a) / Main.SQUARESIZE)] == 1)
                ||(Main.GRID[(int) getGridXPos(b) / Main.SQUARESIZE - 1][((int) getGridYPos(b) / Main.SQUARESIZE)] == 1)
                ||(Main.GRID[(int) getGridXPos(c) / Main.SQUARESIZE - 1][((int) getGridYPos(c) / Main.SQUARESIZE)] == 1)
                ||(Main.GRID[(int) getGridXPos(d) / Main.SQUARESIZE - 1][((int) getGridYPos(d) / Main.SQUARESIZE)] == 1)
        );

    }

    private boolean isRighttBlocked() {

        return (  (Main.GRID[(int) getGridXPos(a) / Main.SQUARESIZE + 1][((int) getGridYPos(a) / Main.SQUARESIZE)] == 1)
                ||(Main.GRID[(int) getGridXPos(b) / Main.SQUARESIZE + 1][((int) getGridYPos(b) / Main.SQUARESIZE)] == 1)
                ||(Main.GRID[(int) getGridXPos(c)/ Main.SQUARESIZE + 1][((int) getGridYPos(c) / Main.SQUARESIZE)] == 1)
                ||(Main.GRID[(int) getGridXPos(d) / Main.SQUARESIZE + 1][((int) getGridYPos(d) / Main.SQUARESIZE)] == 1)
        );

    }

    public boolean hasHitGround(){
        if(getGridYPos(a) == Main.height - Main.SQUARESIZE || getGridYPos(b) == Main.height - Main.SQUARESIZE || getGridYPos(c) == Main.height - Main.SQUARESIZE || getGridYPos(d) == Main.height - Main.SQUARESIZE || isDownBlocked())
        {
            return true;
        }else{
            return false;
        }
    }

    public double getGridXPos(Rectangle piece){
        return piece.localToScene(piece.getX(),piece.getY()).getX() - Main.xMin;
    }

    public double getGridYPos(Rectangle piece){
        return piece.localToScene(piece.getX(),piece.getY()).getY() - Main.yMin;
    }

}
