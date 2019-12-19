package de.uni_hannover.hci.maze.solve_algorithms;

import de.uni_hannover.hci.maze.actual_maze.Maze;
import de.uni_hannover.hci.maze.maze_entry.MazeEntry;
import de.uni_hannover.hci.maze.waypointlist.WaypointList;
import java.util.ArrayList;
import java.util.List;

public class DeadEndFilling{

    public static int iterations; 
	
	static public WaypointList algorithm(Maze maze){
		iterations = 0;
		boolean flag = true;
		boolean flag2;
		MazeEntry[][] entries = new Maze(maze.getStringMaze()).getMaze();
		while(flag){
			flag = true;
			flag2 = false;
			for(int h = 1; h < maze.getMaze().length; h++){
				for(int w = 1; w < maze.getMaze()[0].length; w++){
					if(entries[h][w] == MazeEntry.PATH){
						if(entries[h+1][w] == MazeEntry.WALL && entries[h][w+1] == MazeEntry.WALL
							&& entries[h-1][w] == MazeEntry.WALL){ 
							entries[h][w] = MazeEntry.WALL;
							flag2 = true;
						}
						else if(entries[h][w+1] == MazeEntry.WALL && entries[h-1][w] == MazeEntry.WALL
								&& entries[h][w-1] == MazeEntry.WALL){
							entries[h][w] = MazeEntry.WALL;
							flag2 = true;
						}
						else if(entries[h-1][w] == MazeEntry.WALL && entries[h][w-1] == MazeEntry.WALL
								&& entries[h+1][w] == MazeEntry.WALL){
							entries[h][w] = MazeEntry.WALL;
							flag2 = true;
						}
						else if(entries[h][w-1] == MazeEntry.WALL && entries[h+1][w] == MazeEntry.WALL
								&& entries[h][w+1] == MazeEntry.WALL){
							entries[h][w] = MazeEntry.WALL;
							flag2 = true;
						}
					}
					
				}
			}
			if(!flag2) flag = false;
			iterations++;
		}
		/*
		for(MazeEntry[] a : entries){
			for(MazeEntry e : a){
				if(e == MazeEntry.ENTRY || e == MazeEntry.EXIT) System.out.print("@");
				if(e == MazeEntry.WALL) System.out.print("#");
				if(e == MazeEntry.PATH) System.out.print(" ");
			}
			System.out.println("");
		}
		*/

		WaypointList list = new WaypointList(1, 1, null);

		for(int i = 0; i < entries.length; i++){
			for(int j = 0; j < entries[0].length; j++){
				if(entries[i][j] == MazeEntry.PATH) {
				   list = new WaypointList(j, i, list);
				}
				iterations++;
			}
		}
		
	    return list;
		
	}
	
	
	public static int getNumberOfIterations(){
        return iterations;
    }
}