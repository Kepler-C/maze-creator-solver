package de.uni_hannover.hci.maze.maze_container;

import de.uni_hannover.hci.maze.maze_entry.*;
import de.uni_hannover.hci.maze.actual_maze.*;


import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;



/**
 * The panel, in which the maze is drawn.
*/
public class MazeContainer extends JPanel { 

    
    private MazeEntry[][] entries;
    private static Maze maze;
    private JLabel analyse;

    
    // Anfangsposition zur Erzeugung des Labyrinths im Panel.
    private int startPointX = 10;
    private int startPointY = 25; 
    
    
    
    /** Custom constructor.
    */
    public MazeContainer(){
        super();
        analyse = new JLabel();
        add(analyse);
        this.setOpaque(true);
        this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
    }
    
    
    public void updateResults(int n){
        int iterations = n;
        String results = "" + entries.length + "x" + entries[0].length + " Zellen. " + iterations + " Schritte.";
        analyse.setText(results);
    }

    
    public void unsetResults(){
        analyse.setText("");
    }
    
    
    public void setMaze(Maze m){
        maze = m;
    }
    
    
    public void setEntries(){
        entries = maze.getMaze();
    }
    
    
    public Maze getMaze(){
        return maze;
    }
    
        
    public Dimension getPreferredSize() {
        if(maze == null) {  // Standard Dimension: Nutzer hat nicht nicht den Knopf "Generrieren" gedruckt.
            return new Dimension(200, 200);
        } else {         // Dimension depends on the entries array dimensions.
            return new Dimension((maze.getMaze()[0].length * 10) + 20, (maze.getMaze().length * 10) + 20);
        }
    }
    
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(this.maze != null){
            setEntries();
            Graphics2D g2 = (Graphics2D) g;
            //g2.draw(new Rectangle2D.Double(50, 50, 70, 50));
            //g2.draw(new Rectangle2D.Double(50, 100, 70, 50));
            for(int y = 0; y < maze.getMaze().length; y++){
                for(int x = 0; x < maze.getMaze()[0].length; x++){
                    if(maze.getMaze()[y][x] == MazeEntry.WALL){
                        g2.setColor(Color.GRAY);
                    } 
                    if(entries[y][x] == MazeEntry.PATH) {
                        g2.setColor(Color.WHITE);
                    }
                    if(entries[y][x] == MazeEntry.ENTRY || entries[y][x] == MazeEntry.EXIT) {
                        g2.setColor(Color.RED);
                    }
                    if(entries[y][x] == MazeEntry.SOLUTION){
                        g2.setColor(Color.BLUE);
                    }
                    g2.fillRect(x * 10 + startPointX, y * 10 + startPointY, 10, 10);
                    g2.draw(new Rectangle2D.Double(x * 10 + startPointX, y * 10 + startPointY, 10, 10));
                }
            }
        }
    }

    
    

}
