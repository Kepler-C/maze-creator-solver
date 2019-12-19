package de.uni_hannover.hci.maze.random_kruskal_algorithm;

public class Wall {
    
    private Side s;
    //private boolean isBorder;// bringt nicht viel , könnte weg.
    private boolean destroyed;
    
    
    public Wall(Side s){
        this.s = s;
        destroyed = false;
    }
    
    
    
    public boolean isDestroyed(){
        return destroyed;
    }
    
    
    
    public void setIsDestroyed(boolean b){
        destroyed = b;
    }
    
    
    /*
    public void setIsBorder(boolean b){ 
        isBorder = b;
    }
    
    
    
    public boolean getIsBorder(){
        return isBorder;
    }
    */
    
    


}
