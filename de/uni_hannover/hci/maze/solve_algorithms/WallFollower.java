package de.uni_hannover.hci.maze.solve_algorithms;

import de.uni_hannover.hci.maze.actual_maze.Maze;
import de.uni_hannover.hci.maze.waypointlist.WaypointList;
import de.uni_hannover.hci.maze.maze_entry.MazeEntry;


/** Implementation for the  wall-follower-algorithm.
  * Also known as left -or right- hand rule. In this case it follows the left wall.
*/
public class WallFollower {
    
    
    private WaypointList path;
    public WaypointList result;
    public int iterations;
    
    
    
    public WaypointList findExit(Maze maze){
        // The start point of the the maze is always P(x,y) = (1, 0)
        int xPosition = 1;
        int yPosition = 0;
        iterations = 0;
        int[] direction = {0, 1}; // it begins going down.
        path = new WaypointList(xPosition, yPosition);
        do {
            if(hasLeftWall(xPosition, yPosition, direction, maze)) {    
                if(maze.getMaze()[yPosition + direction[1]][xPosition + direction[0]] != MazeEntry.WALL){
                    xPosition += direction[0];
                    yPosition += direction[1];      
                } else {
                    direction = turnRight(direction);
                    if(maze.getMaze()[yPosition + direction[1]][xPosition + direction[0]] != MazeEntry.WALL){
                        xPosition += direction[0];
                        yPosition += direction[1];             
                    }
                }
            } else {
                direction = turnLeft(direction);
                if(maze.getMaze()[yPosition + direction[1]][xPosition + direction[0]] != MazeEntry.WALL){ 
                    xPosition += direction[0];
                    yPosition += direction[1];
                }
            }
            path = new WaypointList(xPosition, yPosition, path);
            iterations++;
        } while(!isExit(path.getX(), path.getY(), maze));
        extractSolution(path);
        return result;
    }
    
    
    private int[] turnRight(int[] dir){
        int[] newDirection = dir;
        if(newDirection[0] == 0 && newDirection[1] == 1) {// is going down?
            newDirection[0] = -1; // go left
            newDirection[1] = 0; 
        } else if(newDirection[0] == 0 && newDirection[1] == -1){ // going up?
            newDirection[0] = 1; // go right
            newDirection[1] = 0;
        } else if(newDirection[0] == -1 && newDirection[1] == 0){ // going to the left
            newDirection[0] = 0; // go up
            newDirection[1] = -1;
        } else /*if(newDirection[0] == 1 && newDirection[1] == 0)*/{ // going to the right?
            newDirection[0] = 0; // go down
            newDirection[1] = 1;
        }
        return newDirection;
    }
    
    

    
    private int[] turnLeft(int[] dir){
        int[] newDirection = dir;
        if(newDirection[0] == 0 && newDirection[1] == 1){ // is going down?
           newDirection[0] = 1; // go right
           newDirection[1] = 0;
        } else if(newDirection[0] == 0 && newDirection[1] == -1){ // going up?
            newDirection[0] = -1; // go left
            newDirection[1] = 0;
        } else if(newDirection[0] == -1 && newDirection[1] == 0){ // going to the left
            newDirection[0] = 0; // go down
            newDirection[1] = 1;
        } else /*if(newDirection[0] == 1 && newDirection[1] == 0)*/{ // going to the right?
            newDirection[0] = 0; // go up 
            newDirection[1] = -1;
        }
        return newDirection;
    }
    
    
    
    private boolean hasLeftWall(int x, int y, int[] dir, Maze m){
        if(dir[0] == 0 && dir[1] == 1){ // is going down?
            return m.getMaze()[y][x + 1] == MazeEntry.WALL; 
        } else if(dir[0] == 0 && dir[1] == -1){ // is going up?
            return m.getMaze()[y][x - 1] == MazeEntry.WALL;
        } else if(dir[0] == -1 && dir[1] == 0){ // is going to the left?
            return m.getMaze()[y + 1][x] == MazeEntry.WALL;
        } else/* if(dir[0] == 1 && dir[1] == 0)*/{ // is going to the right?
            return m.getMaze()[y - 1][x] == MazeEntry.WALL;
        }
    }
    
    

    
    private boolean isExit(int x, int y, Maze m){
        return m.getMaze()[y][x] == MazeEntry.EXIT;
    }
    

    
   private boolean isRepeated(int x, int y, WaypointList p){
       int counter = 0;
       while(p != null){
           if(p.getX() == x && p.getY() == y) {
               counter++;
           }    
           p = p.getNext();
       }
       return counter > 1; // a cell may be repeated only one time.
   }
   
   
   
   public void extractSolution(WaypointList wpl){
       result = null;
       while(wpl != null){
           // Point appears only once
           if(!isRepeated(wpl.getX(), wpl.getY(), path)) {
               result = new WaypointList(wpl.getX(), wpl.getY(), result);
           } 
           // Point appears two times but belongs to the solution
           if(isRepeated(wpl.getX(), wpl.getY(), path) && 
           !isRepeated(wpl.getNext().getX(), wpl.getNext().getY(), path)){
               result = new WaypointList(wpl.getX(), wpl.getY(), result);
            } 
           wpl = wpl.getNext();
       }
   }
   

   
   public int getNumberOfIterations(){
       return iterations;
   }
    

}