package de.uni_hannover.hci.maze.random_kruskal_algorithm;

import java.util.Random;


public class RandomKruskalAlgorithm {

    private Cell[][] cells;
    private final int width;
    private final int height;
    
    

    public RandomKruskalAlgorithm (int x, int y){
        width = x;
        height = y;
        int cellNumber;
        cells = new Cell[height][width];
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                boolean start, end;
                start = (j == 0 && i == 0) ? true : false;
                end = (j == width - 1) &&  (i == height - 1) ? true : false;
                cellNumber = i * width + j;
                cells[i][j] = new Cell(cellNumber, j, i, start, end);
            }
        }
    }
    
    
    
    public Cell[][] getMaze(){
        return cells;
    }
    
    
    
    public void createMaze(){
        Cell cellA, cellB;
        // wähle 2 Zellen.
        while(!hasFinished()){
            Random r = new Random();
            int randomX = r.nextInt(width);
            int randomY = r.nextInt(height);
            cellA = cells[randomY][randomX];
            cellB = searchNeighbor(randomX, randomY);
            if(cellA.getCellGroup() == cellB.getCellGroup()) continue;
            destroyCommonWalls(cellA, cellB);
            mergeGroups(cells, cellA, cellB);
        }
    }
    
    
    
    private boolean hasFinished(){
        int group = cells[0][0].getCellGroup();
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                if(cells[y][x].getCellGroup() != group) return false;
            }
        }
        return true;
    }
    
    
    
    private Cell searchNeighbor(int x, int y){
        Random r = new Random();
        int randomX = x;
        int randomY = y;
        int randomDirection = r.nextInt(2); // 0->horizontal; 1->vertical
        while(randomX == x && randomY == y){
            if(randomDirection == 0){
                // choose random x coordinate.
                if(x == 0){ // at left border
                    randomX = x + r.nextInt(2);
                } else if(x == (width - 1)){ // right border
                    randomX = x - r.nextInt(2);
                } else {
                   randomX = x + (int)Math.pow(-1, r.nextInt(2)); // +1, -1
                }
            } else {
                // choose random y coordinate
                if(y == 0){ // at top border
                    randomY = y + r.nextInt(2);
                } else if(y == (height - 1)){ // at bottom border.
                    randomY = y - r.nextInt(2);
                } else {
                   randomY = y + (int)Math.pow(-1, r.nextInt(2));
                }
            }
        }
        return cells[randomY][randomX];
    }
    
    
    
    private void destroyCommonWalls(Cell a, Cell b){
        if(a.getPosX() == b.getPosX()){ // the cells have the same x position.
            if(a.getPosY() > b.getPosY()){ // cell a is above cell b.
                a.destroyWall(Side.UP);
                b.destroyWall(Side.DOWN);
            } else {
                a.destroyWall(Side.DOWN);
                b.destroyWall(Side.UP);
            }
        } else { // the cells have the same y position.
            if(a.getPosX() > b.getPosX()){ // cell a is at the right side of cell b.
                a.destroyWall(Side.LEFT);
                b.destroyWall(Side.RIGHT);
            } else {
                a.destroyWall(Side.RIGHT);
                b.destroyWall(Side.LEFT);
            }
        }
    }
    
    
    
    public void mergeGroups(Cell[][] cellmatrix, Cell a, Cell b){
        int oldGroup;
        if(a.getCellGroup() < b.getCellGroup()){
            oldGroup = b.getCellGroup();
            b.setCellGroup(a.getCellGroup());
            for(int i = 0; i < cellmatrix.length; i++){
                for(int j = 0; j < cellmatrix[0].length; j++){
                    if(cellmatrix[i][j].getCellGroup() == oldGroup){
                        cellmatrix[i][j].setCellGroup(a.getCellGroup());
                    }
                }
            }
        } else {
            oldGroup = a.getCellGroup();
            a.setCellGroup(b.getCellGroup());
            for(int i = 0; i < cellmatrix.length; i++){
                for(int j = 0; j < cellmatrix[0].length; j++){
                    if(cellmatrix[i][j].getCellGroup() == oldGroup){
                        cellmatrix[i][j].setCellGroup(b.getCellGroup());
                    }
                }
            }
        }
    }


}
