package de.uni_hannover.hci.maze;

import de.uni_hannover.hci.maze.maze_container.*;
import de.uni_hannover.hci.maze.settings_container.*;
import de.uni_hannover.hci.maze.random_kruskal_algorithm.*;
import de.uni_hannover.hci.maze.algorithm_selector.*;
import de.uni_hannover.hci.maze.algorithms.Algorithm;
import de.uni_hannover.hci.maze.actual_maze.Maze;



import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.Color;



class Main {


   public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(); 
            }
        });
    }

    
    
    private static void createAndShowGUI() {
    
        System.out.println("Created GUI on EDT? "+
        SwingUtilities.isEventDispatchThread());
        
        
        // content pane for the application's frame
        JPanel mainPane = new JPanel();
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.X_AXIS));
        mainPane.setOpaque(true);

        
        
        SettingsContainer settings = new SettingsContainer();       
        MazeContainer mazeCont = settings.getMazeContainer();
        
        
        
        // Sub-panels are attached to the main Panel
        mainPane.add(mazeCont);
        mainPane.add(Box.createRigidArea(new Dimension(5, 0)));
        mainPane.add(settings);
        
        
        
        // the main frame (window)
        JFrame frame = new JFrame("Labyrinth");                
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(mainPane);
        frame.pack(); // damit wird die Dimensionen vom Fenster flexibler.
        frame.setVisible(true);
        
    }


}
