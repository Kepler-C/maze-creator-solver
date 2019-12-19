package de.uni_hannover.hci.maze.actual_maze;

import de.uni_hannover.hci.maze.random_kruskal_algorithm.*;
import de.uni_hannover.hci.maze.maze_entry.MazeEntry;
import de.uni_hannover.hci.maze.algorithm_selector.AlgorithmSelector;
import de.uni_hannover.hci.maze.algorithms.Algorithm;
import de.uni_hannover.hci.maze.waypointlist.WaypointList;
import de.uni_hannover.hci.maze.solve_algorithms.MazeIterator;



public class Maze {

    private MazeEntry[][] entries;
    private String strMaze;
    private int width;
    private int height;
    private int[] startPos = {1, 0};
    
    
    /** Custom constructor.
      * Creates string maze and a two-dimensional array of MazeEntry elements from it.
      * @param width The number of horizontal cells.
      * @param height The number of vertical cells.
    */
    public Maze(int width, int height, int algorithm){
        this.width = width;
        this.height = height;
        if(algorithm == 1){
            strMaze = AlgorithmSelector.getMazeFromAlgorithm(Algorithm.RANDOMIZED_PRIM_ALGORITHM, width, height);
            System.out.println("ran. prim's alg. selected");
        } else {
            strMaze = AlgorithmSelector.getMazeFromAlgorithm(Algorithm.RANDOM_KRUSKAL, width, height);
            System.out.println("rand. Kruskal's alg. selected");
        }
        int w = searchWidth(strMaze); // horizontal length of the final image.
        int h = searchHeight(strMaze); // vertical length of the final image.
        entries = new MazeEntry[h][w];
        char maze[] = strMaze.toCharArray();
        int x = 0;
        int y = 0;
        for(char c: maze){
            if(c == '\n') continue;
            if(c == '#') entries[y][x] = MazeEntry.WALL;
            if(c == ' ') entries[y][x] = MazeEntry.PATH;
            if(c == 'E') entries[y][x] = MazeEntry.ENTRY;   
            if(c == 'A') entries[y][x] = MazeEntry.EXIT;
            if(x == searchWidth(strMaze) - 1){
                x = 0;
                y++;
            } else {
                x++;
            }
        }
    }
    
    
    /** Custom constructor.
      * Creates a two-dimensional array of MazeEntry elements from a string.
      * @param s a string maze.
    */
    public Maze(String s){
        strMaze = s;
        int w = searchWidth(strMaze); // horizontal length of the final image.
        int h = searchHeight(strMaze); // vertical length of the final image.
        entries = new MazeEntry[h][w];
        char maze[] = strMaze.toCharArray();
        int x = 0;
        int y = 0;
        for(char c: maze){
            if(c == '\n') continue;
            if(c == '#') entries[y][x] = MazeEntry.WALL;
            if(c == ' ') entries[y][x] = MazeEntry.PATH;
            if(c == 'E') entries[y][x] = MazeEntry.ENTRY;
            if(c == 'A') entries[y][x] = MazeEntry.EXIT;
            if(x == searchWidth(strMaze) - 1){
                x = 0;
                y++;
            } else {
                x++;
            }
        }
    }

   
   
   /** Custom constructor.
     * Creates a maze object and its string amze from a 2-dimensional array of MazeEntry.
     * @param e the MazeEntry matrix.
   */
   public Maze(MazeEntry[][] e){
       entries = e;
       strMaze = getStringMazeFromMatrix();
   }
   
   
   
   public String getStringMazeFromMatrix(){
       String result = "";
       for(int i = 0; i < entries.length; i++){
           for(int j = 0; j < entries[0].length; j++){
               if(entries[i][j] == MazeEntry.WALL){
                   result += "#";
               }
               if(entries[i][j] == MazeEntry.PATH){
                   result += " ";
               }
               if(entries[i][j] == MazeEntry.ENTRY){
                   result += "E";
               }
               if(entries[i][j] == MazeEntry.EXIT){
                   result += "A";
               }
               if(j == (entries[0].length - 1) && i != (entries.length - 1)) {
                   result += "\n";
               }
           }
       }
       return result;
   }

    
    public MazeEntry[][] getMaze(){
         return this.entries;
    }

    
    /** Returns string maze for creating a .txt file with it.
    */
    public String getStringMaze(){
        return strMaze;
    }


    /** Finds the width of the maze.
      * Width means at this point, the horizontal length of the final rendered image. 
    */
    private int searchWidth(String s){ 
        char maze[] = s.toCharArray();
        int result = 0;
        while(maze[result] != '\n') result++;
        return result;
    }


    /** Finds the height of the maze.
     * Height means at this point, the vertical length of the final rendered image. 
    */
    private int searchHeight(String s){
        char maze[] = s.toCharArray();
        int result = 0;
        int i = 0;
        while(i < maze.length){
           if(maze[i] == '\n') result++;
           i++;
        }
        return result + 1; // plus die letzte Zeile.
    }
    
    
    /** Sets en Entry-element in the entries array.
      * @param x the x coordinate in the array.
      * @param y the y coordinate in the array.
      * @param data the new entry element.
    */
    public void setPos(int x, int y, MazeEntry data) {
        entries[y][x] = data;
    }
    
    
    /** Gets an entry element from the entries array.
      * @param x the x coordinate in the array.
      * @param y the y coordinate in the array.
    */
    public MazeEntry getPos(int x, int y){
        MazeEntry e = entries[y][x];
        return e;
    }    
    
    
    
    public int[] getStart(){ 
        return startPos;
    }
    
    
    public int getHeight(){ 
         return entries.length;
    }
    
    
    public int getWidth(){ 
        return entries[0].length;
    }
    
    
    public int getVerticalCells(){
        return height;
    }
    
    public int getHorizontalCells(){
        return width;
    }

    
    public boolean outOfBounds(int x, int y){
        return  x < 0 || x >= getWidth() || y >= getHeight() || y < 0;
    }
    
    
    
    
    /** Sets the solution path in the entries array.
      * @param path a linked list with the solution coordinates.
    */
    public void setSolution(WaypointList path){
        WaypointList iter = path;
        while(iter != null){
            if(getPos(iter.getX(), iter.getY()) != MazeEntry.WALL && 
               getPos(iter.getX(), iter.getY()) != MazeEntry.ENTRY &&
               getPos(iter.getX(), iter.getY()) != MazeEntry.EXIT){
                  setPos(iter.getX(), iter.getY(), MazeEntry.SOLUTION);
            }
            iter = iter.getNext();
        }
    }
    
    
    
    public void eraseSolution(){
        for(int i = 0; i < entries.length; i++){
            for(int j = 0; j < entries[0].length; j++){
                if(entries[i][j] == MazeEntry.SOLUTION){
                    entries[i][j] = MazeEntry.PATH;   
                }
            }
        }
    }
           
           
    /** Displays the coordinates of the solution. 
        Use only for testing pourposes.
    */
    public void printList(WaypointList wpl){
        if(wpl != null){
            System.out.println("(" + wpl.getX() + ", " + wpl.getY() + ")");
            printList(wpl.getNext());
        } else {
            System.out.printf("[ ]\n");
        }
    }

}
