package de.uni_hannover.hci.maze.waypointlist;


/**
 * Representation of a solution for the maze.
 * This class is a linked list with a sequence of coordinates in the plane (x,y).
*/
public class WaypointList {

    private int x;
    private int y;
    private WaypointList next;
    
    
    
    public WaypointList(int x, int y){
        this(x, y, null);
    } 

    
    public WaypointList(int x, int y, WaypointList next){
        this.x = x;
        this.y = y;
        this.next = next;
    }
    
    
    public void setNext(WaypointList n){
        next = n;
    }
    
    
    public int getX(){ 
        return x;
    }
    
    
    public int getY() { 
        return y;
    }
    
    
    public WaypointList getNext(){
        return next;
    }

    
    
}
