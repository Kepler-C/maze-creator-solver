package de.uni_hannover.hci.maze.settings_container;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.io.File;

import de.uni_hannover.hci.maze.actual_maze.Maze;
import de.uni_hannover.hci.maze.maze_container.MazeContainer;
import de.uni_hannover.hci.maze.waypointlist.WaypointList;
import de.uni_hannover.hci.maze.solve_algorithms.*;
import de.uni_hannover.hci.maze.recursive_division_method.RecursiveDivisionMethod;


/** Container for the buttons and input fields.
  * Every input and control elements that the user needs is defined here.
*/
public class SettingsContainer extends JPanel implements ActionListener { 


    private JTextField nameField;
    private JLabel nameLabel;
    private String[] algorithms;
    private JComboBox algorithmsList; 
    private JLabel algorithmsLabel;
    private String[] solvingAlgorithms;
    private JComboBox solvingAlgorithmList;
    private JLabel solvingAlgorithmsLabel;
    private JTextField heightField;
    private JLabel heightLabel;
    private JTextField widthField;
    private JLabel widthLabel;
    private JButton generateBtn;
    private JButton loadBtn;
    public JButton saveBtn;
    private JButton solveBtn;
    private JButton hideBtn;
    private Maze maze;
    private MazeContainer mazeCont;

    JTextArea log;

    JFileChooser fc;
    static private final String newline = "\n";
    File archive = null;
    FileReader fr = null;
    BufferedReader br = null;
    
    
    /** Custom constructor.
     * Sets all input elements such as text fields and buttons, 
     * and attaches event handlers to them.
    */
    public SettingsContainer() {
        super();

        // Sets the input controls and labels for the settings panel
        this.nameField = new JTextField(20);
        this.nameField.setActionCommand("Name");
        this.nameField.setMaximumSize(nameField.getPreferredSize());
        this.nameLabel = new JLabel("Name");
        this.nameLabel.setLabelFor(nameField);
        
        this.solvingAlgorithms = new String[]{"Deep Search", "Wall Follower", "Dead-end filling"};
        this.solvingAlgorithmList = new JComboBox(solvingAlgorithms);
        this.solvingAlgorithmList.setMaximumSize(solvingAlgorithmList.getPreferredSize());
        this.solvingAlgorithmList.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.solvingAlgorithmList.setSelectedIndex(0);
        this.solvingAlgorithmsLabel = new JLabel("Lösungsalgorithmus");
        this.solvingAlgorithmsLabel.setLabelFor(solvingAlgorithmList);

        this.algorithms = new String[]{"Random Kruskal's", "Randomized Prim's", "Recursive Division Method"};
        this.algorithmsList = new JComboBox(algorithms);
        this.algorithmsList.setMaximumSize(algorithmsList.getPreferredSize());
        this.algorithmsList.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.algorithmsList.setSelectedIndex(0);
        this.algorithmsLabel = new JLabel("Erstellungsalgorithmus");
        this.algorithmsLabel.setLabelFor(algorithmsList);

        this.heightField = new JTextField(3);
        this.heightField.setActionCommand("Höhe");
        this.heightField.setMaximumSize(heightField.getPreferredSize());
        this.heightLabel = new JLabel("Höhe (max: 47)");
        this.heightLabel.setLabelFor(heightField);

        this.widthField = new JTextField(3);
        this.widthField.setActionCommand("Breite");
        this.widthField.setMaximumSize(widthField.getPreferredSize());
        this.widthLabel = new JLabel("Breite (max: 80)");
        this.widthLabel.setLabelFor(widthField);

        this.generateBtn = new JButton("Generieren");
        this.generateBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.loadBtn = new JButton("Laden");
        this.saveBtn = new JButton("Speichern");
        this.solveBtn = new JButton("Lösung anzeigen");
        this.hideBtn = new JButton("Lösung verbergen");
        this.hideBtn.setEnabled(false);
        
        // add action listeners
        generateBtn.addActionListener(this);
        solveBtn.addActionListener(this);
        saveBtn.addActionListener(this);
        loadBtn.addActionListener(this);
        hideBtn.addActionListener(this);
        
        Box verticalSeparator = new Box(BoxLayout.X_AXIS);
        
        
        // adds settings contents to their container.
        this.add(verticalSeparator.createRigidArea(new Dimension(0, 10)));
        this.add(nameLabel);
        this.add(nameField);
        this.add(verticalSeparator.createRigidArea(new Dimension(0, 10)));
        this.add(algorithmsLabel);
        this.add(algorithmsList);
        this.add(verticalSeparator.createRigidArea(new Dimension(0, 10)));
        this.add(solvingAlgorithmsLabel);
        this.add(solvingAlgorithmList);
        this.add(verticalSeparator.createRigidArea(new Dimension(0, 10)));
        this.add(heightLabel);
        this.add(heightField);
        this.add(widthLabel);
        this.add(widthField);
        this.add(verticalSeparator.createRigidArea(new Dimension(0, 10)));
        this.add(generateBtn);
        this.add(loadBtn);
        this.add(saveBtn);
        this.add(solveBtn);
        this.add(hideBtn);

        // ein leer Maze-Panel 
        this.mazeCont = new MazeContainer();

        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setOpaque(true);
        this.setBorder(BorderFactory.createTitledBorder("Einstellungen"));
       
        
        fc = new JFileChooser("./");

    }
    
    
    
    public Maze getMaze(){
        return maze;
    }
    
    
    
