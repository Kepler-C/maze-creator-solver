package de.uni_hannover.hci.maze.recursive_division_method;

import de.uni_hannover.hci.maze.random_kruskal_algorithm.*;
import de.uni_hannover.hci.maze.maze_entry.MazeEntry;
import java.util.Random;


public class RecursiveDivisionMethod {
    
    private MazeEntry[][] blocks;
    private int width;
    private int height;
    
    
    
    public RecursiveDivisionMethod(int w, int h){
        width = 2 * w + 1; 
        height = 2 * h + 1;
        initializeBlocks(width, height);
        setBorders();
        setEntry();
        setExit();
        divideChamber(0, blocks[0].length - 1, 0, blocks.length - 1);
    }
    

    
    /* 
     * Quadrants : 1 = top right, 2 = top left, 3 = bottom left, 4 = bottom right.
    */
    private void divideChamber(int leftCol, int rightCol, int topCol, int bottomCol){
        Random r = new Random(); 
        int vertiCol = r.nextInt(rightCol - 1);
        if(vertiCol % 2 != 0){
            vertiCol--;
        }
        if(vertiCol <  (leftCol + 2)){
            vertiCol = (leftCol + 2);
        }
        int horiCol = r.nextInt(bottomCol - 1);
        if(horiCol % 2 != 0){
            horiCol--;
        }
        if(horiCol < (topCol + 2)){
            horiCol = topCol + 2;
        }
        createHorizontalColumn(horiCol, leftCol, rightCol, topCol, bottomCol);
        createVerticalColumn(vertiCol, leftCol, rightCol, topCol, bottomCol);
        createPassages(horiCol, vertiCol, leftCol, rightCol, topCol, bottomCol);
        if(hasChamber(vertiCol, rightCol, topCol, horiCol)){       // first Quadrant (top right)
            divideChamber(vertiCol, rightCol, topCol, horiCol);
        }
        if(hasChamber(leftCol, vertiCol, topCol, horiCol)){        // second Quadrant (top left)
            divideChamber(leftCol, vertiCol, topCol, horiCol);
        }
        if(hasChamber(leftCol, vertiCol, horiCol, bottomCol)){     // second Quadrant (down left)
            divideChamber(leftCol, vertiCol, horiCol, bottomCol);
        }
        if(hasChamber(vertiCol, rightCol, horiCol, bottomCol)){    // second Quadrant (down right)
            divideChamber(vertiCol, rightCol, horiCol, bottomCol);
        }
    }
    
    
    
    private void createVerticalColumn(int c, int leftCol, int rightCol, int topCol, int bottomCol){
        for(int i = topCol + 1; i < bottomCol; i++){
            for(int j = leftCol + 1; j < rightCol; j++){
                if(j == c){
                    blocks[i][j] = MazeEntry.WALL;
                }
            }
        }
    }
    
    
    
    private void createHorizontalColumn(int c, int leftCol, int rightCol, int topCol, int bottomCol){
        for(int i = topCol + 1; i < bottomCol; i++){
            for(int j = leftCol + 1; j < rightCol; j++){
                if(i == c){
                    blocks[i][j] = MazeEntry.WALL;
                }
            }
        }
    }
    
    
    private void createPassages(int horiCol, int vertiCol, int leftCol, int rightCol, int topCol, int bottomCol){
        Random r = new Random();
        int firstHoriPassg = r.nextInt(rightCol);
        if(firstHoriPassg <= (vertiCol)){
            firstHoriPassg = vertiCol + 1;
        }
        if(firstHoriPassg % 2 == 0){
           firstHoriPassg++;
        }
        int secondHoriPassg = r.nextInt(vertiCol);
        if(secondHoriPassg <= leftCol){
            secondHoriPassg = leftCol + 1;
        }
        if(secondHoriPassg % 2 == 0){
            secondHoriPassg++;
        }
        int vertiPassg = r.nextInt(bottomCol);
        if(vertiPassg <= topCol){
            vertiPassg = topCol + 1;
        }
        if(vertiPassg % 2 == 0){
            vertiPassg++;
        }
        for(int i = topCol + 1; i < bottomCol; i++){
            for(int j = leftCol + 1; j < rightCol; j++){
                if(j == firstHoriPassg && i == horiCol 
                   || j == secondHoriPassg && i == horiCol 
                   || i == vertiPassg && j == vertiCol) {
                    blocks[i][j] = MazeEntry.PATH;
                }
            }
        }
    }
    
    
    private boolean isHorizontalPassage(int x, int y){
        return blocks[y][x] == MazeEntry.PATH && blocks[y][x - 1] == MazeEntry.WALL && blocks[y][x + 1] == MazeEntry.WALL;
    }

    
    
    private boolean isVerticalPassage(int x, int y){
        return blocks[y][x] == MazeEntry.PATH && blocks[y - 1][x] == MazeEntry.WALL && blocks[y + 1][x] == MazeEntry.WALL;
    }
    
    
    
    private boolean hasChamber(int verticalColumn,  int limitX, int horizontalColumn, int limitY){
        return Math.abs(limitX - verticalColumn) > 3 && Math.abs(limitY - horizontalColumn) > 3;
    }

    
    
    
    public MazeEntry[][] getMaze(){
        return blocks;
    }
    
    
    private void initializeBlocks(int w, int h){
        blocks = new MazeEntry[h][w];
        for(int i = 0; i < blocks.length; i++){
            for(int j = 0; j < blocks[0].length; j++){
                blocks[i][j] = MazeEntry.PATH;
            }
        }    
    }
    
    
    private void setBorders(){
        for(int i = 0; i < blocks.length; i++){
            for(int j = 0; j < blocks[0].length; j++){
                if(i == 0 || i == blocks.length - 1 || j == 0 || j == blocks[0].length - 1){
                    blocks[i][j] = MazeEntry.WALL;
                }
            }
        }
    }
       
    
    
    private void setEntry(){
        blocks[0][1] = MazeEntry.ENTRY;
    }
    
    
    
    private void setExit(){
        blocks[blocks.length - 1][blocks[0].length - 2] = MazeEntry.EXIT;
    }
    
    
     
}