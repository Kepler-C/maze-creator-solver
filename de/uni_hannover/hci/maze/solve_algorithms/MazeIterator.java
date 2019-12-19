package de.uni_hannover.hci.maze.solve_algorithms;

import de.uni_hannover.hci.maze.actual_maze.Maze;
import de.uni_hannover.hci.maze.waypointlist.WaypointList;
import de.uni_hannover.hci.maze.maze_entry.MazeEntry;


/** AKA Deep-search Algorithm.
*/
public class MazeIterator {

    public static int iterations;


    public static WaypointList iterateMaze(Maze maze){
		iterations = 0;
        int startPos [] = new int[2]; // takes the starting index of the Array 
        startPos = maze.getStart();
        WaypointList path = new WaypointList(startPos[0],startPos[1]);
        path = iterateMazeRec(startPos[0], startPos[1], maze, path);
        return path;
    }
    
    
    
    public static WaypointList iterateMazeRec(int x, int y, Maze maze, WaypointList path){
        iterations++;
        int[][] vectors = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
        for (int[] vector : vectors){
            int newX = x + vector[0];
            int newY = y + vector[1];
            if(!maze.outOfBounds(newX, newY)){
                if(maze.getPos(newX, newY) == MazeEntry.PATH && !MazeIterator.containsPath(newX, newY, path)){
                    WaypointList newPath = new WaypointList(newX, newY, path);
                    if((newPath = MazeIterator.iterateMazeRec(newX, newY, maze, newPath)) != null){
                        return newPath;
                     }                  
                } else if( maze.getPos(newX, newY) == MazeEntry.EXIT){
                    return path;
                }
            }
        }
        return null;
    }


    public static boolean containsPath(int x, int y, WaypointList path){
        if(path == null) return false;
        while(path.getNext() != null) {
            if(path.getX() == x && path.getY() == y) return true;
            path = path.getNext();
        }
        return false;
    }
    

    public static int getNumberOfIterations(){
        return iterations;
    }
    
   
}