    public MazeContainer getMazeContainer(){
        return mazeCont;
    }
    
    
    
    private boolean areValidDimensions(int width, int height){
        if(width > 80 || height > 47 || width <= 1 || height <= 1){
            return false;
        }
        return true;
    }
   
    
    
    // Hier wird bestimmt, was die Buttons tun.
    public void actionPerformed(ActionEvent e) { 
    
            // Labyrinth as String laden und anzeigen.
            if (e.getSource() == loadBtn) {
                int returnVal = fc.showOpenDialog(SettingsContainer.this);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fc.getSelectedFile();
                         //This is where a real application would open the file.
                         try {
                             archive = new File (fc.getSelectedFile().getName());
                             fr = new FileReader (archive);
                             br = new BufferedReader(fr);

                             String linex = "";
                             String line;
                             while((line = br.readLine()) != null){
                                 linex += line + "\n";
                             }
                             line = linex.substring(0, linex.length() - 1);
                             maze = new Maze(line);
                             mazeCont.setMaze(maze);
                             mazeCont.repaint();
                         } catch(Exception t){
                             t.printStackTrace();
                         } finally {
                             try {
                                 if (null != fr) {
                                     fr.close();
                                 }
                              } catch (Exception e2) {
                                  e2.printStackTrace();
                              }
                         }
                    }
            }        
            
            // Maze speichern.
            if (e.getSource() == saveBtn){
                    if(nameField.getText().equals("")){
                        JOptionPane.showMessageDialog(null, "Bitte zuerst Namen für das Labyrinth angeben!");
                        return;
                    }
                    File file = fc.getSelectedFile();
                    //This is where a real application would save the file.
                    try {
                        //Whatever the file path is.
                        //dateipfad und Name wurden aus testgruenden hardgecoded
                        File statText = new File("./"+ nameField.getText() +".txt");
                        FileOutputStream is = new FileOutputStream(statText);
                        OutputStreamWriter osw = new OutputStreamWriter(is);
                        Writer w = new BufferedWriter(osw);    
                        w.write(mazeCont.getMaze().getStringMaze()); 
                        w.close();
                        JOptionPane.showMessageDialog(null, "Labyrinth erfolgreich gespeichert");
                    } catch (Exception t) {
                        JOptionPane.showMessageDialog(null, "Kein Maze zu speichern");
                        System.err.println(t);
                    }   
            }
            
            // Maze erzeugen.
            if(e.getSource() == generateBtn){
                int width = 0;
                int height = 0; 
                try {
                    width = Integer.parseInt(widthField.getText());                    
                    height = Integer.parseInt(heightField.getText());
                    if(!areValidDimensions(width, height)){
                        JOptionPane.showMessageDialog(null, "Bitte eine gültige Dimension angeben!");
                        if(width <= 1 || height <= 1 || width > 80 || height > 47) {
                            return; // verhindert die Erzeugung des Mazes.  
                        }    
                    }
                    if(algorithmsList.getSelectedIndex() == 1){
                        maze = new Maze(width, height, 1); // rand. Prim's alg.
                    } else if(algorithmsList.getSelectedIndex() == 2){
                        RecursiveDivisionMethod rdm = new RecursiveDivisionMethod(width, height);
                        maze = new Maze(rdm.getMaze());
                    } else {
                        maze = new Maze(width, height, 0); // rand. Kruskal's alg.
                    }
                    mazeCont.setMaze(maze);
                    mazeCont.unsetResults();
                    mazeCont.repaint();
                    hideBtn.setEnabled(false);
                    solveBtn.setEnabled(true);
                } catch(Exception t){
                    // nichts wurde angeben.
                    System.err.println(t);
                    JOptionPane.showMessageDialog(null, "Bitte Dimensionen angeben!");
                }
            }
            
            // Maze lösen.
            if(e.getSource() == solveBtn){
                int iterations = 0;
                try {
                    WaypointList solution;
					if(solvingAlgorithmList.getSelectedIndex() == 0){
						solution = MazeIterator.iterateMaze(maze);
						maze.setSolution(solution);
						iterations = MazeIterator.getNumberOfIterations();
                    }
					if(solvingAlgorithmList.getSelectedIndex() == 1){
						WallFollower wf = new WallFollower();
						solution = wf.findExit(maze);
						maze.setSolution(solution);
						iterations = wf.getNumberOfIterations();
                    }
                    if(solvingAlgorithmList.getSelectedIndex() == 2){
						solution = DeadEndFilling.algorithm(maze);
						maze.setSolution(solution);
						iterations = DeadEndFilling.getNumberOfIterations();
					}
					mazeCont.setMaze(maze);
					mazeCont.updateResults(iterations);
					mazeCont.repaint();
					hideBtn.setEnabled(true);
					solveBtn.setEnabled(false);
                } catch(Exception t){
                    System.err.println(t);
                    JOptionPane.showMessageDialog(null, "Lösung konnte nicht angezeigt werden.");
                }
            }
            
            // Lösung verstecken
            if(e.getSource() == hideBtn){               
                try { 
                    maze.eraseSolution();
                    mazeCont.setMaze(maze);
                    mazeCont.unsetResults();
                    mazeCont.repaint();
                    hideBtn.setEnabled(false); 
                    solveBtn.setEnabled(true);
                } catch(Exception t){
                    System.out.println(t);
                    System.err.println("Lösung konnte nicht versteckt werden.");
                }       
            }
        }
    
    
    
    
}    
    
